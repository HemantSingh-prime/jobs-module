package com.ps.service;

import java.util.List;
import java.util.Optional;

import com.ps.entity.JobDetails;
import com.ps.entity.Skill;

public interface IJobDetailsService {

	public JobDetails  createJob(JobDetails jobDetails);
	
	public List<JobDetails> findAllJob();
	
	public List<JobDetails> findAllJobByJobStatus(final String jobStatus);
	
	public Optional<JobDetails> updateJobDetails(JobDetails jobDetails);
	
	public Optional<JobDetails> deleteJobDetails(final String jobCode);
	
	public List<JobDetails> fetchedJobDetailsByDesignation(final String designation);
	
	public List<JobDetails> fetchedJobDetailsBySkills(List<Skill> skills);
	
	public Optional<JobDetails> fetchJobDetailsByJobCode(final String jobCode);
	
	public List<JobDetails> fetchedJobDetailsByLocationAndDesignation(final String designation,final String location);
}
