package com.ps.js.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ps.js.entity.Department;
import com.ps.js.exception.DepartmentNotFoundException;
import com.ps.js.exception.ErrorMessages;
import com.ps.js.mapper.DepartmentMapper;
import com.ps.js.payload.DepartmentPayload;
import com.ps.js.service.IDepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private IDepartmentService departmentService;
	@Autowired
	private DepartmentMapper departmentMapper;
	
	/**
	 * To added a new department table
	 * 
	 * @param departmentPayload
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/add-department")
	public ResponseEntity<DepartmentPayload> addDepartment(@Valid @RequestBody DepartmentPayload departmentPayload) {
		Department department=new Department(); 
		department=departmentMapper.departmentPayloadToDepartmentMapper(departmentPayload, department);
		Optional<DepartmentPayload> optionalDepartmentPayload = departmentService.addDepartment(department);
		if(optionalDepartmentPayload.isEmpty())
			throw new DepartmentNotFoundException(ErrorMessages.DEPARTMENT_NOT_FOUND_EXCEPTION.toString());
		
		return new ResponseEntity<DepartmentPayload>(optionalDepartmentPayload.get(), HttpStatus.OK);
	}
	/**
	 * To update a department data in table
	 * 
	 * @param departmentPayload
	 * @return {@link ResponseEntity}
	 */
	@PatchMapping("/update-department")
	public ResponseEntity<DepartmentPayload> updateDepartment(@Valid @RequestBody DepartmentPayload departmentPayload){
		Optional<DepartmentPayload> optionalDepartmentPayload=Optional.empty();
		Department department=new Department();
		//Mapped DepartmentPayload to department
		 department=departmentMapper.departmentPayloadToDepartmentMapper(departmentPayload, department);
		 optionalDepartmentPayload=departmentService.updateDepartment(department);
		  if(optionalDepartmentPayload.isEmpty())
			  throw new DepartmentNotFoundException(ErrorMessages.DEPARTMENT_NOT_FOUND_EXCEPTION.toString());
		 return new ResponseEntity<DepartmentPayload>(optionalDepartmentPayload.get(),HttpStatus.OK);
	}
	
	/**
	 * To delete a department from table
	 * 
	 * @param departmentId
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping("/delete-department/{department-id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable("department-id") int departmentId){
	      Optional<Department> optionalDepartment=departmentService.removeDepartment(departmentId);
	      
	      if(optionalDepartment.isEmpty())
	    	  throw new DepartmentNotFoundException(ErrorMessages.DEPARTMENT_NOT_FOUND_EXCEPTION.toString());
	      
	      return new ResponseEntity<String>("Department delete department-id :: "+optionalDepartment.get().getDepartmentId(),HttpStatus.OK);
	}
	
	/**
	 * To find all department data from table
	 * 
	 * @param departmentPayload
	 * @return {@link ResponseEntity}
	 */
	@GetMapping("/fetch-department")
	public ResponseEntity<List<DepartmentPayload>> fetchDepartment(){
		List<DepartmentPayload> listDepartmentPayloads=departmentService.fetchAllDepartment();
		 
		return new ResponseEntity<List<DepartmentPayload>>(listDepartmentPayloads,HttpStatus.OK);
	}
}
