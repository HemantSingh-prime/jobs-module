package com.ps.js.service;

import java.util.List;
import java.util.Optional;

import com.ps.js.entity.JobDetails;
import com.ps.js.entity.Skill;

import com.ps.js.payload.JobDetailsResponsePayload;

public interface IJobDetailsService {

	public JobDetailsResponsePayload  createJob(JobDetails jobDetails);
	
	public List<JobDetails> findAllJob();
	
	public List<JobDetails> findAllJobByJobStatus(final String jobStatus);
	
	public JobDetails updateJobDetails(JobDetails jobDetails);
	
	public Optional<JobDetails> deleteJobDetails(JobDetails jobDetails);
	
	public List<JobDetails> fetchedJobDetailsByDesignation(final String designation);
	
	public List<JobDetails> fetchedJobDetailsBySkills(List<Skill> skills);
	
	public Optional<JobDetails> fetchJobDetailsByJobCode(final String jobCode);
	
	public List<JobDetails> fetchedJobDetailsByLocationAndDesignation(final String designation,final String location);
	
	public Optional<JobDetails> findJobByJobId(int jobId);
}
