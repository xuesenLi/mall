package com.lxs.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Mr.Li
 * @date 2019/12/28 - 17:13
 * 用于注册的
 */
@Data
public class UserRegisterForm {

    //@NotBlank   用于 String 判断空格, 是空格也会报错
    //@NotEmpty   用于集合 array list
    //@NotNull
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

}
