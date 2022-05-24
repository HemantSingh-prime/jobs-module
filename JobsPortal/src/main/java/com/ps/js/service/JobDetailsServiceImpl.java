package com.ps.js.service;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ps.js.entity.JobDetails;
import com.ps.js.entity.JobLocation;
import com.ps.js.entity.Skill;
import com.ps.js.exception.ErrorMessages;
import com.ps.js.exception.JobDetailsNotCreatedException;
import com.ps.js.exception.JobDetailsNotFoundException;
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
	
	//private static ObjectMapper mapper=new ObjectMapper();
	/**
	 * To create a new job details and added to 
	 * @param jobDetails
	 * @return {@link JobDetailsPayload}
	 * 
	 */
	@Override
	@Transactional
	public JobDetailsResponsePayload createJob(JobDetails jobDetails) throws JobDetailsNotCreatedException {
		JobDetailsResponsePayload jobDetailsResponsePayload = new JobDetailsResponsePayload();

		jobDetails = jobDetailsRepository.save(jobDetails);
		// jobDetails.setJobId(0);
		if (!(jobDetails.getJobId() > 0))
			throw new JobDetailsNotCreatedException(ErrorMessages.JOB_DETAILS_NOT_ADDED_EXCEPTION.toString());

		// Convert jobDetails to jobDetailsPayload using jobDetailsMapper
		jobDetailsResponsePayload = jobDetailsMapper.jobDetailToJobDetailsResponsePayloadMapper(jobDetails,
				jobDetailsResponsePayload);

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
	public JobDetails updateJobDetails(JobDetails jobDetailsUpdated) {
		  
		JobDetails jobDetails=new JobDetails();
		Set<JobLocation> listLocations=new HashSet<JobLocation>();
		Set<Skill> listPrimarySkills=new HashSet<Skill>();
		Set<Skill> listSecondrySkills=new HashSet<Skill>();
		JobLocation jobLocation=null;
		//JobDetailsResponsePayload responsePayload=new JobDetailsResponsePayload();
		//Find job details by job-id
		Optional<JobDetails>  optionalJobDetails=jobDetailsRepository.findById(jobDetailsUpdated.getJobId());
		// Fetching the location and adding to collection
	       if(jobDetails.getJobLocation()!=null)
			for (JobLocation location : jobDetails.getJobLocation()) {
				jobLocation=new JobLocation();
				listLocations.add(jobLocationService.fetchJobLocationById(location.getLocationId()).get());
			}

			// Fetching the primary skills and added to collection
	       if(jobDetails.getPrimarySkill()!=null)
			for (Skill skill : jobDetails.getPrimarySkill()) {
				listPrimarySkills.add(skillService.fetchSkillById(skill.getSkillId()).get());
			}

			// Fetching the secondary skills and added to collection
	       if(jobDetails.getSecondrySkill()!=null)
			for (Skill skill : jobDetails.getSecondrySkill()) {
				listSecondrySkills.add(skillService.fetchSkillById(skill.getSkillId()).get());
			}
	       
	       jobDetailsUpdated.setPrimarySkill(listPrimarySkills);
			  jobDetailsUpdated.setSecondrySkill(listSecondrySkills);
			  jobDetailsUpdated.setJobLocation(listLocations);
		  if(optionalJobDetails.isEmpty())
			  throw new JobDetailsNotFoundException();
		  jobDetails=optionalJobDetails.get();
		
				  if(jobDetailsUpdated.getDepartmentId()<1)
					  jobDetailsUpdated.setDepartmentId(jobDetails.getDepartmentId());
				  if(jobDetailsUpdated.getDescription()==null || jobDetailsUpdated.getDescription().isBlank())
					  jobDetailsUpdated.setDescription(jobDetails.getDescription());
				  if(jobDetailsUpdated.getDesignation()==null || jobDetailsUpdated.getDesignation().isBlank())
					  jobDetailsUpdated.setDesignation(jobDetails.getDesignation());
				  if(jobDetailsUpdated.getJobStatus()==null || jobDetailsUpdated.getJobStatus().isBlank())
					  jobDetailsUpdated.setJobStatus(jobDetails.getJobStatus());
				  if(jobDetailsUpdated.getRolesAndResponsebility()==null ||jobDetailsUpdated.getRolesAndResponsebility().isEmpty())
					  jobDetailsUpdated.setRolesAndResponsebility(jobDetails.getRolesAndResponsebility());
				  if(jobDetailsUpdated.getJobPostingDate()==null)
					  jobDetailsUpdated.setJobPostingDate(jobDetails.getJobPostingDate());
				  if(jobDetailsUpdated.getJobLocation()==null || jobDetailsUpdated.getJobLocation().size()==0)
					  jobDetailsUpdated.setJobLocation(jobDetails.getJobLocation());
				  if(jobDetailsUpdated.getPrimarySkill()==null ||jobDetailsUpdated.getPrimarySkill().size()==0)
					  jobDetailsUpdated.setPrimarySkill(jobDetails.getPrimarySkill());
				  if(jobDetailsUpdated.getSecondrySkill()==null || jobDetailsUpdated.getSecondrySkill().size()==0)
					  jobDetailsUpdated.setSecondrySkill(jobDetails.getSecondrySkill());
				  if(jobDetailsUpdated.getJobCode()==null)
					  jobDetailsUpdated.setJobCode(jobDetails.getJobCode());
				  
				  
		jobDetailsUpdated=jobDetailsRepository.save(jobDetailsUpdated);
		  
		    
		return jobDetailsUpdated;
	}
	/**
     * To Delete jobDetails by using job-id 
     * @param jobId
     * return {@link optional<JobDetails>}
     */
	@Override
	public Optional<JobDetails> deleteJobDetails(JobDetails jobDetails) {
		jobDetailsRepository.delete(jobDetails);
		  Optional<JobDetails> optional=Optional.of(jobDetails);
		return optional;
	}
	
	/**
     * To find jobDetails by using job-id 
     * @param jobId
     * return {@link optional<JobDetails>}
     */
	@Override
	public Optional<JobDetails> findJobByJobId(int jobId){
		Optional<JobDetails> optionalJobDetails=jobDetailsRepository.findById(jobId);
		
		return optionalJobDetails;
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
		      Optional<JobDetails> optionalJobDetails=jobDetailsRepository.findByJobCode(jobCode);
		return optionalJobDetails;
	}

}
