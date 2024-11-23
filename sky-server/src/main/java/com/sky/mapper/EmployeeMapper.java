package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @AutoFill(OperationType.INSERT)
    void insert(Employee employee);

    /**
     * 编辑员工信息
     *
     * @param employee
     * @return
     */
    @AutoFill(OperationType.UPDATE)
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
     * @param employee
     */
    @AutoFill(OperationType.UPDATE)
    @Update("update employee set status = #{status}, update_time = #{updateTime}, update_user=#{updateUser} where id = #{id}")
    void updateStatus(Employee employee);

    /**
     * 修改员工密码
     *
     * @param employee
     */
    @AutoFill(OperationType.UPDATE)
    @Update("update employee set password = #{password}, update_time = #{updateTime}, update_user=#{updateUser} where id = #{id}")
    void editPassword(Employee employee);
}
