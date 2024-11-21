package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     *
     * @param employeeDTO
     */
    void addEmp(EmployeeDTO employeeDTO);

    /**
     * 编辑员工信息
     *
     * @param employeeDTO
     * @return
     */
    void updateEmp(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据ID查询员工
     *
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 启用员工/禁用员工
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 修改员工密码
     *
     * @param passwordEditDTO
     */
    void editPassword(PasswordEditDTO passwordEditDTO);
}
