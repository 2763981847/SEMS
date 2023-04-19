package cn.autumnclouds.sems.model.dto.attendance;

import cn.autumnclouds.sems.common.PageRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AttendanceQueryRequest extends PageRequest {
    /**
     * 员工名
     */
    private String employeeName;

    /**
     * 员工工号
     */
    private Long empno;


}
