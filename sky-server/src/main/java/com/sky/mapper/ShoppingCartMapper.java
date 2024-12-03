package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 查询购物车
     *
     * @param userId
     * @return
     */
    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCart> list(Long userId);

    /**
     * 根据菜品ID和用户ID查询
     *
     * @param dishId
     * @param userId
     * @param dishFlavor
     * @return
     */
    ShoppingCart getByDishId(Long dishId, Long userId, String dishFlavor);

    /**
     * 根据套餐ID和用户ID查询
     *
     * @param setmealId
     * @param userId
     * @return
     */
    @Select("select * from shopping_cart where setmeal_id = #{setmealId} and user_id = #{userId}")
    ShoppingCart getBySetmealId(Long setmealId, Long userId);

    /**
     * 添加到购物车
     *
     * @param shoppingCart
     */
    @AutoFill(OperationType.USER_INSERT)
    void add(ShoppingCart shoppingCart);

    /**
     * 更新购物车
     *
     * @param shoppingCart
     */
    void update(ShoppingCart shoppingCart);

    /**
     * 根据ID删除
     *
     * @param id
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);

    /**
     * 清空购物车
     *
     * @param userId
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void clean(Long userId);

    /**
     * 批量添加
     *
     * @param shoppingCartList
     */
    void insertBatch(List<ShoppingCart> shoppingCartList);
}
