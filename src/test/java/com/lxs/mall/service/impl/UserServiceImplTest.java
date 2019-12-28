package com.lxs.mall.service.impl;

import com.lxs.mall.MallTest;
import com.lxs.mall.enums.RoleEnum;
import com.lxs.mall.pojo.User;
import com.lxs.mall.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author Mr.Li
 * @date 2019/12/28 - 14:58
 */
@Transactional
public class UserServiceImplTest extends MallTest {

    @Autowired
    private UserService userService;

    @Test
    public void register() {
        User user = new User("jack1", "123456", "jack1@qq.com", RoleEnum.CUSTOMER.getCode());

        userService.register(user);
    }
}