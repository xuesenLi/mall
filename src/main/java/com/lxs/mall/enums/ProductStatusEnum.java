package com.lxs.mall.enums;

import lombok.Getter;

/**
 * 商品状态.1-在售 2-下架 3-删除
 * @author Mr.Li
 * @date 2020/1/13 - 11:01
 */

@Getter
public enum ProductStatusEnum {

    ON_SALE(1, "在售中"),

    OFF_SALE(2, "下架"),

    DELETE(3, "删除"),

    ;

    private Integer code;

    private String  msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
