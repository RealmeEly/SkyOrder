package com.sky.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {
    /**
     * 根据分类ID删除菜品
     *
     * @param id
     */
    @Delete("delete from dish where category_id = #{id}")
    void deleteByCategoryId(Long id);
}
