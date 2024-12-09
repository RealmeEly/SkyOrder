package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
    /**
     * 根据OpenId获取用户
     *
     * @param openId
     * @return
     */
    @Select("select * from user where openid = #{openId}")
    User getByOpenId(String openId);

    /**
     * 新增用户
     *
     * @param user
     */
    void addUser(User user);

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(Long id);

    /**
     * 根据创建时间统计
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer countByCreateTime(LocalDateTime beginTime, LocalDateTime endTime);
}
