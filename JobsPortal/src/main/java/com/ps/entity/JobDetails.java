package com.ps.entity;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "JOB_DETAILS")
public class JobDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="JOB_ID")
	private int jobId;
	@Column(name="TITLE")
	private String title;
	private String designation;
	private String jobCode;
	private int noOfPositions;
	private float minSalary;
	private float maxSalary;
	private int minExperience;
	private int maxExperience;
	@Column(name="department_id")
	private int departmentId;
	private String description; 
	
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })
		  @JoinTable(
		        joinColumns = { @JoinColumn(name = "job_id") },
		        inverseJoinColumns = { @JoinColumn(name = "skill_id") })
	private Set<Skill> skill;
	private String rolesAndResponsebility;
	private Date jobPostingDate;
	private String jobStatus;
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })
		  @JoinTable(
		        joinColumns = { @JoinColumn(name = "job_id") },
		        inverseJoinColumns = { @JoinColumn(name = "location_id") })
	private List<JobLocation> jobLocation;
}
