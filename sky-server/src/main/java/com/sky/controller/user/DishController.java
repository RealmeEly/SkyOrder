package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类ID查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("用户查询分类ID为{}的菜品", categoryId);
        //构造redis中的key，规则：dish_分类id
        String key = "dish_" + categoryId;
        //查询redis
        List<DishVO> dishVOList = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (dishVOList != null && !dishVOList.isEmpty()) {
            return Result.success(dishVOList);
        }
        log.info("\u001B[34m" + "查询MYSQL数据库" + "\u001B[0m");
        dishVOList = dishService.list(categoryId);
        //写入缓存
        redisTemplate.opsForValue().set(key, dishVOList);
        return Result.success(dishVOList);
    }
}
