package cn.autumnclouds.sems.controller;

import cn.autumnclouds.sems.common.Result;
import cn.autumnclouds.sems.model.dto.department.DepartmentAddRequest;
import cn.autumnclouds.sems.model.dto.employee.EmployeeAddRequest;
import cn.autumnclouds.sems.model.dto.employee.EmployeeQueryRequest;
import cn.autumnclouds.sems.model.entity.Department;
import cn.autumnclouds.sems.model.entity.Employee;
import cn.autumnclouds.sems.service.DepartmentService;
import cn.autumnclouds.sems.service.EmployeeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @PostMapping
    public Result<String> addDepartment(@Valid @RequestBody EmployeeAddRequest employeeAddRequest) {
        boolean success = employeeService.addEmployee(employeeAddRequest);
        return success ? Result.success("添加成功") : Result.fail("添加失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteDepartment(@PathVariable("id") Integer id) {
        boolean success = employeeService.removeById(id);
        return success ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @PutMapping
    public Result<String> updateDepartment(@Valid @RequestBody Employee employee) {
        boolean success = employeeService.updateById(employee);
        return success ? Result.success("更新成功") : Result.fail("更新失败");
    }

    @GetMapping("/{id}")
    public Result<Employee> getDepartment(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getById(id);
        return employee != null ? Result.success(employee) : Result.fail("查询失败");
    }

    @PostMapping("/{current}/{size}")
    public Result<Page<Employee>> listDepartments(@PathVariable("current") int currentPage,
                                                  @PathVariable("size") int pageSize,
                                                  @RequestBody(required = false) EmployeeQueryRequest employeeQueryRequest) {
        Page<Employee> departmentPage = employeeService.listEmployeesPage(currentPage, pageSize, employeeQueryRequest);
        return Result.success(departmentPage);
    }
}
