package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据ID删除分类
     *
     * @param id
     * @return
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据类型查询分类
     *
     * @param type
     * @return
     */
    @Select("select * from category where type = #{type} order by sort, create_time desc")
    List<Category> list(Integer type);

    /**
     * 修改分类信息
     *
     * @param category
     */
    void update(Category category);

    /**
     * 启用/禁用
     *
     * @param status
     * @param id
     */
    @Update("update category set status = #{status} where id = #{id}")
    void updateStatus(Integer status, Long id);

    /**
     * 新增分类
     *
     * @param category
     */
    void insert(Category category);

    /**
     * 根据ID查询分类
     *
     * @param id
     * @return
     */
    @Select("select * from category where id = #{id}")
    Category getById(Long id);
}
