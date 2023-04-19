package cn.autumnclouds.sems.model.dto.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@Data
public class EmployeeAddRequest {

    /**
     * 部门编号
     */
    private Long departmentId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 性别  0-男 1-女
     */
    private Integer sex;

    /**
     * 年龄
     */
    @Range(min = 0, max = 150, message = "年龄范围为0-150岁")
    private Integer age;

    /**
     * 身份证号
     */
    @NotNull(message = "身份证号不能为空")
    private String idNumber;


    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 职位
     */
    private String jobTitle;

    /**
     * 工号
     */
    @NotNull(message = "工号不能为空")
    private Long empno;
}
