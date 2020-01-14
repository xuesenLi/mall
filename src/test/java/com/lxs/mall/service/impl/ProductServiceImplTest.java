package com.lxs.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.lxs.mall.MallTest;
import com.lxs.mall.pojo.Product;
import com.lxs.mall.service.ProductService;
import com.lxs.mall.vo.ProductVo;
import com.lxs.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Mr.Li
 * @date 2020/1/13 - 10:03
 */
@Slf4j
public class ProductServiceImplTest extends MallTest {

    @Autowired
    private ProductService productService;


    @Test
    public void list() {


        ResponseVo<PageInfo> list = productService.list(null, 2, 3);

        log.info("ResponseVo<List<ProductVo>> = {} ", list);

    }
}
