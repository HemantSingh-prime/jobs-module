package com.ps.js.service;

import java.util.List;
import java.util.Optional;

import com.ps.js.entity.Department;
import com.ps.js.payload.DepartmentPayload;

public interface IDepartmentService {

	public Optional<DepartmentPayload> addDepartment(Department depratment);
	
	public Optional<DepartmentPayload> updateDepartment(Department department);
	
	public Optional<Department> removeDepartment(final int departmentId);
	
	public List<DepartmentPayload> fetchAllDepartment();
	
	public DepartmentPayload fetchDepartmentById(final int departmentId);
	
}
