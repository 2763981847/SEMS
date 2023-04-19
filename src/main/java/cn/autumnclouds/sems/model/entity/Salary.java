package cn.autumnclouds.sems.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 * 
 * @TableName salary
 */
@TableName(value ="salary")
@Data
public class Salary implements Serializable {
    /**
     * 工资编号
     */
    @TableId(value = "salary_id")
    private Long salaryId;

    /**
     * 员工编号
     */
    @TableField(value = "employee_id")
    private Long employeeId;

    /**
     * 发放日期
     */
    @TableField(value = "payday")
    private LocalDate payday;

    /**
     * 基本工资
     */
    @TableField(value = "base_salary")
    private Integer baseSalary;

    /**
     * 奖金
     */
    @TableField(value = "bonus")
    private Integer bonus;

    /**
     * 罚金
     */
    @TableField(value = "fine")
    private Integer fine;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}