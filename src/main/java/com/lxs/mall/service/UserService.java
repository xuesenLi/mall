package com.lxs.mall.service;

import com.lxs.mall.pojo.User;
import com.lxs.mall.vo.ResponseVo;

/**
 * @author Mr.Li
 * @date 2019/12/28 - 14:41
 */
public interface UserService {

    /**
     * 注册
     */
    ResponseVo<User> register(User user);

    /**
     * 登录
     */
    ResponseVo<User> login(String username, String password);
}
