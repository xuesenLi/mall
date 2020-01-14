package com.lxs.mall.dao;

import com.lxs.mall.pojo.Sequence;

public interface SequenceMapper {
    int deleteByPrimaryKey(String name);

    int insert(Sequence record);

    int insertSelective(Sequence record);

    Sequence selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(Sequence record);

    int updateByPrimaryKey(Sequence record);

    /**
     *  拿到序列号并修改
     * @param name
     * @return
     */
    Sequence getSequenceByName(String name);

}
