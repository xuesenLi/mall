package com.lxs.mall.enums;

import lombok.Getter;

/**
 * @author Mr.Li
 * @date 2019/12/28 - 17:02
 */
@Getter
public enum ResponseEnum {

    ERROR(-1, "服务端错误"),
    SUCCESS(0, "成功"),
    PASSWORD_ERROR(1, "密码错误"),
    USERNAME_EXIST(2, "用户已存在"),
    EMAIL_EXIST(4, "邮箱已存在"),
    PARAM_ERROR(3, "参数错误"),
    NEED_LOGIN(10, "用户未登录"),
    USERNAME_OR_PASSWORD_ERROR(11, "用户名或则密码错误"),

    PRODUCT_OFF_SALE_OR_DELETE(12, "商品下架或者删除"),

    PRODUCT_NOT_EXIST(13, "商品不存在"),

    PRODUCT_STOCK_ERROR(14, "库存不足"),

    CART_PRODUCT_NOT_EXIST(15, "购物车中此商品商品不存在"),

    DELETE_SHIPPING_FAIL(16, "删除收货地址失败"),

    SHIPPING_NOT_EXIST(17, "收货地址不存在"),

    CART_SELECT_IS_EMPTY(15, "购物车中没有选中的商品"),

    ORDER_NOT_EXIST(16, "订单不存在"),

    ORDER_STATUS_ERROR(16, "订单状态有误"),

    ;

    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
