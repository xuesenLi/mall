package com.lxs.mall.dao;

import com.lxs.mall.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 验证username是否存在
     * @param username
     * @return
     */
    int countByUsername(String username);

    /**
     * 验证email是否存在
     * @param email
     * @return
     */
    int countByEmail(String email);

    /**
     *
     * @param username
     * @return
     */
    User selectByUsername(String username);
}