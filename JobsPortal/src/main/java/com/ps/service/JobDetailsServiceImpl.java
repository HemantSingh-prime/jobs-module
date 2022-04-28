package com.ps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.entity.JobDetails;
import com.ps.entity.Skill;
import com.ps.exception.JobDetailsNotCreatedException;
import com.ps.repository.JobDetailsRepository;

@Service
public class JobDetailsServiceImpl implements IJobDetailsService {

	@Autowired
	private JobDetailsRepository jobDetailsRepository; 
	
	
	@Override
	public JobDetails createJob(JobDetails jobDetails)throws JobDetailsNotCreatedException {
		jobDetails=jobDetailsRepository.save(jobDetails);
		//jobDetails.setJobId(0);
		if(!(jobDetails.getJobId()>0))
			throw new JobDetailsNotCreatedException("Job Details not added ");
					
		
		return jobDetails;
	}

	@Override
	public List<JobDetails> findAllJob() {
		   List<JobDetails> listJobDetails= jobDetailsRepository.findAll();
		return listJobDetails;
	}

	@Override
	public List<JobDetails> findAllJobByJobStatus(String jobStatus) {
		
		return jobDetailsRepository.findByJobStatus(jobStatus);
	}

	@Override
	public Optional<JobDetails> updateJobDetails(JobDetails jobDetails) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<JobDetails> deleteJobDetails(String jobCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobDetails> fetchedJobDetailsByDesignation(String designation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobDetails> fetchedJobDetailsBySkills(List<Skill> skills) {
		List<JobDetails> listJobDetails=jobDetailsRepository.findAll();
		List<JobDetails> listJobDetails2=new ArrayList<JobDetails>();
		 for(JobDetails jobDetails:listJobDetails) {
			 for(Skill skill:skills) {
				 if(jobDetails.getSkill().iterator().next().equals(skill))
					 listJobDetails2.add(jobDetails);
			 }
		 }
			 
		return listJobDetails2;
	}

	@Override
	public List<JobDetails> fetchedJobDetailsByLocationAndDesignation(String designation, String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<JobDetails> fetchJobDetailsByJobCode(String jobCode) {
		
		return jobDetailsRepository.findByJobCode(jobCode);
	}

}
