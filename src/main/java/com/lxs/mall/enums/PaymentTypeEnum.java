package com.lxs.mall.enums;

import lombok.Getter;

/**
 * @author Mr.Li
 * @date 2020/1/14 - 16:12
 */
@Getter
public enum PaymentTypeEnum {
    PAY_ONLINE(1, "在线支付"),

    ;

    private Integer code;

    private String desc;

    PaymentTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
