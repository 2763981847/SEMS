package cn.autumnclouds.sems.controller;

import cn.autumnclouds.sems.common.Result;
import cn.autumnclouds.sems.model.dto.attendance.AttendanceQueryRequest;
import cn.autumnclouds.sems.model.entity.Attendance;
import cn.autumnclouds.sems.service.AttendanceService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@RestController
@RequestMapping("/attendance")
@Validated
public class AttendanceController {
    @Resource
    private AttendanceService attendanceService;

    @DeleteMapping("/{id}")
    public Result<String> deleteAttendance(@PathVariable("id") Integer id) {
        boolean success = attendanceService.removeById(id);
        return success ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @PutMapping
    public Result<String> updateAttendance(@Valid @RequestBody Attendance attendance) {
        boolean success = attendanceService.updateById(attendance);
        return success ? Result.success("更新成功") : Result.fail("更新失败");
    }

    @GetMapping("/{id}")
    public Result<Attendance> getAttendance(@PathVariable("id") Integer id) {
        Attendance attendance = attendanceService.getById(id);
        return attendance != null ? Result.success(attendance) : Result.fail("查询失败");
    }

    @GetMapping("/{current}/{size}")
    public Result<Page<Attendance>> listAttendances(@PathVariable("current") int currentPage,
                                                    @PathVariable("size") int pageSize,
                                                    @RequestParam(required = false) AttendanceQueryRequest attendanceQueryRequest) {
        Page<Attendance> attendancePage = attendanceService.listAttendancesPage(currentPage, pageSize, attendanceQueryRequest);
        return Result.success(attendancePage);
    }
}
