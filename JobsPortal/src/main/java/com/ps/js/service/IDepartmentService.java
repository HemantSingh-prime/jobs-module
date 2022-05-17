package com.ps.js.service;

import java.util.List;
import java.util.Optional;

import com.ps.js.entity.Department;

public interface IDepartmentService {

	public Optional<Department> addDepartment(Department depratment);
	
	public Optional<Department> updateDepartment(Department department);
	
	public String removeDepartment(final int departmentId);
	
	public List<Department> fetchAllDepartment();
	
	public Optional<Department> fetchDepartmentById(final int departmentId);
	
}
