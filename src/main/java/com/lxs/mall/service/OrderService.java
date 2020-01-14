package com.lxs.mall.service;

import com.github.pagehelper.PageInfo;
import com.lxs.mall.vo.OrderVo;
import com.lxs.mall.vo.ResponseVo;

/**
 *
 */
public interface OrderService {

	/**
	 * 创建订单
	 * @param uid
	 * @param shippingId  收获地址Id
	 * @return
	 */
	ResponseVo<OrderVo> create(Integer uid, Integer shippingId);

	/**
	 * 订单列表  分页
	 * @param uid
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

	/**
	 * 订单详情
	 * @param uid
	 * @param orderNo
	 * @return
	 */
	ResponseVo<OrderVo> detail(Integer uid, Long orderNo);

	ResponseVo cancel(Integer uid, Long orderNo);

	void paid(Long orderNo);
}
