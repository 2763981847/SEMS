package cn.autumnclouds.sems.service;

import cn.autumnclouds.sems.model.dto.employee.EmployeeAddRequest;
import cn.autumnclouds.sems.model.dto.employee.EmployeeQueryRequest;
import cn.autumnclouds.sems.model.entity.Employee;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Oreki
* @description 针对表【employee(员工信息表)】的数据库操作Service
* @createDate 2023-04-19 11:20:44
*/
public interface EmployeeService extends IService<Employee> {

    boolean addEmployee(EmployeeAddRequest employeeAddRequest);

    Page<Employee> listEmployeesPage(int currentPage, int pageSize, EmployeeQueryRequest employeeQueryRequest);

    List<Employee> listEmployeesByName(String name);

    Employee getEmployeeByEmpno(Long empno);
}
