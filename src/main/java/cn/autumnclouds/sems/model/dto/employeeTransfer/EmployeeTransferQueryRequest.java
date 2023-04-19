package cn.autumnclouds.sems.model.dto.employeeTransfer;

import cn.autumnclouds.sems.common.PageRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeTransferQueryRequest extends PageRequest {

    /**
     * 员工名
     */
    private String employeeName;

    /**
     * 员工工号
     */

    private Long empno;

    /**
     * 调动原因
     */
    private String transferReason;


    /**
     * 迁出部门
     */
    private String outDepName;

    /**
     * 迁入部门
     */
    private String inDepName;
}
