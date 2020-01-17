package com.lxs.mall.dao;

import com.lxs.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByIdAndUid(@Param("uid") Integer uid, @Param("shippingId") Integer shipping);

    List<Shipping> selectByUid(Integer uid);

    Shipping selectByUidAndShippingId(@Param("uid") Integer uid, @Param("shippingId") Integer shipping);

    /**
     *  通过ShippingId 返回地址信息
     * @param shippingIdSet
     * @return
     */
    List<Shipping> selectByShippingIdSet(@Param("shippingIdSet")Set<Integer> shippingIdSet);

}
