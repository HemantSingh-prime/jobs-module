package com.ps.js.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DepartmentPayload {

	
	private int departmentId;
	@NotNull(message = "Department name should not be null")
	@NotBlank(message = "Department name should not be empty")
	private String departmentName; 
}
