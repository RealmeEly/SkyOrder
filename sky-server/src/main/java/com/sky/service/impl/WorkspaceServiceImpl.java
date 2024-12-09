package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 获取今日营业数据
     *
     * @return
     */
    @Override
    public BusinessDataVO getBusinessDate(LocalDateTime beginTime, LocalDateTime endTime) {
        Double turnover = orderMapper.sumByStatusAndOrderTime(Orders.COMPLETED, beginTime, endTime);
        if (turnover == null) turnover = 0.0;
        Integer totalOrderCount = orderMapper.countByOrderTime(beginTime, endTime);
        Integer validOrderCount = orderMapper.countByStatusAndOrderTime(Orders.COMPLETED, beginTime, endTime);
        Double orderCompletionRate = totalOrderCount == 0 ? 0.0 : validOrderCount.doubleValue() / totalOrderCount;
        Double unitPrice = turnover == 0 ? 0.0 : turnover / validOrderCount;
        Integer newUsers = userMapper.countByCreateTime(beginTime, endTime);
        return BusinessDataVO.builder().turnover(turnover).validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate).unitPrice(unitPrice).newUsers(newUsers).build();
    }

    /**
     * 查询套餐总览
     *
     * @return
     */
    @Override
    public SetmealOverViewVO getOverviewSetmeals() {
        Integer sold = setmealMapper.countByStatus(1);
        Integer discontinued = setmealMapper.countByStatus(0);
        return SetmealOverViewVO.builder().sold(sold).discontinued(discontinued).build();
    }

    /**
     * 查询菜品总览
     *
     * @return
     */
    @Override
    public DishOverViewVO getOverviewDishes() {
        Integer sold = dishMapper.countByStatus(1);
        Integer discontinued = dishMapper.countByStatus(0);
        return DishOverViewVO.builder().sold(sold).discontinued(discontinued).build();
    }
}
