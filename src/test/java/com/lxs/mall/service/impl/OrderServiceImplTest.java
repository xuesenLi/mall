package com.lxs.mall.service.impl;

import com.lxs.mall.MallTest;
import com.lxs.mall.service.OrderService;
import com.lxs.mall.vo.OrderVo;
import com.lxs.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author Mr.Li
 * @date 2020/1/14 - 16:57
 */
@Slf4j
public class OrderServiceImplTest extends MallTest {

    @Autowired
    private OrderService orderService;


    @Test
    public void create() {
        ResponseVo<OrderVo> result = orderService.create(7, 11);
        log.info(" result  === {} ", result);

    }
}
