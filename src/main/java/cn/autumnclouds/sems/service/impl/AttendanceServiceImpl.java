package cn.autumnclouds.sems.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import cn.autumnclouds.sems.common.ErrorCode;
import cn.autumnclouds.sems.exception.ThrowUtils;
import cn.autumnclouds.sems.model.dto.attendance.AttendanceQueryRequest;
import cn.autumnclouds.sems.model.entity.Department;
import cn.autumnclouds.sems.model.entity.Employee;
import cn.autumnclouds.sems.model.entity.Salary;
import cn.autumnclouds.sems.service.EmployeeService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.autumnclouds.sems.model.entity.Attendance;
import cn.autumnclouds.sems.service.AttendanceService;
import cn.autumnclouds.sems.mapper.AttendanceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Oreki
 * @description 针对表【attendance(员工考勤表)】的数据库操作Service实现
 * @createDate 2023-04-19 11:21:54
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance>
        implements AttendanceService {

    @Resource
    private EmployeeService employeeService;

    @Override
    public Page<Attendance> listAttendancesPage(int currentPage, int pageSize, AttendanceQueryRequest attendanceQueryRequest) {
        if (attendanceQueryRequest == null) {
            return lambdaQuery().orderBy(true, false, Attendance::getDate)
                    .page(new Page<>(currentPage, pageSize));
        }
        String employeeName = attendanceQueryRequest.getEmployeeName();
        Long empno = attendanceQueryRequest.getEmpno();
        String sortField = attendanceQueryRequest.getSortField();
        boolean isAsc = attendanceQueryRequest.isAsc();
        LocalDate beginDate = attendanceQueryRequest.getBeginDate();
        LocalDate endDate = attendanceQueryRequest.getEndDate();
        LambdaQueryWrapper<Attendance> lambdaQueryWrapper;
        // 设置排序
        if (StrUtil.isBlank(sortField)) {
            // 设置默认按发放日期降序排序
            lambdaQueryWrapper = new LambdaQueryWrapper<Attendance>().orderByDesc(Attendance::getDate);
        } else {
            // 设置指定排序字段
            lambdaQueryWrapper = new QueryWrapper<Attendance>().orderBy(true, isAsc, sortField).lambda();
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
                lambdaQueryWrapper.in(Attendance::getEmployeeId, empIds);
            }
        }
        // 根据员工工号条件查询
        if (empno != null) {
            Employee employee = employeeService.getEmployeeByEmpno(empno);
            ThrowUtils.throwIf(employee == null, ErrorCode.PARAMS_ERROR, "员工不存在");
            lambdaQueryWrapper.eq(Attendance::getEmployeeId, employee.getEmployeeId());
        }
        // 设置其他查询条件
        lambdaQueryWrapper
                .ge(beginDate != null, Attendance::getDate, beginDate)
                .le(endDate != null, Attendance::getDate, endDate);
        return this.page(new Page<>(currentPage, pageSize), lambdaQueryWrapper);
    }

    @Override
    public boolean sign(Boolean isSignIn, Long employeeId) {
        if (isSignIn == null || employeeId == null) {
            return false;
        }
        return Boolean.TRUE.equals(isSignIn) ? signIn(employeeId) : signOut(employeeId);
    }

    private boolean signIn(Long employeeId) {
        //检查今日是否已经签到
        Attendance attendance = lambdaQuery().eq(Attendance::getEmployeeId, employeeId)
                .eq(Attendance::getDate, LocalDate.now())
                .one();
        ThrowUtils.throwIf(attendance != null, ErrorCode.OPERATION_ERROR, "今日已签到");
        //签到
        attendance = new Attendance();
        attendance.setDate(LocalDate.now());
        attendance.setSignInTime(LocalTime.now());
        attendance.setEmployeeId(employeeId);
        return this.save(attendance);
    }

    private boolean signOut(Long employeeId) {
        //检查今日是否已经签到
        Attendance attendance = lambdaQuery().eq(Attendance::getEmployeeId, employeeId)
                .eq(Attendance::getDate, LocalDate.now())
                .one();
        ThrowUtils.throwIf(attendance == null, ErrorCode.OPERATION_ERROR, "今日还未签到");
        //检查今日是否已经签退
        ThrowUtils.throwIf(attendance.getSignOutTime() != null, ErrorCode.OPERATION_ERROR, "今日已签退");
        //签退
        attendance.setSignOutTime(LocalTime.now());
        return this.updateById(attendance);
    }

}




