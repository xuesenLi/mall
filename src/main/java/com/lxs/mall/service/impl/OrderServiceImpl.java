package com.lxs.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxs.mall.dao.*;
import com.lxs.mall.enums.OrderStatusEnum;
import com.lxs.mall.enums.PaymentTypeEnum;
import com.lxs.mall.enums.ProductStatusEnum;
import com.lxs.mall.enums.ResponseEnum;
import com.lxs.mall.pojo.*;
import com.lxs.mall.service.CartService;
import com.lxs.mall.service.OrderService;
import com.lxs.mall.vo.CartVo;
import com.lxs.mall.vo.OrderItemVo;
import com.lxs.mall.vo.OrderVo;
import com.lxs.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mr.Li
 * @date 2020/1/14 - 11:35
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    /**
     * 创建订单
     * @param uid
     * @param shippingId  收获地址Id
     * @return
     */
    @Override
    @Transactional
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        //生成订单编号 orderNo
        Long orderNo = this.generateOrderNum();
        //收获地址校验
        Shipping shipping = shippingMapper.selectBuUidAndShippingId(uid, shippingId);
        if(shipping == null){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }

        //获取购物车（在redis中）， 校验（是否有商品， 取出选中的商品，  库存）
        List<Cart> carts = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)  //过滤商品为选中状态
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(carts)){
            return ResponseVo.error(ResponseEnum.CART_SELECT_IS_EMPTY);
        }

            //通过 sql  in  一次性查出购物车所有商品
        Set<Integer> productIdSet = carts.stream().map(Cart::getProductId)
                .collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
            //将productList转换成Map<ProductId, Product>
        Map<Integer, Product> productMap = productList.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));


        List<OrderItem> orderItemList = new ArrayList<>();
        //对购物车商品的校验
        for (Cart cart : carts) {
            //根据productId 查找数据库 TODO  不在for循环里面查找数据库
            Product product = productMap.get(cart.getProductId());
            //是否有商品
            if(product == null){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,
                        "商品不存在, productId = " +  cart.getProductId());
            }
            //商品的上下架状态
            if(!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,
                        "商品不是在售状态， " + product.getName());
            }
            //判断库存是否充足
            if(product.getStock() < cart.getQuantity()){
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,
                        "库存不正确. " + product.getName());
            }

            //构建 订单子表 orderItem add OrderItemList
            orderItemList.add(buildOrderItem(uid, orderNo, cart.getQuantity(), product));

            //减库存  TODO  可以直接修改库存
            product.setStock(product.getStock() - cart.getQuantity());
            int rowProduct = productMapper.updateByPrimaryKeySelective(product);
            if(rowProduct <= 0){
                return ResponseVo.error(ResponseEnum.ERROR);
            }

            //更新购物车 （选中的商品) 不能够在这里更新redis购物车 redis不能够回滚

        }

        //计算总价， 自己算被选中的商品 ( 优惠券。。。）

        //生成订单， 入库  order , order_item  事务
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);

        int rowForOrder = orderMapper.insertSelective(order);
        if(rowForOrder <= 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        //写入List<OrderItem>  批量写入 切记for循环
        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if(rowForOrderItem <= 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        //更新购物车 （选中的商品)
        //Redis有事务(打包命令), 与Mysql的事务不一样，不能回滚  (redis是单线程的）
        for (Cart cart : carts) {
             cartService.delete(uid, cart.getProductId());
        }

        //构造orderVo
        return ResponseVo.success(buildOrderVo(order, orderItemList, shipping));
    }


    /**
     * 订单列表  分页
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        //1. 查找订单表order
        List<Order> orderList = orderMapper.selectByUid(uid);

        //2. 查询订单子表 orderItem sql  in
        Set<Long> orderNoSet = orderList.stream()
                .map(Order::getOrderNo)
                .collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
            //封装到Map<Long, List<OrderItem>>  Long == orderNo
        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));


        //3. 查询每笔订单地址 shipping sql  in
        Set<Integer> shippingIdSet = orderList.stream()
                .map(Order::getShippingId)
                .collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByShippingIdSet(shippingIdSet);
            //封装到Map<Integer, String> Integer == shippingId,   String == 地址
        Map<Integer, Shipping> shippingMap = shippingList.stream()
                .collect(Collectors.toMap(Shipping::getId, shipping -> shipping));

        //4. 构建返回对象List<OrderVo>
        List<OrderVo> orderVoList = new ArrayList<>();
        orderList.forEach(order -> {

            OrderVo orderVo = buildOrderVo(order,
                    orderItemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));

            orderVoList.add(orderVo);
        });
        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVoList);

        //构建返回对象List<OrderVo>  TODO  另一种实现
        /*orderList.forEach(order -> {
            //
            buildOrderVo(order,
                    orderItemList.stream()
                            .filter(e -> e.getOrderNo().equals(order.getOrderNo()))
                            .collect(Collectors.toList()),
                    shippingList.stream()
                            .filter(e -> e.getId().equals(order.getShippingId()))
                            .collect(Collectors.toList()).get(0)
            );
        });
*/
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        return null;
    }

    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        return null;
    }

    @Override
    public void paid(Long orderNo) {

    }

    /**
     * 构建Order对象
     * @param uid
     * @param orderNo
     * @param shippingId
     * @param orderItemList
     * @return
     */
    private Order buildOrder(Integer uid,
                             Long orderNo,
                             Integer shippingId,
                             List<OrderItem> orderItemList){
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        order.setPayment(orderItemList.stream()
                            .map(OrderItem::getTotalPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add));
        return order;
    }


    /**
     *  构建 订单子表
     * @param uid
     * @param orderNo
     * @param quantity
     * @param product
     * @return
     */
    private OrderItem buildOrderItem(Integer uid, Long orderNo, Integer quantity, Product product){
        OrderItem orderItem = new OrderItem();
        orderItem.setUserId(uid);
        orderItem.setProductId(product.getId());
        orderItem.setOrderNo(orderNo);
        orderItem.setProductName(product.getName());
        orderItem.setProductImage(product.getMainImage());
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setQuantity(quantity);
        //当前商品总价
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return orderItem;
    }

    /**
     * 构建 OrderVo
     * @param order
     * @param orderItemList
     * @param shipping
     * @return
     */
    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);

        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(orderItem -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(orderItem, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());

        orderVo.setOrderItemVoList(orderItemVoList);

        orderVo.setShippingId(shipping.getId());
        orderVo.setShippingVo(shipping);
        return orderVo;
    }


    /**
     * 生成订单编号
     * 企业级 ：  分布式唯一id/主键
     * @return
     */
    private Long generateOrderNo() {
        return null;
    }


    /**
     * //生成交易流水号，订单号 16位
     * propagation = Propagation.REQUIRES_NEW  无论代码是否在事务中，他都会开启一个新的事务，把代码执行完后提交代码。
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private Long generateOrderNum(){
        StringBuilder stringBuilder = new StringBuilder();
        //订单号16位
        //前八位为时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        stringBuilder.append(now.format(DateTimeFormatter.ISO_DATE).replace("-", ""));

        //中间6位为自增序列
        //获取当前的Sequence
        int CurrentSequence = 0;
        Sequence sequence = sequenceMapper.getSequenceByName("order");
        CurrentSequence = sequence.getCurrentValue();
        sequence.setCurrentValue(sequence.getCurrentValue() + sequence.getStep());
        //使当前的sequence 增加 相应step
        sequenceMapper.updateByPrimaryKeySelective(sequence);

        String sequenceStr = String.valueOf(CurrentSequence);
        //转换成6位
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(CurrentSequence);


        //最后2位为分库分表位, 暂时写死
        stringBuilder.append("00");

        return Long.valueOf(stringBuilder.toString());
    }
}

