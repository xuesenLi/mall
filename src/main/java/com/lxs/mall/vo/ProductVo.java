package com.lxs.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 根据接口文档返回参数设计字段
 *      商品表
 * @author Mr.Li
 * @date 2020/1/13 - 9:40
 */
@Data
public class ProductVo {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;

}
