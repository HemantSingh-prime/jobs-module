package com.ps.js.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DepartmentPayload {

	
	private int departmentId;
	@NotNull
	@NotBlank
	private String departmentName; 
}
