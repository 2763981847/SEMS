package cn.autumnclouds.sems.service.impl;

import java.time.LocalDate;
import java.util.stream.Collectors;

import cn.autumnclouds.sems.model.dto.employeeTransfer.EmployeeTransferQueryRequest;
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
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
        lambdaQueryWrapper
                .in(StrUtil.isNotBlank(employeeName),
                        EmployeeTransfer::getEmployeeId,
                        employeeService.listEmployeesByName(employeeName)
                                .stream().map(Employee::getEmployeeId).toArray())
                .eq(empno != null, EmployeeTransfer::getEmployeeId, employeeService.getEmployeeByEmpno(empno).getEmployeeId())
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
}




