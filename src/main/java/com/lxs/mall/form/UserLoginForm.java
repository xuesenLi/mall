package com.lxs.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Li
 * @date 2019/12/28 - 18:36
 */
@Data
public class UserLoginForm {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank
    private String password;
}
