package com.lxs.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.lxs.mall.dao.ProductMapper;
import com.lxs.mall.enums.ProductStatusEnum;
import com.lxs.mall.enums.ResponseEnum;
import com.lxs.mall.form.CartAddForm;
import com.lxs.mall.form.CartUpdateForm;
import com.lxs.mall.pojo.Cart;
import com.lxs.mall.pojo.Product;
import com.lxs.mall.service.CartService;
import com.lxs.mall.vo.CartProductVo;
import com.lxs.mall.vo.CartVo;
import com.lxs.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Li
 * @date 2020/1/13 - 14:08
 */
@Service
public class CartServiceImpl implements CartService {

    /** redis key */
    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate  redisTemplate;



    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm form) {
        Integer quantity = 1;
        Product product = productMapper.selectByPrimaryKey(form.getProductId());

        //判断商品是否存在
        if(product == null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品是否在售
        if(!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品库存是否充足
        if(product.getStock() <= 0){
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        //写入redis
        //key: cart_1  cart_uid
        //value : Map<String, String> 可通过map中的key 快速查找
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        Cart cart;
        //先通过key查看购物车是否已经存在。
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        if(StringUtils.isEmpty(value)){
            //说明redis中没有该商品， 新增
            cart = new Cart(product.getId(), quantity, form.getSelected());
        }else{
            //已经加入过购物车， 数量 + 1
            cart = JSON.parseObject(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }

        //写入redis
        opsForHash.put(redisKey,
                String.valueOf(product.getId()),
                JSON.toJSONString(cart));

         return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);


        //opsForHash.entries 获取所有的数据
        Map<String, String> entries = opsForHash.entries(redisKey);

        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();

        //是否全选
        boolean selectAll = true;
        Integer cartTotalQuantity = 0;  //总数量
        BigDecimal CartTotalPrice = BigDecimal.ZERO;  //总价格

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = JSON.parseObject(entry.getValue(), Cart.class);

            //TODO 需要优化， 使用mysql 里的 in
            Product product = productMapper.selectByPrimaryKey(productId);
            if(product != null){
                CartProductVo cartProductVo = new CartProductVo(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf( cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected()
                );
                cartProductVoList.add(cartProductVo);

                if(!cart.getProductSelected()){
                    selectAll = false;
                }
                //计算总价(只计算选中的)
                if(cart.getProductSelected()){
                    CartTotalPrice = CartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }
            cartTotalQuantity += cart.getQuantity();
        }

        cartVo.setCartProductVoList(cartProductVoList);
        //有一个没有选中，就不叫全选
        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartTotalPrice(CartTotalPrice);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        //先通过key查看购物车是否已经存在。
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            //说明redis中没有该商品， 报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        //已经加入过购物车， 修改内容
        Cart cart = JSON.parseObject(value, Cart.class);
        if (form.getQuantity() != null && form.getQuantity() >= 0) {
            cart.setQuantity(form.getQuantity());
        }
        if (form.getSelected() != null) {
            cart.setProductSelected(form.getSelected());
        }
        opsForHash.put(redisKey, String.valueOf(productId), JSON.toJSONString(cart));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        //先通过key查看购物车是否已经存在。
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            //说明redis中没有该商品， 报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        //删除redis中数据 ..
        opsForHash.delete(redisKey, String.valueOf(productId));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    JSON.toJSONString(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    JSON.toJSONString(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }


    /**
     * 获取redis中数据  遍历
     * @param uid
     * @return
     */
    public List<Cart> listForCart(Integer uid){
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        //opsForHash.entries 获取所有的数据
        Map<String, String> entries = opsForHash.entries(redisKey);
        List<Cart> carts = new ArrayList<>();
        entries.forEach((key, value) -> {
            carts.add(JSON.parseObject(value, Cart.class));
        });

        return carts;
    }
}
