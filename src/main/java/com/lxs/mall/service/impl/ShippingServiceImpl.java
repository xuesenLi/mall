package com.lxs.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxs.mall.dao.ShippingMapper;
import com.lxs.mall.enums.ResponseEnum;
import com.lxs.mall.form.ShippingForm;
import com.lxs.mall.pojo.Shipping;
import com.lxs.mall.service.ShippingService;
import com.lxs.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Li
 * @date 2020/1/14 - 9:26
 */
@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingMapper shippingMapper;


    /**
     * 添加收货地址， 返回自增ID
     * @param uid
     * @param form
     * @return
     */
    @Override
    @Transactional
    public ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setUserId(uid);
        int row = shippingMapper.insertSelective(shipping);
        if(row == 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        Map<String, Integer> map = new HashMap<>();

        //获取自增ID  需要在mapper文件中设置
        map.put("shippingId", shipping.getId());
        return ResponseVo.success(map);
    }

    @Override
    @Transactional
    public ResponseVo delete(Integer uid, Integer shippingId) {
        //需要通过uid 以及sippingId 共同来验证删除
        int row = shippingMapper.deleteByIdAndUid(uid, shippingId);
        if(row == 0){
            return ResponseVo.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo update(Integer uid, Integer shippingId, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);

        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if(row == 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUid(uid);

        PageInfo<Shipping> pageInfo = new PageInfo<>(shippingList);
        //pageInfo.setList(shippingList);
        return ResponseVo.success(pageInfo);
    }
}
