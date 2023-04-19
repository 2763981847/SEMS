package cn.autumnclouds.sems.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import cn.autumnclouds.sems.common.ErrorCode;
import cn.autumnclouds.sems.exception.ThrowUtils;
import cn.autumnclouds.sems.model.dto.employeeTransfer.EmployeeTransferAddRequest;
import cn.autumnclouds.sems.model.dto.employeeTransfer.EmployeeTransferQueryRequest;
import cn.autumnclouds.sems.model.entity.Attendance;
import cn.autumnclouds.sems.model.entity.Employee;
import cn.autumnclouds.sems.model.entity.Salary;
import cn.autumnclouds.sems.service.EmployeeService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.autumnclouds.sems.model.entity.EmployeeTransfer;
import cn.autumnclouds.sems.service.EmployeeTransferService;
import cn.autumnclouds.sems.mapper.EmployeeTransferMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Oreki
 * @description 针对表【employee_transfer(员工调动记录表)】的数据库操作Service实现
 * @createDate 2023-04-19 11:21:45
 */
@Service
public class EmployeeTransferServiceImpl extends ServiceImpl<EmployeeTransferMapper, EmployeeTransfer>
        implements EmployeeTransferService {

    @Resource
    EmployeeService employeeService;

    @Override
    public Page<EmployeeTransfer> listEmployeeTransfersPage(int currentPage,
                                                            int pageSize,
                                                            EmployeeTransferQueryRequest employeeTransferQueryRequest) {
        if (employeeTransferQueryRequest == null) {
            return lambdaQuery().orderBy(true, false, EmployeeTransfer::getTransferDate)
                    .page(new Page<>(currentPage, pageSize));
        }
        String employeeName = employeeTransferQueryRequest.getEmployeeName();
        Long empno = employeeTransferQueryRequest.getEmpno();
        String transferReason = employeeTransferQueryRequest.getTransferReason();
        String outDepName = employeeTransferQueryRequest.getOutDepName();
        String inDepName = employeeTransferQueryRequest.getInDepName();
        String sortField = employeeTransferQueryRequest.getSortField();
        boolean isAsc = employeeTransferQueryRequest.isAsc();
        LocalDate beginDate = employeeTransferQueryRequest.getBeginDate();
        LocalDate endDate = employeeTransferQueryRequest.getEndDate();
        LambdaQueryWrapper<EmployeeTransfer> lambdaQueryWrapper;
        // 设置排序
        if (StrUtil.isBlank(sortField)) {
            // 设置默认按调动日期降序排序
            lambdaQueryWrapper = new LambdaQueryWrapper<EmployeeTransfer>().orderByDesc(EmployeeTransfer::getTransferDate);
        } else {
            // 设置指定排序字段
            lambdaQueryWrapper = new QueryWrapper<EmployeeTransfer>().orderBy(true, isAsc, sortField).lambda();
        }
        // 根据员工名称条件查询
        if (StrUtil.isNotBlank(employeeName)) {
            List<Long> empIds = employeeService.listEmployeesByName(employeeName)
                    .stream()
                    .map(Employee::getEmployeeId)
                    .collect(Collectors.toList());
            if (empIds.isEmpty()) {
                return Page.of(currentPage, pageSize, 0);
            } else {
                lambdaQueryWrapper.in(EmployeeTransfer::getEmployeeId, empIds);
            }
        }
        // 根据员工工号条件查询
        if (empno != null) {
            Employee employee = employeeService.getEmployeeByEmpno(empno);
            ThrowUtils.throwIf(employee == null, ErrorCode.PARAMS_ERROR, "员工不存在");
            lambdaQueryWrapper.eq(EmployeeTransfer::getEmployeeId, employee.getEmployeeId());
        }
        //设置其他查询条件
        lambdaQueryWrapper
                .like(StrUtil.isNotBlank(transferReason), EmployeeTransfer::getTransferReason, transferReason)
                .in(StrUtil.isNotBlank(outDepName), EmployeeTransfer::getOutDepId,
                        employeeService.listEmployeesByName(outDepName)
                                .stream().map(Employee::getEmployeeId).toArray())
                .in(StrUtil.isNotBlank(inDepName), EmployeeTransfer::getInDepId,
                        employeeService.listEmployeesByName(inDepName)
                                .stream().map(Employee::getEmployeeId).toArray())
                .ge(beginDate != null, EmployeeTransfer::getTransferDate, beginDate)
                .le(endDate != null, EmployeeTransfer::getTransferDate, endDate);
        return this.page(new Page<>(currentPage, pageSize), lambdaQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addEmployeeTransfer(EmployeeTransferAddRequest employeeTransferAddRequest) {
        Long employeeId = employeeTransferAddRequest.getEmployeeId();
        //查出要调动的员工信息
        Employee employee = employeeService.getById(employeeId);
        ThrowUtils.throwIf(employee == null, ErrorCode.PARAMS_ERROR, "员工不存在");
        Long outDepId = employee.getEmployeeId();
        String originalJobTitle = employee.getJobTitle();
        //获取调动信息
        String transferReason = employeeTransferAddRequest.getTransferReason();
        String newJobTitle = employeeTransferAddRequest.getNewJobTitle();
        Long inDepId = employeeTransferAddRequest.getInDepId();
        //创建调动记录
        EmployeeTransfer employeeTransfer = new EmployeeTransfer();
        employeeTransfer.setEmployeeId(employeeId);
        employeeTransfer.setTransferReason(transferReason);
        employeeTransfer.setOriginalJobTitle(originalJobTitle);
        employeeTransfer.setNewJobTitle(newJobTitle);
        employeeTransfer.setOutDepId(outDepId);
        employeeTransfer.setInDepId(inDepId);
        employeeTransfer.setTransferDate(LocalDate.now());
        //修改员工信息
        employee.setJobTitle(newJobTitle);
        employee.setDepartmentId(inDepId);
        return this.save(employeeTransfer) && employeeService.updateById(employee);
    }
}




