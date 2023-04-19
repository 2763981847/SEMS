package cn.autumnclouds.sems.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 * 员工信息表
 * @TableName employee
 */
@TableName(value ="employee")
@Data
public class Employee implements Serializable {
    /**
     * 员工编号
     */
    @TableId(value = "employee_id")
    private Long employeeId;

    /**
     * 部门编号
     */
    @TableField(value = "department_id")
    private Long departmentId;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 性别
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 身份证号
     */
    @TableField(value = "id_number")
    private String idNumber;

    /**
     * 手机号
     */
    @TableField(value = "phone_number")
    private String phoneNumber;

    /**
     * 职位
     */
    @TableField(value = "job_title")
    private String jobTitle;

    /**
     * 工号
     */
    @TableField(value = "empno")
    private Long empno;

    /**
     * 年龄
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 入职日期
     */
    @TableField(value = "enrollment_date")
    private LocalDate enrollmentDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}