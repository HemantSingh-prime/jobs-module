package com.ps.js.mapper;

import com.ps.js.entity.Department;
import com.ps.js.payload.DepartmentPayload;

public interface DepartmentMapper {

	public Department departmentPayloadToDepartmentMapper(DepartmentPayload departmentPayload,Department department);

	public DepartmentPayload departmentToDepartmentPayloadMapper(Department department,DepartmentPayload departmentPayload);
}
