package com.lxs.mall.dao;

import com.lxs.mall.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectByUid(Integer Uid);


    /**
     * 通过uid orderNo 查找订单
     * @param Uid
     * @param orderNo
     * @return
     */
    Order selectByUidAndOrderNo(@Param("Uid") Integer Uid, @Param("orderNo") Long orderNo);

    /**
     * 通过 orderNo 查找订单  更快 索引
     * @param orderNo
     * @return
     */
    Order selectByOrderNo(@Param("orderNo") Long orderNo);

}
