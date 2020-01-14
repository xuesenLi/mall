package com.lxs.mall.dao;

import com.lxs.mall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * 通过categoryIdSet 查找商品
     * @param categoryIdSet
     * @return
     */
    List<Product> selectByCategoryIdSet(@Param("categoryIdSet") Set<Integer> categoryIdSet);

    /**
     * 通过productIdSet 查找商品
     * @param productIdSet
     * @return
     */
    List<Product> selectByProductIdSet(@Param("productIdSet") Set<Integer> productIdSet);

}
