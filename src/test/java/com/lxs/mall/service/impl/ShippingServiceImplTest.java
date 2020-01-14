package com.lxs.mall.service.impl;

import com.lxs.mall.MallTest;
import com.lxs.mall.enums.ResponseEnum;
import com.lxs.mall.form.ShippingForm;
import com.lxs.mall.service.ShippingService;
import com.lxs.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Mr.Li
 * @date 2020/1/14 - 9:42
 */
@Slf4j
public class ShippingServiceImplTest extends MallTest {

    @Autowired
    private ShippingService shippingService;

    private Integer uid = 1;

    private ShippingForm form;

    private Integer shippingId;

    @Before
    public void before() {
        ShippingForm form = new ShippingForm();
        form.setReceiverName("廖师兄");
        form.setReceiverAddress("慕课网");
        form.setReceiverCity("北京");
        form.setReceiverMobile("18812345678");
        form.setReceiverPhone("010123456");
        form.setReceiverProvince("北京");
        form.setReceiverDistrict("海淀区");
        form.setReceiverZip("000000");
        this.form = form;
    }

    @Test
    public void add() {
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, form);
        log.info("result={}", responseVo);
        this.shippingId = responseVo.getData().get("shippingId");
       // Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

   // @After
    @Test
    public void delete() {
        ResponseVo responseVo = shippingService.delete(2, 10);
        log.info("result={}", responseVo);
       // Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void update() {
        form.setReceiverCity("杭州");
        ResponseVo responseVo = shippingService.update(1, 11, form);
        log.info("result={}", responseVo);
       // Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void list() {
        ResponseVo responseVo = shippingService.list(uid, 2, 3);
        log.info("result={}", responseVo);
      //  Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

}
