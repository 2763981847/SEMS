package cn.autumnclouds.sems.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 * 部门信息表
 * @TableName department
 */
@TableName(value ="department")
@Data
public class Department implements Serializable {
    /**
     * 部门编号
     */
    @TableId(value = "department_id")
    private Long departmentId;

    /**
     * 部门名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 部门简介
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 部门成立时间
     */
    @TableField(value = "establishment_date")
    private LocalDate establishmentDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}