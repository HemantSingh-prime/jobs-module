package com.ps.js.payload;

import java.sql.Date;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



import lombok.Data;

@Data
public class JobDetailsPayload  {

	
	private int jobId;
	@NotNull
	@NotBlank
	private String title;
	@NotNull(message = "Designation should not be null or empty")
	@NotBlank(message = "Designation should not be null or empty")
	private String designation;
	@NotNull(message = "JobCode should not be null")
	@NotBlank(message = "JobCode should not be empty")
	private String jobCode;
	@Min(message = "no of positions should not be less then 1",value = 1)
	private int noOfPositions;
	@Min(message = "min salary should not be less then 1 ",value = 1)
	private float minSalary;
	@Min(message = "min salary should not be less then 1",value = 1)
	private float maxSalary;
	@Min(message = "min experience should not be negative", value = 0)
	private int minExperience;
	@Min(message = "max experience should not be less then 1 yr",value = 1)
	private int maxExperience;
	@Min(message = "department id should not be less then 1",value = 1)
	private int departmentId;
	@NotNull(message = "department id should not be null")
	@NotBlank(message = "description should not be empty")
	private String description;
	@NotEmpty(message = "primary skills set should not be null or empty")
	private Set<Integer> primarySkill;
	@NotEmpty(message = "secondry skills set should not be null or empty")
	private Set<Integer> secondrySkill;
	@NotEmpty(message = "roles and responsebility set should not be null or empty")
	private Set<Integer> rolesAndResponsebilities;
	private Date jobPostingDate;
	@NotNull(message = "job status should not be null ")
	@NotBlank(message = "job status should not be empty")
	private String jobStatus;
	@NotEmpty(message = "job location set should not be null or empty")
	private Set<Integer> jobLocation;
	
}
