package com.lxs.mall.controller;

import com.lxs.mall.service.CategoryService;
import com.lxs.mall.vo.CategoryVo;
import com.lxs.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2020/1/12 - 18:47
 */
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categorys")
    public ResponseVo<List<CategoryVo>> selectAll(){
        return categoryService.selectAll();
    }


}
