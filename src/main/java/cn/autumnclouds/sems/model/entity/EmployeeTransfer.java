package cn.autumnclouds.sems.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 * 员工调动记录表
 * @TableName employee_transfer
 */
@TableName(value ="employee_transfer")
@Data
public class EmployeeTransfer implements Serializable {
    /**
     * 调动编号
     */
    @TableId(value = "transfer_id")
    private Long transferId;

    /**
     * 员工编号
     */
    @TableField(value = "employee_id")
    private Long employeeId;

    /**
     * 调动原因
     */
    @TableField(value = "transfer_reason")
    private String transferReason;

    /**
     * 原职位
     */
    @TableField(value = "original_job_title")
    private String originalJobTitle;

    /**
     * 新职位
     */
    @TableField(value = "new_job_title")
    private String newJobTitle;

    /**
     * 迁出部门
     */
    @TableField(value = "out_dep_id")
    private Long outDepId;

    /**
     * 迁入部门
     */
    @TableField(value = "in_dep_id")
    private Long inDepId;

    /**
     * 调动日期
     */
    @TableField(value = "transfer_date")
    private LocalDate transferDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}