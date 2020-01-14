package com.lxs.mall.service;

import com.lxs.mall.form.CartAddForm;
import com.lxs.mall.form.CartUpdateForm;
import com.lxs.mall.vo.CartVo;
import com.lxs.mall.vo.ResponseVo;

/**
 * @author Mr.Li
 * @date 2020/1/13 - 14:07
 */

public interface CartService {

    ResponseVo<CartVo> add(Integer uid, CartAddForm form);

    /**
     * 获取购物车列表
     * @param uid
     * @return
     */
    ResponseVo<CartVo> list(Integer uid);


    /**
     * 修改购物车
     * @param uid
     * @param productId
     * @param cartUpdateForm
     * @return
     */
    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);


    /**
     * 删除购物车
     * @param uid
     * @param productId
     * @return
     */
    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    /**
     * 全选
     * @param uid
     * @return
     */
    ResponseVo<CartVo> selectAll(Integer uid);

    /**
     * 全不选
     * @param uid
     * @return
     */
    ResponseVo<CartVo> unSelectAll(Integer uid);

    /**
     * 获取购物车中所有商品数量
     * @param uid
     * @return
     */
    ResponseVo<Integer> sum(Integer uid);


}
