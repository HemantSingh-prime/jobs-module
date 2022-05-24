package com.ps.js.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.js.entity.Department;
import com.ps.js.entity.JobDetails;
import com.ps.js.entity.JobLocation;
import com.ps.js.entity.Skill;
import com.ps.js.exception.ErrorMessages;

import com.ps.js.exception.JobDetailsNotFoundException;
import com.ps.js.exception.LocationsNotFoundException;

import com.ps.js.exception.SkillNotFoundException;
import com.ps.js.mapper.JobDetailsMapper;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobDetailsResponsePayload;
import com.ps.js.payload.JobLocationPayload;
import com.ps.js.payload.ResponsePayload;
import com.ps.js.payload.SkillPayload;

import com.ps.js.service.IDepartmentService;
import com.ps.js.service.IJobDetailsService;
import com.ps.js.service.IJobLocationService;


import com.ps.js.service.ISkillService;

@RestController
@RequestMapping("/jobs")
public class JobsController {

	/**
	 * Injecting JobLocationService
	 */
	@Autowired
	private IJobLocationService jobLocationService;
	/**
	 * Injecting JobDetailsService
	 */
	@Autowired
	private IJobDetailsService jobDetailsService;
	/**
	 * Injecting SkillService
	 */
	@Autowired
	private ISkillService skillService;
	/**
	 * Injecting DepartmentService
	 */
	@Autowired
	private IDepartmentService departmentService;
	/**
	 * Injecting rolesAndResponsebilityService
	 */
	// @Autowired
	// private IRolesAndResponsebilityService rolesAndResponsebilityService;
	/**
	 * Injecting JobDetailsMapper
	 */
	@Autowired
	private JobDetailsMapper jobDetailsMapper;

	//private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * For fetching all the jobs currently having in record
	 * 
	 * @return {@link ResponseEntity}
	 */

	@GetMapping("/find-all-job")
	public ResponseEntity<List<JobDetails>> findAllJob() {
		// Fetch all the jobs available
		List<JobDetails> listJobDetails = jobDetailsService.findAllJob();
		if (listJobDetails == null)
			throw new JobDetailsNotFoundException(ErrorMessages.NO_JOB_DETAILS_FOUND_EXCEPTION.toString());

		return new ResponseEntity<List<JobDetails>>(listJobDetails, HttpStatus.OK);
	}

	/**
	 * For create a new jobDetails and return jobDetails with generated jobId
	 * 
	 * @param jobDetailsPayload
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/open-job")
	public ResponseEntity<JobDetailsResponsePayload> openJob(@Valid @RequestBody JobDetailsPayload jobDetailsPayload) {
		Set<JobLocation> locations = new HashSet<JobLocation>();
		Set<Skill> primarySkills = new HashSet<Skill>();
		Set<Skill> secondrySkills = new HashSet<Skill>();
		// Set<RolesAndResponsebility> rolesAndResponsebilities = new
		// HashSet<RolesAndResponsebility>();
		JobDetails jobDetails = new JobDetails();
		JobDetailsResponsePayload jobDetailsResponsePayload = null;
		// Convert job details to job details payload using jobDetailsMapper service
		jobDetails = jobDetailsMapper.jobDetailsPayloadToJobDetailsMapper(jobDetailsPayload, jobDetails);

		

		

		// Invoking jobDetailsService to add a new jobDetails
		jobDetailsResponsePayload = jobDetailsService.createJob(jobDetails);

		if (jobDetailsResponsePayload != null) {
			for (ResponsePayload payload : jobDetailsResponsePayload.getJobLocation()) {
				payload.setUrl("locations/find-location-by-id/" + payload.getId());
			}
			for (ResponsePayload payload : jobDetailsResponsePayload.getPrimarySkill()) {
				payload.setUrl("skills/find-skill-by-id/" + payload.getId());
			}
			for (ResponsePayload payload : jobDetailsResponsePayload.getSecondrySkill()) {
				payload.setUrl("skills/find-skill-by-id/" + payload.getId());
			}

		}

		return new ResponseEntity<JobDetailsResponsePayload>(jobDetailsResponsePayload, HttpStatus.OK);
	}

	
	/**
	 * For delete jobDetails by using job-code and return confirmation for the
	 * deleted record
	 * 
	 * @param jobId
	 * @return {@link ResponseEntity}
	 */

	@DeleteMapping("/delete-job-details/{job-id}")
	public ResponseEntity<String> deleteJobDetails(@PathVariable("job-id") int jobId) {
		// Find Job details using jobDetailsService based job-id
		Optional<JobDetails> optionalJobDetails = jobDetailsService.findJobByJobId(jobId);
		if (optionalJobDetails.isEmpty())
			throw new JobDetailsNotFoundException(ErrorMessages.JOB_DETAILS_NOT_FOUND_EXCEPTION.toString());
		JobDetails jobDetails = optionalJobDetails.get();
		// Deleting jobDetails using jobDetailsService
		jobDetails = jobDetailsService.deleteJobDetails(jobDetails).get();

		return new ResponseEntity<String>("Job details Delete :" + jobDetails.getJobId(), HttpStatus.OK);
	}

