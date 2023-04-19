package cn.autumnclouds.sems.controller;

import cn.autumnclouds.sems.common.Result;
import cn.autumnclouds.sems.model.dto.employeeTransfer.EmployeeTransferAddRequest;
import cn.autumnclouds.sems.model.dto.employeeTransfer.EmployeeTransferQueryRequest;
import cn.autumnclouds.sems.model.entity.EmployeeTransfer;
import cn.autumnclouds.sems.service.EmployeeTransferService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@RestController
@RequestMapping("/employee_transfer")
public class EmployeeTransferController {
    @Resource
    private EmployeeTransferService employeeTransferService;

    @PostMapping
    public Result<String> addEmployeeTransfer(@Valid @RequestBody EmployeeTransferAddRequest employeeTransferAddRequest) {
        boolean success = employeeTransferService.addEmployeeTransfer(employeeTransferAddRequest);
        return success ? Result.success("调动成功") : Result.fail("调动失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteEmployeeTransfer(@PathVariable("id") Integer id) {
        boolean success = employeeTransferService.removeById(id);
        return success ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @PutMapping
    public Result<String> updateEmployeeTransfer(@Valid @RequestBody EmployeeTransfer employeeTransfer) {
        boolean success = employeeTransferService.updateById(employeeTransfer);
        return success ? Result.success("更新成功") : Result.fail("更新失败");
    }

    @GetMapping("/{id}")
    public Result<EmployeeTransfer> getEmployeeTransfer(@PathVariable("id") Integer id) {
        EmployeeTransfer employeeTransfer = employeeTransferService.getById(id);
        return employeeTransfer != null ? Result.success(employeeTransfer) : Result.fail("查询失败");
    }

    @PostMapping("/{current}/{size}")
    public Result<Page<EmployeeTransfer>> listEmployeeTransfers(@PathVariable("current") int currentPage,
                                                                @PathVariable("size") int pageSize,
                                                                @RequestBody(required = false) EmployeeTransferQueryRequest employeeTransferQueryRequest) {
        Page<EmployeeTransfer> employeeTransferPage = employeeTransferService.listEmployeeTransfersPage(currentPage, pageSize, employeeTransferQueryRequest);
        return Result.success(employeeTransferPage);
    }
}
