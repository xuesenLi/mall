package com.lxs.mall.vo;

import com.lxs.mall.pojo.Shipping;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author Mr.Li
 * @date 2020/1/14 - 11:11
 */
@Data
public class OrderVo {

    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private List<OrderItemVo> orderItemVoList;

    private Integer shippingId;

    private Shipping shippingVo;

}
