package com.lxs.mall.service;

import com.github.pagehelper.PageInfo;
import com.lxs.mall.form.ShippingForm;
import com.lxs.mall.vo.ResponseVo;

import java.util.Map;

/**
 * @author Mr.Li
 * @date 2020/1/14 - 9:21
 */
public interface ShippingService {

    /**
     * 添加地址
     * @param uid
     * @param form
     * @return
     */
    ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm form);

    /**
     * 删除
     * @param uid
     * @param shippingId
     * @return
     */
    ResponseVo delete(Integer uid, Integer shippingId);


    /**
     * 更新地址
     * @param uid
     * @param shippingId
     * @param form
     * @return
     */
    ResponseVo update(Integer uid, Integer shippingId, ShippingForm form);


    /**
     * 查询所有地址 分页
     * @param uid
     * @return
     */
    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);




}
