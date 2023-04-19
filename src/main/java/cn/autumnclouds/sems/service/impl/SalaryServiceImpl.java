package cn.autumnclouds.sems.service.impl;

import cn.autumnclouds.sems.common.ErrorCode;
import cn.autumnclouds.sems.exception.ThrowUtils;
import cn.autumnclouds.sems.model.dto.salary.SalaryAddRequest;
import cn.autumnclouds.sems.model.dto.salary.SalaryQueryRequest;
import cn.autumnclouds.sems.model.entity.Employee;
import cn.autumnclouds.sems.model.entity.EmployeeTransfer;
import cn.autumnclouds.sems.service.EmployeeService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.autumnclouds.sems.model.entity.Salary;
import cn.autumnclouds.sems.service.SalaryService;
import cn.autumnclouds.sems.mapper.SalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Oreki
 * @description 针对表【salary】的数据库操作Service实现
 * @createDate 2023-04-19 11:21:57
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {

    @Resource
    EmployeeService employeeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addSalary(SalaryAddRequest salaryAddRequest) {
        Salary salary = new Salary();
        BeanUtil.copyProperties(salaryAddRequest, salary);
        salary.setPayday(LocalDate.now());
        return this.save(salary);
    }

    @Override
    public Page<Salary> listSalariesPage(int currentPage, int pageSize, SalaryQueryRequest salaryQueryRequest) {
        if (salaryQueryRequest == null) {
            return lambdaQuery().orderBy(true, false, Salary::getPayday)
                    .page(new Page<>(currentPage, pageSize));
        }
        String employeeName = salaryQueryRequest.getEmployeeName();
        Long empno = salaryQueryRequest.getEmpno();
        Page<Salary> emptyPage = new Page<>(currentPage, pageSize, 0);
        Integer minBaseSalary = salaryQueryRequest.getMinBaseSalary();
        Integer maxBaseSalary = salaryQueryRequest.getMaxBaseSalary();
        if (minBaseSalary != null && maxBaseSalary != null && minBaseSalary > maxBaseSalary) {
            return emptyPage;
        }
        Integer minBonus = salaryQueryRequest.getMinBonus();
        Integer maxBonus = salaryQueryRequest.getMaxBonus();
        if (minBonus != null && maxBonus != null && minBonus > maxBonus) {
            return emptyPage;
        }
        Integer minFine = salaryQueryRequest.getMinFine();
        Integer maxFine = salaryQueryRequest.getMaxFine();
        if (minFine != null && maxFine != null && minFine > maxFine) {
            return emptyPage;
        }
        LocalDate beginPayDay = salaryQueryRequest.getBeginDate();
        LocalDate endPayDay = salaryQueryRequest.getEndDate();
        String sortField = salaryQueryRequest.getSortField();
        boolean isAsc = salaryQueryRequest.isAsc();
        if (beginPayDay != null && endPayDay != null && beginPayDay.isAfter(endPayDay)) {
            return emptyPage;
        }
        LambdaQueryWrapper<Salary> lambdaQueryWrapper;
        // 设置排序
        if (StrUtil.isBlank(sortField)) {
            // 设置默认按发放日期降序排序
            lambdaQueryWrapper = new LambdaQueryWrapper<Salary>().orderByDesc(Salary::getPayday);
        } else {
            // 设置指定排序字段
            lambdaQueryWrapper = new QueryWrapper<Salary>().orderBy(true, isAsc, sortField).lambda();
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
                lambdaQueryWrapper.in(Salary::getEmployeeId, empIds);
            }
        }
        // 根据员工工号条件查询
        if (empno != null) {
            Employee employee = employeeService.getEmployeeByEmpno(empno);
            ThrowUtils.throwIf(employee == null, ErrorCode.PARAMS_ERROR, "员工不存在");
            lambdaQueryWrapper.eq(Salary::getEmployeeId, employee.getEmployeeId());
        }
        //设置其他条件
        lambdaQueryWrapper
                .ge(minBaseSalary != null, Salary::getBaseSalary, minBaseSalary)
                .le(maxBaseSalary != null, Salary::getBaseSalary, maxBaseSalary)
                .ge(minBonus != null, Salary::getBonus, minBonus)
                .le(maxBonus != null, Salary::getBonus, maxBonus)
                .ge(minFine != null, Salary::getFine, minFine)
                .le(maxFine != null, Salary::getFine, maxFine)
                .ge(beginPayDay != null, Salary::getPayday, beginPayDay)
                .le(endPayDay != null, Salary::getPayday, endPayDay);
        return this.page(new Page<>(currentPage, pageSize), lambdaQueryWrapper);
    }
}