	/**
	 * For updated jobDetails by using job-id and return updated jobDetails
	 * response.
	 * 
	 * 
	 * @param jobDetailsPayload
	 * @return {@link ResponseEntity}
	 */
	@PatchMapping("/update-job-details")
	public ResponseEntity<JobDetails> updateJobDetails(
			@RequestBody JobDetailsPayload jobDetailsPayload) {
		//JobDetailsResponsePayload jobDetailsResponsePayload = new JobDetailsResponsePayload();
		//Optional<JobDetailsResponsePayload> optionalJobDetailsResponsePayload = null;
		JobDetails jobDetails = new JobDetails();
		Set<JobLocation> locations = new HashSet<JobLocation>();
		Set<Skill> primarySkills = new HashSet<Skill>();
		Set<Skill> secondrySkills = new HashSet<Skill>();
		Optional<JobDetails> optionalJobDetails = null;
		// Convert JobDetailsPayload to JobDetails
		jobDetails = jobDetailsMapper.jobDetailsPayloadToJobDetailsMapper(jobDetailsPayload, jobDetails);
		// Find jobDetails by job-id
		optionalJobDetails = jobDetailsService.findJobByJobId(jobDetails.getJobId());
		// Fetching the location and adding to collection
		       if(jobDetails.getJobLocation()!=null)
				for (JobLocation location : jobDetails.getJobLocation()) {
					locations.add(jobLocationService.fetchJobLocationById(location.getLocationId()).get());
				}

				// Fetching the primary skills and added to collection
		       if(jobDetails.getPrimarySkill()!=null)
				for (Skill skill : jobDetails.getPrimarySkill()) {
					primarySkills.add(skillService.fetchSkillById(skill.getSkillId()).get());
				}

				// Fetching the secondary skills and added to collection
		       if(jobDetails.getSecondrySkill()!=null)
				for (Skill skill : jobDetails.getSecondrySkill()) {
					secondrySkills.add(skillService.fetchSkillById(skill.getSkillId()).get());
				}
		if (optionalJobDetails.isEmpty())
			throw new JobDetailsNotFoundException(ErrorMessages.JOB_DETAILS_NOT_FOUND_EXCEPTION.toString());
		  jobDetails.setPrimarySkill(primarySkills);
		  jobDetails.setSecondrySkill(secondrySkills);
		  jobDetails.setJobLocation(locations);
		// Updating jobDetails using jobDetailsService
		jobDetails = jobDetailsService.updateJobDetails(jobDetails);
		

		return new ResponseEntity<JobDetails>(jobDetails, HttpStatus.OK);
	}

	
	
	/**
	 * To find skill by using skill-id
	 * 
	 * @param skillId
	 * @return {@link ResponseEntity<Skill>}
	 */
	@GetMapping("/find-skill-by-id/{skill-id}")
	public ResponseEntity<Skill> findSKillById(@PathVariable("skill-id") int skillId) {
		Skill skill = null;
		// Fetch skill by id
		Optional<Skill> optionalSkill = skillService.fetchSkillById(skillId);

		if (optionalSkill.isEmpty())
			throw new SkillNotFoundException();
		skill = optionalSkill.get();
		return new ResponseEntity<Skill>(skill, HttpStatus.OK);
	}

	/**
	 * To find job-location by using location-id
	 * 
	 * @param locationId
	 * @return {@link ResponseEntity<JobLocation>}
	 */
	@GetMapping("/find-location-by-id/{location-id}")
	public ResponseEntity<JobLocation> findLocationById(@PathVariable("location-id") int locationId) {
		JobLocation location = null;
		// Fetch location by location-id
		Optional<JobLocation> optionalLocation = jobLocationService.fetchJobLocationById(locationId);
		if (optionalLocation.isEmpty())
			throw new LocationsNotFoundException(ErrorMessages.LOCATION_NOT__FOUND_EXCEPTION.toString());
		location = optionalLocation.get();
		return new ResponseEntity<JobLocation>(location, HttpStatus.OK);
	}


	

	/**
	 * To find jobDetails by jobId	
	 * @param jobId
	 * @return
	 */
	@GetMapping("/find-job-by-job-id/{job-id}")
	public ResponseEntity<JobDetails> findJobByJobId(@PathVariable("job-id")int jobId){
		
		Optional<JobDetails> optionalJobDetails=jobDetailsService.findJobByJobId(jobId);
		if(optionalJobDetails.isEmpty())
			throw new JobDetailsNotFoundException(ErrorMessages.JOB_DETAILS_NOT_FOUND_EXCEPTION.toString());
		
		JobDetails jobDetails=optionalJobDetails.get();
		
		return new ResponseEntity<JobDetails>(jobDetails,HttpStatus.OK);
	}

	/**
	 * To find all the record based on job status
	 * 
	 * @param jobStatus
	 * @return {@link ResponseEntity}
	 */
	@GetMapping("/find-job-by-job-status/{job-status}")
	public ResponseEntity<List<JobDetails>> findJobByJobStatus(@PathVariable("job-status") String jobStatus) {
		List<JobDetails> listJobDetails = jobDetailsService.findAllJobByJobStatus(jobStatus);

		return new ResponseEntity<List<JobDetails>>(listJobDetails, HttpStatus.OK);
	}

	
	

	

	

}