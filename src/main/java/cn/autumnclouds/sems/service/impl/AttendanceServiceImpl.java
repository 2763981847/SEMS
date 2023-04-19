package cn.autumnclouds.sems.service.impl;

import java.time.LocalDate;

import cn.autumnclouds.sems.model.dto.attendance.AttendanceQueryRequest;
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
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
        String employeeName = attendanceQueryRequest.getEmployeeName();
        Long empno = attendanceQueryRequest.getEmpno();
        LocalDate date = attendanceQueryRequest.getDate();
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
        // 设置查询条件
        lambdaQueryWrapper
                .in(StrUtil.isNotBlank(employeeName),
                        Attendance::getEmployeeId,
                        employeeService.listEmployeesByName(employeeName)
                                .stream()
                                .map(Employee::getEmployeeId)
                                .toArray())
                .eq(empno != null, Attendance::getEmployeeId, employeeService.getEmployeeByEmpno(empno).getEmployeeId())
                .ge(beginDate != null, Attendance::getDate, beginDate)
                .le(endDate != null, Attendance::getDate, endDate);
        return this.page(new Page<>(currentPage, pageSize), lambdaQueryWrapper);
    }
}




