package cn.autumnclouds.sems.common;

import lombok.Data;

import java.time.LocalDate;

/**
 * 分页请求
 *
 * @author oreki
 */
@Data
public class PageRequest {
    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private boolean isAsc = true;

    /**
     * 开始时间
     */
    private LocalDate beginDate;

    /**
     * 结束时间
     */
    private LocalDate endDate;
}
