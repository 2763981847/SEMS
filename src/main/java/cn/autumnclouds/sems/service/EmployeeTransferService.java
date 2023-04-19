package cn.autumnclouds.sems.service;

import cn.autumnclouds.sems.model.dto.employeeTransfer.EmployeeTransferQueryRequest;
import cn.autumnclouds.sems.model.entity.EmployeeTransfer;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Oreki
* @description 针对表【employee_transfer(员工调动记录表)】的数据库操作Service
* @createDate 2023-04-19 11:21:45
*/
public interface EmployeeTransferService extends IService<EmployeeTransfer> {

    Page<EmployeeTransfer> listEmployeeTransfersPage(int currentPage, int pageSize, EmployeeTransferQueryRequest employeeTransferQueryRequest);
}
