package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品IDs获取关联套餐IDs
     *
     * @param dishIds
     * @return
     */
    List<Long> getIdsByDishIds(List<Long> dishIds);

    /**
     * 根据菜品ID获取关联套餐ID
     *
     * @param dishId
     * @return
     */
    @Select("select setmeal_id from setmeal_dish where dish_id = #{dishId}")
    List<Long> getIdsByDishId(Long dishId);
}
