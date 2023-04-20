package cn.autumnclouds.sems.controller;

import cn.autumnclouds.sems.common.Result;
import cn.autumnclouds.sems.model.dto.department.DepartmentAddRequest;
import cn.autumnclouds.sems.model.entity.Department;
import cn.autumnclouds.sems.service.DepartmentService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@RestController
@RequestMapping("/department")
@Validated
@Api(tags = "部门管理模块")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @PostMapping
    public Result<String> addDepartment(@Valid @RequestBody DepartmentAddRequest departmentAddRequest) {
        boolean success = departmentService.addDepartment(departmentAddRequest);
        return success ? Result.success("添加成功") : Result.fail("添加失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteDepartment(@PathVariable("id") Long id) {
        boolean success = departmentService.removeById(id);
        return success ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @PutMapping
    public Result<String> updateDepartment(@Valid @RequestBody Department department) {
        boolean success = departmentService.updateById(department);
        return success ? Result.success("更新成功") : Result.fail("更新失败");
    }

    @GetMapping("/{id}")
    public Result<Department> getDepartment(@PathVariable("id") Long id) {
        Department department = departmentService.getById(id);
        return department != null ? Result.success(department) : Result.fail("查询失败");
    }

    @GetMapping("/{current}/{size}")
    public Result<Page<Department>> listDepartments(@PathVariable("current") int currentPage,
                                                    @PathVariable("size") int pageSize,
                                                    @ApiParam("查询条件") Department department) {
        Page<Department> departmentPage = departmentService.listDepartmentsPage(currentPage, pageSize, department);
        return Result.success(departmentPage);
    }


}
