package cn.autumnclouds.sems.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

/**
 * 员工考勤表
 * @TableName attendance
 */
@TableName(value ="attendance")
@Data
public class Attendance implements Serializable {
    /**
     * 考勤编号
     */
    @TableId(value = "attendance_id")
    private Long attendanceId;

    /**
     * 员工编号
     */
    @TableField(value = "employee_id")
    private Long employeeId;

    /**
     * 考勤日期
     */
    @TableField(value = "date")
    private LocalDate date;

    /**
     * 签到时间
     */
    @TableField(value = "sign_in_time")
    private LocalTime signInTime;

    /**
     * 签退时间
     */
    @TableField(value = "sign_out_time")
    private LocalTime signOutTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}