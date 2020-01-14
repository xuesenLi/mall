package com.lxs.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.lxs.mall.MallTest;
import com.lxs.mall.form.CartAddForm;
import com.lxs.mall.service.CartService;
import com.lxs.mall.vo.CartVo;
import com.lxs.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.rmi.runtime.Log;

/**
 * @author Mr.Li
 * @date 2020/1/13 - 15:16
 */
@Slf4j
public class CartServiceImplTest extends MallTest {

    @Autowired
    private CartService cartService;

    @Test
    public void add() {

        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(27);

        ResponseVo<CartVo> add = cartService.add(1, cartAddForm);
    }

    @Test
    public void list(){
        ResponseVo<CartVo> list = cartService.list(1);
        log.info("list = {}",  list);
    }
}
