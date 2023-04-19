package cn.autumnclouds.sems.service.impl;

import cn.autumnclouds.sems.common.ErrorCode;
import cn.autumnclouds.sems.exception.ThrowUtils;
import cn.autumnclouds.sems.model.dto.employee.EmployeeAddRequest;
import cn.autumnclouds.sems.model.dto.employee.EmployeeQueryRequest;
import cn.autumnclouds.sems.model.entity.Department;
import cn.autumnclouds.sems.service.DepartmentService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.autumnclouds.sems.model.entity.Employee;
import cn.autumnclouds.sems.service.EmployeeService;
import cn.autumnclouds.sems.mapper.EmployeeMapper;
import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Oreki
 * @description 针对表【employee(员工信息表)】的数据库操作Service实现
 * @createDate 2023-04-19 11:20:44
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
        implements EmployeeService {
    @Resource
    DepartmentService departmentService;

    @Override
    public boolean addEmployee(EmployeeAddRequest employeeAddRequest) {
        String idNumber = employeeAddRequest.getIdNumber();
        String phoneNumber = employeeAddRequest.getPhoneNumber();
        Long empno = employeeAddRequest.getEmpno();
        // 检查身份证号格式
        ThrowUtils.throwIf(!IdcardUtil.isValidCard(idNumber), ErrorCode.PARAMS_ERROR, "身份证号不合法");
        // 检查手机号格式
        ThrowUtils.throwIf(!PhoneUtil.isMobile(phoneNumber), ErrorCode.PARAMS_ERROR, "手机号不合法");
        // 检查工号是否重复
        Long count = this.lambdaQuery().eq(Employee::getEmpno, empno).select(Employee::getEmpno).count();
        ThrowUtils.throwIf(count > 0, ErrorCode.PARAMS_ERROR, "工号重复");
        //检查部门是否真实存在
        Long departmentId = employeeAddRequest.getDepartmentId();
        ThrowUtils.throwIf(departmentService.getById(departmentId) == null, ErrorCode.PARAMS_ERROR, "部门不存在");
        // 设置员工基本属性
        Employee employee = new Employee();
        BeanUtil.copyProperties(employeeAddRequest, employee);
        // 设置入职时间
        employee.setEnrollmentDate(LocalDate.now());
        return this.save(employee);
    }


    @Override
    public Page<Employee> listEmployeesPage(int currentPage, int pageSize, EmployeeQueryRequest employeeQueryRequest) {
        if (employeeQueryRequest == null) {
            return lambdaQuery().orderBy(true, true, Employee::getEmployeeId)
                    .page(new Page<>(currentPage, pageSize));
        }
        Long employeeId = employeeQueryRequest.getEmployeeId();
        String name = employeeQueryRequest.getName();
        Integer minAge = employeeQueryRequest.getMinAge();
        Integer maxAge = employeeQueryRequest.getMaxAge();
        if (minAge != null && maxAge != null && minAge > maxAge) {
            return Page.of(currentPage, pageSize, 0);
        }
        String departmentName = employeeQueryRequest.getDepartmentName();
        Integer sex = employeeQueryRequest.getSex();
        String jobTitle = employeeQueryRequest.getJobTitle();
        String sortField = employeeQueryRequest.getSortField();
        boolean isAsc = employeeQueryRequest.isAsc();
        LocalDate beginDate = employeeQueryRequest.getBeginDate();
        LocalDate endDate = employeeQueryRequest.getEndDate();
        // 模糊查询支持根据工号、身份证号、手机号查询
        String fuzzyQuery = employeeQueryRequest.getFuzzyQuery();
        LambdaQueryWrapper<Employee> lambdaQueryWrapper;
        // 设置排序
        if (StrUtil.isBlank(sortField)) {
            // 默认按照员工id升序排序
            lambdaQueryWrapper = new LambdaQueryWrapper<Employee>().orderByAsc(Employee::getEmployeeId);
        } else {
            // 设置指定排序字段
            lambdaQueryWrapper = new QueryWrapper<Employee>().orderBy(true, isAsc, sortField).lambda();
        }
        // 根据部门名称条件查询
        if (StrUtil.isNotBlank(departmentName)) {
            List<Long> depIds = departmentService.listDepartmentsByName(departmentName)
                    .stream()
                    .map(Department::getDepartmentId)
                    .collect(Collectors.toList());
            if (depIds.isEmpty()) {
                return Page.of(currentPage, pageSize, 0);
            } else {
                lambdaQueryWrapper.in(Employee::getDepartmentId, depIds);
            }
        }
        //其余条件查询
        lambdaQueryWrapper
                .eq(employeeId != null, Employee::getEmployeeId, employeeId)
                .like(StringUtils.isNotBlank(name), Employee::getName, name)
                .ge(minAge != null, Employee::getAge, minAge)
                .le(maxAge != null, Employee::getAge, maxAge)
                .eq(sex != null, Employee::getSex, sex)
                .like(StringUtils.isNotBlank(jobTitle), Employee::getJobTitle, jobTitle)
                .ge(beginDate != null, Employee::getEnrollmentDate, beginDate)
                .le(endDate != null, Employee::getEnrollmentDate, endDate);
        //模糊查询
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(fuzzyQuery), Employee::getEmpno, fuzzyQuery)
                .or()
                .eq(StringUtils.isNotBlank(fuzzyQuery), Employee::getIdNumber, fuzzyQuery)
                .or()
                .eq(StringUtils.isNotBlank(fuzzyQuery), Employee::getPhoneNumber, fuzzyQuery);
        return this.page(new Page<>(currentPage, pageSize), lambdaQueryWrapper);
    }

    @Override
    public List<Employee> listEmployeesByName(String name) {
        return StrUtil.isBlank(name) ? Collections.emptyList() : this.lambdaQuery().like(Employee::getName, name).list();
    }

    @Override
    public Employee getEmployeeByEmpno(Long empno) {
        return empno == null ? null : this.lambdaQuery().eq(Employee::getEmpno, empno).one();
    }

}




