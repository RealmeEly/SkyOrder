package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 用户根据分类查询套餐
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<SetmealVO>> list(Long categoryId) {
        log.info("用户查询分类ID为{}的套餐", categoryId);
        List<SetmealVO> setmealVOList = setmealService.list(categoryId);
        return Result.success(setmealVOList);
    }

    /**
     * 获取套餐菜品信息
     *
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> getDishes(@PathVariable Long id) {
        log.info("获取ID为{}的套餐的菜品信息", id);
        List<DishItemVO> dishItemVOList = setmealService.getDishes(id);
        return Result.success(dishItemVOList);
    }
}
