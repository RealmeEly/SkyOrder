package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SetmealMapper {
    /**
     * 根据分类ID删除套餐
     *
     * @param id
     */
    @Delete("delete from setmeal where category_id = #{id}")
    void deleteByCategoryId(Long id);

    /**
     * 启售/停售
     *
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    @Update("update setmeal set status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    void updateStatus(Setmeal setmeal);
}
