package cn.autumnclouds.sems.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 分页请求
 *
 * @author oreki
 */
@Data
@ApiModel("分页请求对象")
public class PageRequest {
    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    @ApiModelProperty("是否升序（默认升序）")
    private boolean isAsc = true;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private LocalDate beginDate;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private LocalDate endDate;
}
