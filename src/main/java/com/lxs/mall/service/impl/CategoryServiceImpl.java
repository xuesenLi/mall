package com.lxs.mall.service.impl;

import com.lxs.mall.consts.MallConst;
import com.lxs.mall.dao.CategoryMapper;
import com.lxs.mall.pojo.Category;
import com.lxs.mall.service.CategoryService;
import com.lxs.mall.vo.CategoryVo;
import com.lxs.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mr.Li
 * @date 2020/1/12 - 18:31
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 返回所有类目信息  封装
     * @return
     */
    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<Category> categories = categoryMapper.selectAll();

        //查出parent_id = 0;   lambda + stream
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(MallConst.ROOT_PARENT_ID))
                .map(this :: category2categoryVoConvert)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());

        //查询子目录
        findSubCategory(categoryVoList, categories);


        return ResponseVo.success(categoryVoList);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();

        findSubCategoryId(id, resultSet, categories);

    }

    //递归查找子目录 id
    private void findSubCategoryId(Integer id, Set<Integer> resultSet, List<Category> categories) {
        for (Category category : categories) {
            if(category.getParentId().equals(id)){
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(), resultSet, categories);
            }
        }
    }

    //查找子目录
    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories){
        for (CategoryVo categoryVo : categoryVoList){

            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for(Category category : categories){
                //如果查到内容，设置subCategoryList 继续往下查
                if(categoryVo.getId().equals(category.getParentId())){
                    subCategoryVoList.add(category2categoryVoConvert(category));
                }
                //设置之前先排序
                //subCategoryVoList.sort((a,b) -> b.getSortOrder() - a.getSortOrder());
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());

                categoryVo.setSubCategories(subCategoryVoList);
                //递归实现 多级目录的遍历。
                findSubCategory(subCategoryVoList, categories);
            }
        }
    }

    //category ->  categoryVo
    private CategoryVo category2categoryVoConvert(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
