package cn.autumnclouds.sems.controller;

import cn.autumnclouds.sems.common.Result;
import cn.autumnclouds.sems.model.dto.employeeTransfer.EmployeeTransferAddRequest;
import cn.autumnclouds.sems.model.dto.employeeTransfer.EmployeeTransferQueryRequest;
import cn.autumnclouds.sems.model.entity.EmployeeTransfer;
import cn.autumnclouds.sems.service.EmployeeTransferService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@RestController
@RequestMapping("/employee_transfer")
@Api(tags = "员工调动管理模块")
public class EmployeeTransferController {
    @Resource
    private EmployeeTransferService employeeTransferService;

    @PostMapping
    public Result<String> addEmployeeTransfer(@Valid @RequestBody EmployeeTransferAddRequest employeeTransferAddRequest) {
        boolean success = employeeTransferService.addEmployeeTransfer(employeeTransferAddRequest);
        return success ? Result.success("调动成功") : Result.fail("调动失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteEmployeeTransfer(@PathVariable("id") Long id) {
        boolean success = employeeTransferService.removeById(id);
        return success ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @PutMapping
    public Result<String> updateEmployeeTransfer(@Valid @RequestBody EmployeeTransfer employeeTransfer) {
        boolean success = employeeTransferService.updateById(employeeTransfer);
        return success ? Result.success("更新成功") : Result.fail("更新失败");
    }

    @GetMapping("/{id}")
    public Result<EmployeeTransfer> getEmployeeTransfer(@PathVariable("id") Long id) {
        EmployeeTransfer employeeTransfer = employeeTransferService.getById(id);
        return employeeTransfer != null ? Result.success(employeeTransfer) : Result.fail("查询失败");
    }

    @GetMapping("/{current}/{size}")
    public Result<Page<EmployeeTransfer>> listEmployeeTransfers(@PathVariable("current") int currentPage,
                                                                @PathVariable("size") int pageSize,
                                                                @ApiParam("查询条件") EmployeeTransferQueryRequest employeeTransferQueryRequest) {
        Page<EmployeeTransfer> employeeTransferPage = employeeTransferService.listEmployeeTransfersPage(currentPage, pageSize, employeeTransferQueryRequest);
        return Result.success(employeeTransferPage);
    }
}
