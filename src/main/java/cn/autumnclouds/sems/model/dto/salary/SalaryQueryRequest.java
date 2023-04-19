package cn.autumnclouds.sems.model.dto.salary;

import cn.autumnclouds.sems.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SalaryQueryRequest extends PageRequest {

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 员工工号
     */
    private Long empno;

    /**
     * 最低基本工资
     */
    private Integer minBaseSalary;

    /**
     * 最高基本工资
     */
    private Integer maxBaseSalary;

    /**
     * 最低奖金
     */
    private Integer minBonus;

    /**
     * 最高奖金
     */
    private Integer maxBonus;

    /**
     * 最低罚金
     */
    private Integer minFine;

    /**
     * 最高罚金
     */
    private Integer maxFine;

}
