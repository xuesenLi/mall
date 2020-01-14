package com.lxs.mall.dao;

import com.lxs.mall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    /**
     * 批量写入
     * @param orderItemList
     * @return
     */
    int batchInsert(@Param("orderItemList") List<OrderItem> orderItemList);


    /**
     * 通过 orderNoSet 返回订单子表
     * @param orderNoSet
     * @return
     */
    List<OrderItem> selectByOrderNoSet(@Param("orderNoSet") Set<Long> orderNoSet);

}
