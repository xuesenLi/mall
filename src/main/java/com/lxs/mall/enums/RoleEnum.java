package com.lxs.mall.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author Mr.Li
 * @date 2019/12/28 - 15:04
 *
 * 角色0-管理员,1-普通用户
 */
@Getter
public enum RoleEnum {
    ADMIN(0),

    CUSTOMER(1),
    ;
    private Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
