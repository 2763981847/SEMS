package cn.autumnclouds.sems.service;

import cn.autumnclouds.sems.model.dto.attendance.AttendanceQueryRequest;
import cn.autumnclouds.sems.model.entity.Attendance;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Oreki
* @description 针对表【attendance(员工考勤表)】的数据库操作Service
* @createDate 2023-04-19 11:21:54
*/
public interface AttendanceService extends IService<Attendance> {

    Page<Attendance> listAttendancesPage(int currentPage, int pageSize, AttendanceQueryRequest attendanceQueryRequest);
}
