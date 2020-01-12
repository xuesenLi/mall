package com.lxs.mall.interceptor;

import com.lxs.mall.consts.MallConst;
import com.lxs.mall.enums.ResponseEnum;
import com.lxs.mall.exception.UserLoginException;
import com.lxs.mall.pojo.User;
import com.lxs.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mr.Li
 * @date 2019/12/29 - 12:06
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info(" preHandler............" );
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if(user == null){
            //需要返回我们的ResponseVo json数据
            //1. response.getWriter.print("json串");
            //2. 抛出自定义异常。。
            throw new UserLoginException();
           // return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
