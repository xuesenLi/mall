package com.lxs.mall.vo;

import lombok.Data;

import java.util.List;

/**
 * 根据接口文档返回参数设计字段
 *      商品类目表
 * @author Mr.Li
 * @date 2020/1/12 - 18:26
 */
@Data
public class CategoryVo {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;

    //类目子目录
    private List<CategoryVo> subCategories;


}
