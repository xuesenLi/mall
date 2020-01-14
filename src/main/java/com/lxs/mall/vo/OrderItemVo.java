package com.lxs.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mr.Li
 * @date 2020/1/14 - 11:31
 */
@Data
public class OrderItemVo {

    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;
}
