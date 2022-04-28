package com.ps.payload;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.ps.entity.JobLocation;
import com.ps.entity.Skill;

import lombok.Data;

@Data
public class JobDetailsPayload {

	
	private int jobId;
	private String title;
	private String designation;
	private String jobCode;
	private int noOfPositions;
	private float minSalary;
	private float maxSalary;
	private int minExperience;
	private int maxExperience;
	private int departmentId;
	private String description; 
	private Set<Skill> skill;
	private String rolesAndResponsebility;
	private Date jobPostingDate;
	private String jobStatus;
	private List<JobLocation> jobLocation;
	
}
