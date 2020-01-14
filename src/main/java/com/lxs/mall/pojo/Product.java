package com.lxs.mall.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    //商品状态.1-在售 2-下架 3-删除
    private Integer status;

    private Date createTime;

    private Date updateTime;
}
