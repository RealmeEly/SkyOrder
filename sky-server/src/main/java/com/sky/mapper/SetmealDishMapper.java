package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
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
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 根据菜品ID获取关联套餐IDs
     *
     * @param dishId
     * @return
     */
    @Select("select setmeal_id from setmeal_dish where dish_id = #{dishId}")
    List<Long> getSetmealIdsByDishId(Long dishId);

    /**
     * 根据套餐ID获取关联菜品
     *
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getDishsBySetmealId(Long setmealId);

    /**
     * 批量新增
     *
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐ID获取关联菜品IDs
     *
     * @param setmealId
     * @return
     */
    @Select("select dish_id from setmeal_dish where setmeal_id = #{setmealId}")
    List<Long> getDishIdsBySetmealId(Long setmealId);

    /**
     * 根据套餐ID批量删除
     *
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBatchBySetmealId(Long setmealId);

    /**
     * 根据套餐IDs批量删除
     *
     * @param setmealIds
     */
    void deleteBatchBySetmealIds(List<Long> setmealIds);
}
