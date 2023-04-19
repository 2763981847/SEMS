package cn.autumnclouds.sems.service;

import cn.autumnclouds.sems.model.dto.salary.SalaryAddRequest;
import cn.autumnclouds.sems.model.dto.salary.SalaryQueryRequest;
import cn.autumnclouds.sems.model.entity.Salary;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Oreki
* @description 针对表【salary】的数据库操作Service
* @createDate 2023-04-19 11:21:57
*/
public interface SalaryService extends IService<Salary> {

    boolean addSalary(SalaryAddRequest salaryAddRequest);

    Page<Salary> listSalariesPage(int currentPage, int pageSize, SalaryQueryRequest salaryQueryRequest);
}
