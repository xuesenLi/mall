package com.lxs.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加商品
 * @author Mr.Li
 * @date 2020/1/13 - 13:36
 */
@Data
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;

}
