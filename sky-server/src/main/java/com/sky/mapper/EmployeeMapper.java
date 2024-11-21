package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工
     *
     * @param employee
     */
    void insert(Employee employee);

    /**
     * 编辑员工信息
     *
     * @param employee
     * @return
     */
    void updateEmp(Employee employee);

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据ID查询员工
     *
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);

    /**
     * 启用员工/禁用员工
     *
     * @param status
     * @param id
     */
    @Update("update employee set status = #{status} where id = #{id}")
    void startOrStop(Integer status, Long id);

    /**
     * 修改员工密码
     *
     * @param id
     * @param password
     */
    @Update("update employee set password = #{password} where id = #{id}")
    void editPassword(Long id, String password);
}
