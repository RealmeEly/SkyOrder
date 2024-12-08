package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    /**
     * 用户下单
     *
     * @param order
     */
    void insert(Orders order);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);

    /**
     * 根据订单号获取ID
     *
     * @param orderNumber
     * @return
     */
    @Select("select id from orders where number = #{orderNumber}")
    Long getOrderIdByNumber(String orderNumber);

    /**
     * 更新订单支付状态
     *
     * @param orderStatus
     * @param orderPaidStatus
     * @param checkoutTime
     * @param orderId
     */
    @Update("update orders set status = #{orderStatus}, pay_status = #{orderPaidStatus}, checkout_time = #{checkoutTime} where id = #{orderId}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime checkoutTime, Long orderId);

    /**
     * 分页查询
     *
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 订单统计
     *
     * @param status
     * @return
     */
    Integer countByStatus(Integer status);

    /**
     * 根据状态和下单时间查询
     *
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime orderTime);

    /**
     * 根据状态和下单时间统计营业额
     *
     * @param status
     * @param beginTime
     * @param endTime
     * @return
     */
    Double sumByStatusAndOrderTime(Integer status, LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 根据下单时间统计订单数
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer countByOrderTime(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 根据状态和下单时间统计订单数
     *
     * @param status
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer countByStatusAndOrderTime(Integer status, LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 获取销量Top10
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime beginTime, LocalDateTime endTime);
}
