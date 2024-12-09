package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {
    /**
     * 获取今日营业数据
     *
     * @return
     */
    BusinessDataVO getBusinessDate(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 查询套餐总览
     *
     * @return
     */
    SetmealOverViewVO getOverviewSetmeals();

    /**
     * 查询菜品总览
     *
     * @return
     */
    DishOverViewVO getOverviewDishes();
}
