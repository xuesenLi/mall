package com.lxs.mall.service;

import com.github.pagehelper.PageInfo;
import com.lxs.mall.vo.ProductDetailVo;
import com.lxs.mall.vo.ResponseVo;

/**
 * @author Mr.Li
 * @date 2020/1/13 - 9:42
 */
public interface ProductService {

    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> detail(Integer productId);

}
