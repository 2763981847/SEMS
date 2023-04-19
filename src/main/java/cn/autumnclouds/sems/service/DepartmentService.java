package cn.autumnclouds.sems.service;

import cn.autumnclouds.sems.model.dto.department.DepartmentAddRequest;
import cn.autumnclouds.sems.model.entity.Department;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Oreki
 * @description 针对表【department(部门信息表)】的数据库操作Service
 * @createDate 2023-04-19 11:17:50
 */
public interface DepartmentService extends IService<Department> {

    boolean addDepartment(DepartmentAddRequest departmentAddRequest);

    Page<Department> listDepartmentsPage(int currentPage, int pageSize, Department department);

    List<Department> listDepartmentsByName(String name);
}
