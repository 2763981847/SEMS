package cn.autumnclouds.sems.model.dto.employeeTransfer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@Data
public class EmployeeTransferAddRequest {

    /**
     * 员工编号
     */
    @NotNull(message = "员工编号不能为空")
    private Long employeeId;

    /**
     * 调动原因
     */
    private String transferReason;


    /**
     * 新职位
     */
    private String newJobTitle;

    /**
     * 迁入部门
     */
    private Long inDepId;

}
