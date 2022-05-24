package com.ps.js.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.js.entity.Department;
import com.ps.js.exception.DepartmentNotFoundException;
import com.ps.js.exception.ErrorMessages;
import com.ps.js.mapper.DepartmentMapper;
import com.ps.js.payload.DepartmentPayload;
import com.ps.js.repository.DepartmentRepository;
/**
 * @author Hemant
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {

	/**
	 * Injecting DepartmentRepository
	 */
	@Autowired
	private DepartmentRepository departmentRepository;
	
	/**
	 * Injecting DepartmentMapper
	 */
	@Autowired
	private DepartmentMapper departmentMapper;
	
	/**
	 * To added new department in the record
	 * @param department
	 * @return {@link Optional<DepartmentPayload>}
	 */
	@Override
	public Optional<DepartmentPayload> addDepartment(Department depratment) {
		DepartmentPayload departmentPayload=new DepartmentPayload(); 
		Optional<DepartmentPayload> optionalDepartmentPayload=Optional.empty();
		depratment=departmentRepository.save(depratment);
		     //Mapped department to departmentPayload
		departmentPayload=departmentMapper.departmentToDepartmentPayloadMapper(depratment, departmentPayload);
		 optionalDepartmentPayload=Optional.of(departmentPayload);
		return optionalDepartmentPayload;
	}

	/**
	 * To added new department in the record
	 * @param department
	 * @return {@link Optional<DepartmentPayload>}
	 */
	@Override
	public Optional<DepartmentPayload> updateDepartment(Department department) {
		Optional<Department> optionalDepartment=Optional.empty();
		DepartmentPayload departmentPayload=new DepartmentPayload();
		optionalDepartment=Optional.of(departmentRepository.save(department));
		if(optionalDepartment.isEmpty())
			throw new DepartmentNotFoundException(ErrorMessages.DEPARTMENT_NOT_FOUND_EXCEPTION.toString());
		//Mapping department to departmentPayload
		  departmentPayload=departmentMapper.departmentToDepartmentPayloadMapper(optionalDepartment.get(), departmentPayload);
		  Optional<DepartmentPayload> optionalDepartmentPayload=Optional.of(departmentPayload);
		  
		return optionalDepartmentPayload;
	}

	@Override
	public Optional<Department> removeDepartment(int departmentId) {
		 Optional<Department> optionalDepartment=Optional.empty();
		 optionalDepartment=departmentRepository.findById(departmentId);
		 if(optionalDepartment.isEmpty())
			 throw new DepartmentNotFoundException(ErrorMessages.DEPARTMENT_NOT_FOUND_EXCEPTION.toString());
		 
		 departmentRepository.deleteById(departmentId);
		 
		return optionalDepartment;
	}

	@Override
	public List<DepartmentPayload> fetchAllDepartment() {
		List<Department> listDepartments=new ArrayList<Department>();
		List<DepartmentPayload> listDepartmentPayloads=new ArrayList<DepartmentPayload>();
		DepartmentPayload departmentPayload=null;
		listDepartments=departmentRepository.findAll();
		if(listDepartments.size()>0)
			for(Department department:listDepartments) {
				departmentPayload=new DepartmentPayload();
				departmentPayload=departmentMapper.departmentToDepartmentPayloadMapper(department, departmentPayload);
				listDepartmentPayloads.add(departmentPayload);
			}
		return listDepartmentPayloads;
	}

	@Override
	public DepartmentPayload fetchDepartmentById(int departmentId) {
		Optional<Department> optionalDepartment=Optional.empty();
		   optionalDepartment=departmentRepository.findById(departmentId);
		return null;
	}

}
