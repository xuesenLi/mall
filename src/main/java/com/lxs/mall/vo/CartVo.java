package com.lxs.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车
 * @author Mr.Li
 * @date 2020/1/13 - 13:28
 */
@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    //全选
    private Boolean selectedAll;

    //购物车总价
    private BigDecimal cartTotalPrice;

    //总数
    private Integer cartTotalQuantity;

}
