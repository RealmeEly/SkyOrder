package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品分类查询
     *
     * @param categoryId
     * @return
     */
    List<DishVO> list(Long categoryId);

    /**
     * 根据ID查询菜品
     *
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    void update(DishDTO dishDTO);

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    void addDish(DishDTO dishDTO);

    /**
     * 启售/停售
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
