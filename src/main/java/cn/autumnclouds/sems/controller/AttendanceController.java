package cn.autumnclouds.sems.controller;

import cn.autumnclouds.sems.common.Result;
import cn.autumnclouds.sems.model.dto.attendance.AttendanceQueryRequest;
import cn.autumnclouds.sems.model.entity.Attendance;
import cn.autumnclouds.sems.service.AttendanceService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @PostMapping("/sign/{isSignIn}/{employeeId}")
    @ApiOperation("签到 isSignIn: false:签退 true:签到")
    public Result<String> sign(@PathVariable("isSignIn") Boolean isSignIn,
                               @PathVariable("employeeId") Long employeeId) {
        boolean success = attendanceService.sign(isSignIn, employeeId);
        String msg = isSignIn ? "签到" : "签退";
        return success ? Result.success(msg + "成功") : Result.fail(msg + "失败");
    }

    @PostMapping
    public Result<String> addAttendance(@Valid @RequestBody Attendance attendance) {
        boolean success = attendanceService.save(attendance);
        return success ? Result.success("添加成功") : Result.fail("添加失败");
    }

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

    @PostMapping("/{current}/{size}")
    public Result<Page<Attendance>> listAttendances(@PathVariable("current") int currentPage,
                                                    @PathVariable("size") int pageSize,
                                                    @RequestBody(required = false) AttendanceQueryRequest attendanceQueryRequest) {
        Page<Attendance> attendancePage = attendanceService.listAttendancesPage(currentPage, pageSize, attendanceQueryRequest);
        return Result.success(attendancePage);
    }
}
