package cn.autumnclouds.sems.model.dto.salary;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@Data
public class SalaryAddRequest {

    /**
     * 员工编号
     */
    @NotNull(message = "员工编号不能为空")
    private Long employeeId;


    /**
     * 基本工资
     */
    @Range(min = 0, message = "基本工资不得小于{min}")
    private Integer baseSalary;

    /**
     * 奖金
     */
    @Range(min = 0, message = "奖金不得小于{min}")
    private Integer bonus;

    /**
     * 罚金
     */
    @Range(min = 0, message = "罚金不得小于{min}")
    private Integer fine;

}
