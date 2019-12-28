package com.lxs.mall.dao;

import com.lxs.mall.MallTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Mr.Li
 * @date 2019/11/29 - 16:28
 */

public class CategoryMapperTest extends MallTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void findById() {
    }

    @Test
    public void queryById() {
        /*Category category = categoryMapper.queryById(100005);
        System.out.println(category);*/
    }
}