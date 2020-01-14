package com.lxs.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 根据接口文档返回参数设计字段
 *  *      商品详情
 * @author Mr.Li
 * @date 2020/1/13 - 10:56
 */
@Data
public class ProductDetailVo {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
