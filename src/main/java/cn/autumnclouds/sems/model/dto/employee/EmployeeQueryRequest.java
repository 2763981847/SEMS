package cn.autumnclouds.sems.model.dto.employee;

import cn.autumnclouds.sems.common.PageRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeQueryRequest extends PageRequest {

    /**
     * 员工编号
     */
    private Long employeeId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 最小年龄
     */
    private Integer minAge;

    /**
     * 最大年龄
     */
    private Integer maxAge;


    /**
     * 性别  0-男 1-女
     */
    private Integer sex;

    /**
     * 职位
     */
    private String jobTitle;

    /**
     * 模糊查询字段
     */
    @ApiModelProperty("模糊查询字段(可支持根据工号、身份证号、手机号查询)")
    private String fuzzyQuery;

}
