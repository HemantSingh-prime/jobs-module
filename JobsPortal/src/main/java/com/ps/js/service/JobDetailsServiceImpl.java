package com.ps.js.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.js.entity.JobDetails;

import com.ps.js.entity.Skill;
import com.ps.js.exception.ErrorMessages;
import com.ps.js.exception.JobDetailsNotCreatedException;
import com.ps.js.mapper.JobDetailsMapper;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobDetailsResponsePayload;
import com.ps.js.repository.JobDetailsRepository;
/**
 * 
 * @author Hemant
 *
 */
@Service
public class JobDetailsServiceImpl implements IJobDetailsService {

	
	/**
	 * Injecting JobDetailsRepository
	 */
	@Autowired
	private JobDetailsRepository jobDetailsRepository; 
	
	/**
	 * Injecting JobDetailsMapper
	 */
	@Autowired
	private JobDetailsMapper jobDetailsMapper;
	
	private static ObjectMapper mapper=new ObjectMapper();
	/**
	 * To create a new job details and added to 
	 * @param jobDetails
	 * @return {@link JobDetailsPayload}
	 * 
	 */
	@Override
	@Transactional
	public JobDetailsResponsePayload createJob(JobDetails jobDetails)throws JobDetailsNotCreatedException {
		JobDetailsResponsePayload jobDetailsResponsePayload=new JobDetailsResponsePayload();
		
		
		jobDetails=jobDetailsRepository.save(jobDetails);
		//jobDetails.setJobId(0);
		if(!(jobDetails.getJobId()>0))
			throw new JobDetailsNotCreatedException(ErrorMessages.JOB_DETAILS_NOT_ADDED_EXCEPTION.toString());
		
		//Convert jobDetails to jobDetailsPayload using jobDetailsMapper
            jobDetailsResponsePayload=jobDetailsMapper.jobDetailToJobDetailsResponsePayloadMapper(jobDetails, jobDetailsResponsePayload);				
		      
				
            return jobDetailsResponsePayload;
	}
     /**
      * To find all the Jobs 
      * @param
      * @return {@link List<JobDetails>}
      */
	@Override
	public List<JobDetails> findAllJob() {
		   List<JobDetails> listJobDetails= jobDetailsRepository.findAll();
		return listJobDetails;
	}
	/**
     * To find all the Jobs based on requested job-status 
     * @param
     * @return {@link List<JobDetails>}
     */
	@Override
	public List<JobDetails> findAllJobByJobStatus(String jobStatus) {
		
		return jobDetailsRepository.findByJobStatus(jobStatus);
	}
     /**
      * To Update jobDetails by using job-code or job-id
      * @param
      * return {@link optional<JobDetails>}
      */
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
				 if(jobDetails.getPrimarySkill().iterator().next().equals(skill))
					 listJobDetails2.add(jobDetails);
				 else if (jobDetails.getSecondrySkill().iterator().next().equals(skill))
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
