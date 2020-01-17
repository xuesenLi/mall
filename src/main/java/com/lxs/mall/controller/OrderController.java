package com.lxs.mall.controller;

import com.github.pagehelper.PageInfo;
import com.lxs.mall.consts.MallConst;
import com.lxs.mall.form.OrderCreateForm;
import com.lxs.mall.pojo.User;
import com.lxs.mall.service.OrderService;
import com.lxs.mall.vo.OrderVo;
import com.lxs.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Mr.Li
 * @date 2020/1/15 - 10:07
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param form
     * @param session
     * @return
     */
    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form,
                                      HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.create(user.getId(), form.getShippingId());
    }

    /**
     * 订单列表  分页
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                     HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(), pageNum, pageSize);
    }

    /**
     * 订单详情
     * @param orderNo
     * @param session
     * @return
     */
    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable("orderNo") Long orderNo,
                                      HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(), orderNo);
    }

    /**
     * 取消订单
     * @param orderNo
     * @param session
     * @return
     */
    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(@PathVariable("orderNo") Long orderNo,
                             HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(), orderNo);
    }
}
