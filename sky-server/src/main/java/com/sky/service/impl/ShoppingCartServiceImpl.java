package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.service.ShoppingCartService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 查询购物车
     *
     * @return
     */
    @Override
    public List<ShoppingCart> list() {
        Long userId = BaseContext.getCurrentId();
        return shoppingCartMapper.list(userId);
    }

    /**
     * 添加到购物车
     *
     * @param shoppingCartDTO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        Long dishId = shoppingCartDTO.getDishId();
        Long setmealId = shoppingCartDTO.getSetmealId();
        String dishFlavor = shoppingCartDTO.getDishFlavor();
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = null;
        if (shoppingCartDTO.getDishId() != null) {
            shoppingCart = shoppingCartMapper.getByDishId(dishId, userId, dishFlavor);
        } else {
            shoppingCart = shoppingCartMapper.getBySetmealId(setmealId, userId);
        }
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
            if (shoppingCart.getDishId() != null) {
                DishVO dishVO = dishService.getById(shoppingCart.getDishId());
                shoppingCart.setName(dishVO.getName());
                shoppingCart.setImage(dishVO.getImage());
                shoppingCart.setAmount(dishVO.getPrice());
            } else {
                SetmealVO setmealVO = setmealService.getById(shoppingCart.getSetmealId());
                shoppingCart.setName(setmealVO.getName());
                shoppingCart.setImage(setmealVO.getImage());
                shoppingCart.setAmount(setmealVO.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCartMapper.add(shoppingCart);
        } else {
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.update(shoppingCart);
        }
    }

    /**
     * 从购物车中删除一个商品
     *
     * @param shoppingCartDTO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        Long dishId = shoppingCartDTO.getDishId();
        Long setmealId = shoppingCartDTO.getSetmealId();
        String dishFlavor = shoppingCartDTO.getDishFlavor();
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = null;
        if (shoppingCartDTO.getDishId() != null) {
            shoppingCart = shoppingCartMapper.getByDishId(dishId, userId, dishFlavor);
        } else {
            shoppingCart = shoppingCartMapper.getBySetmealId(setmealId, userId);
        }
        assert shoppingCart != null;
        if (shoppingCart.getNumber() != 1) {
            shoppingCart.setNumber(shoppingCart.getNumber() - 1);
            shoppingCartMapper.update(shoppingCart);
        } else {
            shoppingCartMapper.deleteById(shoppingCart.getId());
        }
    }

    /**
     * 清空购物车
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.clean(userId);
    }
}
