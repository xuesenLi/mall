package com.lxs.mall.controller;

import com.lxs.mall.consts.MallConst;
import com.lxs.mall.enums.ResponseEnum;
import com.lxs.mall.form.UserLoginForm;
import com.lxs.mall.form.UserRegisterForm;
import com.lxs.mall.pojo.User;
import com.lxs.mall.service.UserService;
import com.lxs.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Mr.Li
 * @date 2019/12/28 - 16:31
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param userRegisterForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/user/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userRegisterForm,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("注册提交的表单有误, {} {}",
                    bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }

        User user = new User();
        //spring提供的对象拷贝方法
        //原对象 -> 现对象
        BeanUtils.copyProperties(userRegisterForm, user);
        return userService.register(user);
    }

    /**
     * 登录
     * @param userLoginForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  BindingResult bindingResult,
                                  HttpSession session){
        if(bindingResult.hasErrors()){
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }

        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        //设置session
        session.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());
        log.info("/login sessionID = {}", session.getId());
        return userResponseVo;
    }

    /**
     * 在session中获取登录状态
     * @param session
     * @return
     */
    //session保存在内存中， 容易丢失，  所以放在 redis中 ： token + redis 方式
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session){
        log.info("/user sessionID = {}", session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return ResponseVo.success(user);
    }

    //TODO 判断登录状态， 拦截器
    // {@link UserLoginInterceptor}
    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session){
        log.info("/logout sessionID = {}", session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        //删除session
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success(user);
    }

}
