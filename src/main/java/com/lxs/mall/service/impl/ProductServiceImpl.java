package com.lxs.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxs.mall.dao.ProductMapper;
import com.lxs.mall.enums.ProductStatusEnum;
import com.lxs.mall.enums.ResponseEnum;
import com.lxs.mall.exception.GlobalException;
import com.lxs.mall.pojo.Product;
import com.lxs.mall.service.CategoryService;
import com.lxs.mall.service.ProductService;
import com.lxs.mall.vo.ProductDetailVo;
import com.lxs.mall.vo.ProductVo;
import com.lxs.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mr.Li
 * @date 2020/1/13 - 9:44
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if(categoryId != null){
            //返回当前id 的所有子目录id
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }

        //mybaits分页
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet.size() == 0 ? null : categoryIdSet);


        List<ProductVo> productVoList = productList
                .stream().map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                }).collect(Collectors.toList());
        //productList 为数据库返回的
        PageInfo pageInfo = new PageInfo<>(productList);
        pageInfo.setList(productVoList);
        log.info("products =  {} ", productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);

        //只对确定性条件 判断
        if(product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode()) ||
                product.getStatus().equals(ProductStatusEnum.DELETE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);

        //敏感数据处理
        productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());

        return ResponseVo.success(productDetailVo);
    }
}
