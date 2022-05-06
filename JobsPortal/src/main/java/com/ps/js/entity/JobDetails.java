package com.ps.js.entity;

import java.sql.Date;
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

	/**
	 * Creating jobId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="JOB_ID")
	private int jobId;
	/**
	 * Creating title
	 */
	@Column(name="TITLE")
	private String title;
	/**
	 * Creating designation
	 */
	private String designation;
	/**
	 * Creating jobCode
	 */
	@Column(unique = true)
	private String jobCode;
	/**
	 * Creating noOfPositions
	 */
	private int noOfPositions;
	/**
	 * Creating minSalary
	 */
	private float minSalary;
	/**
	 * Creating maxSalary
	 */
	private float maxSalary;
	/**
	 * Creating minExperience
	 */
	private int minExperience;
	/**
	 * Creating maxExperience
	 */
	private int maxExperience;
	/**
	 * Creating departmentId
	 */
	@Column(name="department_id")
	private int departmentId;
	/**
	 * Creating description
	 */
	private String description; 
	@ManyToMany(fetch = FetchType.EAGER,
		      cascade = {
		          
		          CascadeType.MERGE
		      })
		  @JoinTable(
		        joinColumns = { @JoinColumn(name = "job_id") },
		        inverseJoinColumns = { @JoinColumn(name = "skill_id") })
	private Set<Skill> primarySkill;
	@ManyToMany(fetch = FetchType.EAGER,
		      cascade = {
		          
		          CascadeType.MERGE
		      })
		  @JoinTable(
		        joinColumns = { @JoinColumn(name = "job_id") },
		        inverseJoinColumns = { @JoinColumn(name = "skill_id") })
	private Set<Skill> secondrySkill;
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          
		          CascadeType.MERGE
		      })
		  @JoinTable(
		        joinColumns = { @JoinColumn(name = "job_id") },
		        inverseJoinColumns = { @JoinColumn(name = "id") })
	private Set<RolesAndResponsebility> rolesAndResponsebilities;
	private Date jobPostingDate;
	private String jobStatus;
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		                            
		          CascadeType.MERGE
		      })
		  @JoinTable(
		        joinColumns = { @JoinColumn(name = "job_id") },
		        inverseJoinColumns = { @JoinColumn(name = "location_id") })
	private Set<JobLocation> jobLocation;
}
