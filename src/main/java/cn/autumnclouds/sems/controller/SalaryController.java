package cn.autumnclouds.sems.controller;

import cn.autumnclouds.sems.common.Result;
import cn.autumnclouds.sems.model.dto.salary.SalaryAddRequest;
import cn.autumnclouds.sems.model.dto.salary.SalaryQueryRequest;
import cn.autumnclouds.sems.model.entity.Salary;
import cn.autumnclouds.sems.service.SalaryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@RestController
@RequestMapping("/salary")
@Validated
public class SalaryController {
    @Resource
    private SalaryService salaryService;

    @PostMapping
    @ApiOperation(value = "发放工资")
    public Result<String> addSalary(@Valid @RequestBody SalaryAddRequest salaryAddRequest) {
        boolean success = salaryService.addSalary(salaryAddRequest);
        return success ? Result.success("发放成功") : Result.fail("发放失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteSalary(@PathVariable("id") Integer id) {
        boolean success = salaryService.removeById(id);
        return success ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @PutMapping
    public Result<String> updateSalary(@Valid @RequestBody Salary salary) {
        boolean success = salaryService.updateById(salary);
        return success ? Result.success("更新成功") : Result.fail("更新失败");
    }

    @GetMapping("/{id}")
    public Result<Salary> getSalary(@PathVariable("id") Integer id) {
        Salary salary = salaryService.getById(id);
        return salary != null ? Result.success(salary) : Result.fail("查询失败");
    }

    @GetMapping("/{current}/{size}")
    public Result<Page<Salary>> listSalaries(@PathVariable("current") int currentPage,
                                             @PathVariable("size") int pageSize,
                                             SalaryQueryRequest salaryQueryRequest) {
        Page<Salary> salaryPage = salaryService.listSalariesPage(currentPage, pageSize, salaryQueryRequest);
        return Result.success(salaryPage);
    }
}
