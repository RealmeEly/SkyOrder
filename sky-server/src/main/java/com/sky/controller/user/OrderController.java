package com.sky.controller.user;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderInfoDetailVO;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    /**
     * 历史订单查询
     *
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/historyOrders")
    public Result<PageResult> historyOrders(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("历史订单查询：{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.historyOrders(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @GetMapping("/orderDetail/{id}")
    public Result<OrderInfoDetailVO> getById(@PathVariable Long id) {
        log.info("查询ID为{}的订单", id);
        OrderInfoDetailVO orderInfoDetailVO = orderService.getById(id);
        return Result.success(orderInfoDetailVO);
    }

    /**
     * 取消订单
     *
     * @param id
     * @return
     */
    @PutMapping("/cancel/{id}")
    public Result<String> cancelOrder(@PathVariable Long id) {
        log.info("用户取消ID为{}的订单", id);
        orderService.userCancelOrder(id);
        return Result.success();
    }

    /**
     * 再来一单
     *
     * @param id
     * @return
     */
    @PostMapping("/repetition/{id}")
    public Result<String> repetition(@PathVariable Long id) {
        log.info("再来一单");
        orderService.repetition(id);
        return Result.success();
    }

    /**
     * 催单
     *
     * @param id
     * @return
     */
    @GetMapping("/reminder/{id}")
    public Result<String> reminder(@PathVariable Long id) {
        log.info("用户催单");
        orderService.reminder(id);
        return Result.success();
    }
}
