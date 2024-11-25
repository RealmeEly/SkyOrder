package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    /**
     * 根据分类ID删除菜品
     *
     * @param id
     */
    @Delete("delete from dish where category_id = #{id}")
    void deleteByCategoryId(Long id);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品分类查询
     *
     * @param categoryId
     * @return
     */
    @Select("select * from dish where category_id = #{categoryId} order by create_time desc")
    List<DishVO> list(Long categoryId);

    /**
     * 根据ID查询菜品
     *
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    DishVO getById(Long id);

    /**
     * 修改菜品
     *
     * @param dish
     */
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 新增菜品
     *
     * @param dish
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 启售/停售
     *
     * @param dish
     */
    @AutoFill(OperationType.UPDATE)
    void updateStatus(Dish dish);

    /**
     * 计数停售菜品数量
     *
     * @param Ids
     * @return
     */
    Integer countStop(List<Long> Ids);
}
