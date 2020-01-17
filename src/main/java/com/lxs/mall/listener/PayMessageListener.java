package com.lxs.mall.listener;

import com.alibaba.fastjson.JSON;
import com.lxs.mall.consts.MallConst;
import com.lxs.mall.pojo.PayInfo;
import com.lxs.mall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 关于PayInfo对象, 正确姿势： 应该由pay项目提供client.jar,  mall项目引入jar包
 *  《 微服务》
 *
 * 使用RabbitMQ 来接受Pay 传递过来的 消息
 * @author Mr.Li
 * @date 2020/1/15 - 11:18
 */
@Component
@RabbitListener(queues = MallConst.QUEUE_PAY_NOTIFY)  //队列名
@Slf4j
public class PayMessageListener {

    @Autowired
    private OrderService orderService;


    //表示使用这个方法来接受监听到的消息
    @RabbitHandler
    public void process(String msg){

        log.info("【接收到消息】 msg = >  {}", msg);
        PayInfo payInfo = JSON.parseObject(msg, PayInfo.class);

            if(payInfo.getPlatformStatus().equals("SUCCESS")){
                //支付成功   修改订单里的状态
                orderService.paid(payInfo.getOrderNo());
            }

    }
}
