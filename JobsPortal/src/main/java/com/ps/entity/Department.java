package com.ps.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Department {

	@Id
	@Column(name="department_id")
	private int departmentId;
	private String departmentName;
	@OneToMany
	@JoinColumn(name="department_id", referencedColumnName = "department_id")
	private Set<JobDetails> jobDetails;
}
