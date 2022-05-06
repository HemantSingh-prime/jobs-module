package com.ps.js.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.js.entity.Department;
import com.ps.js.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

	/**
	 * Injecting DepartmentRepository
	 */
	@Autowired
	private DepartmentRepository departmentRepository;
	/**
	 * To added new department in the record
	 * @param departmet
	 * @return {@link Optional<Department>}
	 */
	@Override
	public Optional<Department> addDepartment(Department depratment) {
		
		return Optional.of(departmentRepository.save(depratment));
	}

	@Override
	public Optional<Department> updateDepartment(Department department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeDepartment(int departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> fetchAllDepartment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Department> fetchDepartmentById(int departmentId) {
		
		return departmentRepository.findById(departmentId);
	}

}
