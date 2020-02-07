package com.lxs.mall.pojo;

import lombok.Data;

/**
 * 购物车中的字段
 *      用于存在redis中的属性
 * @author Mr.Li
 * @date 2020/1/13 - 14:57
 */
@Data
public class Cart {

    //商品Id
    private Integer productId;

    //商品数量
    private Integer quantity;

    //商品是否被选中
    private Boolean productSelected;

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }

    public Cart() {
    }
}
