package cn.autumnclouds.sems.service.impl;

import cn.autumnclouds.sems.common.ErrorCode;
import cn.autumnclouds.sems.exception.ThrowUtils;
import cn.autumnclouds.sems.model.dto.department.DepartmentAddRequest;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.autumnclouds.sems.model.entity.Department;
import cn.autumnclouds.sems.service.DepartmentService;
import cn.autumnclouds.sems.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @author Oreki
 * @description 针对表【department(部门信息表)】的数据库操作Service实现
 * @createDate 2023-04-19 11:17:50
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
        implements DepartmentService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDepartment(DepartmentAddRequest departmentAddRequest) {
        Department department = new Department();
        BeanUtil.copyProperties(departmentAddRequest, department);
        department.setEstablishmentDate(LocalDate.now());
        return this.save(department);
    }

    @Override
    public Page<Department> listDepartmentsPage(int currentPage, int pageSize, Department department) {
        if (department == null) {
            return lambdaQuery().orderBy(true, true, Department::getDepartmentId)
                    .page(new Page<>(currentPage, pageSize));
        }
        Long departmentId = department.getDepartmentId();
        String name = department.getName();
        String introduction = department.getIntroduction();
        return lambdaQuery()
                .eq(departmentId != null, Department::getDepartmentId, departmentId)
                .like(name != null, Department::getName, name)
                .like(introduction != null, Department::getIntroduction, introduction)
                .orderBy(true, true, Department::getDepartmentId)
                .page(new Page<>(currentPage, pageSize));
    }

    @Override
    public List<Department> listDepartmentsByName(String name) {
        if (StrUtil.isBlank(name)) {
            return Collections.emptyList();
        }
        return lambdaQuery().like(Department::getName, name).list();
    }

}




