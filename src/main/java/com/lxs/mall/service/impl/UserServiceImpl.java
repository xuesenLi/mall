package com.lxs.mall.service.impl;

import com.lxs.mall.dao.UserMapper;
import com.lxs.mall.enums.ResponseEnum;
import com.lxs.mall.enums.RoleEnum;
import com.lxs.mall.pojo.User;
import com.lxs.mall.service.UserService;
import com.lxs.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;


/**
 * @author Mr.Li
 * @date 2019/12/28 - 14:41
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册
     * @param user
     */
    @Override
    public ResponseVo<User> register(User user) {
        //username不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if(countByUsername > 0){
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        //email 不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if(countByEmail > 0){
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }

        user.setRole(RoleEnum.CUSTOMER.getCode());

        //MD5摘要算法(Spring自带了)
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        //写入数据库
        int resultCount = userMapper.insertSelective(user);
        if(resultCount == 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if(user == null){
            //用户不存在 返回： 用户名或则密码错误
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        //忽略大小写比较
        log.info("MD5 ==== {}", DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
       if(!user.getPassword().equalsIgnoreCase(
               DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
           //密码错误 返回： 用户名或则密码错误
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
       }

       //将密码隐藏
        user.setPassword("");
       return ResponseVo.success(user);



    }
}
