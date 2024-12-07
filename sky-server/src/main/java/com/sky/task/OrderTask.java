package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单（间隔1分钟执行）
     */
    @Scheduled(cron = "0 * * * * ?")
    public void processTimeoutOrder() {
        log.info("\u001B[34m" + "处理超时订单" + "\u001B[0m");
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> orders = orderMapper.getByStatusAndOrderTime(Orders.PENDING_PAYMENT, time);
        if (orders != null && !orders.isEmpty()) {
            orders.forEach(order -> {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("支付超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            });
        }
    }

    /**
     * 处理派送中订单（每日凌晨1点整执行）
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder() {
        log.info("\u001B[34m" + "处理派送中订单" + "\u001B[0m");
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> orders = orderMapper.getByStatusAndOrderTime(Orders.DELIVERY_IN_PROGRESS, time);
        if (orders != null && !orders.isEmpty()) {
            orders.forEach(order -> {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            });
        }
    }
}
