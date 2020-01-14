package com.lxs.mall.config;

import com.lxs.mall.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Mr.Li
 * @date 2019/12/29 - 12:11
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error","/user/login", "/user/register", "/products", "/products/*", "/categories");
    }
}
