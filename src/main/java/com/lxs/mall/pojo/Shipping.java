package com.lxs.mall.pojo;

import lombok.Data;

import java.util.Date;

/**
 *收获地址
 */

@Data
public class Shipping {

    private Integer id;

    private Integer userId;

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

    private Date createTime;

    private Date updateTime;
}
