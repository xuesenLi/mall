package com.lxs.mall.controller;

import com.lxs.mall.consts.MallConst;
import com.lxs.mall.form.CartAddForm;
import com.lxs.mall.form.CartUpdateForm;
import com.lxs.mall.pojo.User;
import com.lxs.mall.service.CartService;
import com.lxs.mall.vo.CartVo;
import com.lxs.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Mr.Li
 * @date 2020/1/13 - 13:39
 */
@RestController
public class CartController {

    @Autowired
    private CartService cartService;


    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.list(user.getId());
    }

    /**
     * 添加购物车
     * @param cartAddForm
     * @param session
     * @return
     */
    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm,
                                  HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.add(user.getId(), cartAddForm);
    }

    /**
     * 更新购物车
     * @param session
     * @return
     */
    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable("productId") Integer productId,
                                     @Valid @RequestBody CartUpdateForm form,
                                     HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(user.getId(), productId, form);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable("productId") Integer productId,
                                     HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(), productId);
    }

    /**
     * 全选
     * @param session
     * @return
     */
    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    /**
     * 全不选
     * @param session
     * @return
     */
    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    /**
     * 购物车数量
     * @param session
     * @return
     */
    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }

}
