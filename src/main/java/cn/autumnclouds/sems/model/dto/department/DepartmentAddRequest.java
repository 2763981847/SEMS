package cn.autumnclouds.sems.model.dto.department;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@Data
public class DepartmentAddRequest {

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String name;

    /**
     * 部门简介
     */
    private String introduction;
}
