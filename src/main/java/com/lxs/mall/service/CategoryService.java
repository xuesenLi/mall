package com.lxs.mall.service;

import com.lxs.mall.vo.CategoryVo;
import com.lxs.mall.vo.ResponseVo;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2020/1/12 - 18:29
 */
public interface CategoryService {

    /**
     * 返回所有类目信息
     * @return
     */
    ResponseVo<List<CategoryVo>> selectAll();

}
