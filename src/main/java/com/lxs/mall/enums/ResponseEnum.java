package com.lxs.mall.enums;

import lombok.Getter;

/**
 * @author Mr.Li
 * @date 2019/12/28 - 17:02
 */
@Getter
public enum ResponseEnum {

    ERROR(-1, "服务端错误"),
    SUCCESS(200, "成功"),
    PASSWORD_ERROR(1, "密码错误"),
    USERNAME_EXIST(2, "用户已存在"),
    EMAIL_EXIST(4, "邮箱已存在"),
    PARAM_ERROR(3, "参数错误"),
    NEED_LOGIN(10, "用户未登录"),
    USERNAME_OR_PASSWORD_ERROR(11, "用户名或则密码错误"),

    ;

    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
