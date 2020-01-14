package com.lxs.mall.form;

import lombok.Data;

/**
 * 收货地址 请求表单
 * @author Mr.Li
 * @date 2020/1/14 - 9:19
 */
@Data
public class ShippingForm {

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

}
