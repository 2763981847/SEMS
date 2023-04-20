package cn.autumnclouds.sems.controller;

import cn.autumnclouds.sems.common.Result;
import cn.autumnclouds.sems.model.dto.employee.EmployeeAddRequest;
import cn.autumnclouds.sems.model.dto.employee.EmployeeAddRequest;
import cn.autumnclouds.sems.model.dto.employee.EmployeeQueryRequest;
import cn.autumnclouds.sems.model.entity.Employee;
import cn.autumnclouds.sems.model.entity.Employee;
import cn.autumnclouds.sems.service.EmployeeService;
import cn.autumnclouds.sems.service.EmployeeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "员工管理模块")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @PostMapping
    public Result<String> addEmployee(@Valid @RequestBody EmployeeAddRequest employeeAddRequest) {
        boolean success = employeeService.addEmployee(employeeAddRequest);
        return success ? Result.success("添加成功") : Result.fail("添加失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteEmployee(@PathVariable("id") Long id) {
        boolean success = employeeService.removeById(id);
        return success ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @PutMapping
    public Result<String> updateEmployee(@Valid @RequestBody Employee employee) {
        boolean success = employeeService.updateById(employee);
        return success ? Result.success("更新成功") : Result.fail("更新失败");
    }

    @GetMapping("/{id}")
    public Result<Employee> getEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        return employee != null ? Result.success(employee) : Result.fail("查询失败");
    }

    @GetMapping("/{current}/{size}")
    public Result<Page<Employee>> listEmployees(@PathVariable("current") int currentPage,
                                                @PathVariable("size") int pageSize,
                                                @ApiParam("查询条件") EmployeeQueryRequest employeeQueryRequest) {
        Page<Employee> employeePage = employeeService.listEmployeesPage(currentPage, pageSize, employeeQueryRequest);
        return Result.success(employeePage);
    }

}
