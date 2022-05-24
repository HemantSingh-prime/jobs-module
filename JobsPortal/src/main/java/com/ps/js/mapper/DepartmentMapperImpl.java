package com.ps.js.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.ps.js.entity.Department;
import com.ps.js.payload.DepartmentPayload;

/**
 * 
 * @author Hemant
 *
 */
@Component
public class DepartmentMapperImpl implements DepartmentMapper {

	@Override
	public Department departmentPayloadToDepartmentMapper(DepartmentPayload departmentPayload, Department department) {
		BeanUtils.copyProperties(departmentPayload, department);
		return department;
	}

	@Override
	public DepartmentPayload departmentToDepartmentPayloadMapper(Department department, DepartmentPayload departmentPayload) {
		BeanUtils.copyProperties(department, departmentPayload);
		return departmentPayload;
	}

}
