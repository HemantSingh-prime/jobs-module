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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ps.js.entity.Department;
import com.ps.js.entity.JobDetails;
import com.ps.js.entity.JobLocation;
import com.ps.js.entity.RolesAndResponsebility;
import com.ps.js.entity.Skill;
import com.ps.js.exception.LocationsNotFoundException;
import com.ps.js.exception.SkillNotFoundException;
import com.ps.js.mapper.JobDetailsMapper;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobLocationPayload;
import com.ps.js.payload.RolesAndResponsebilityPayload;
import com.ps.js.payload.SkillPayload;

import com.ps.js.service.IDepartmentService;
import com.ps.js.service.IJobDetailsService;
import com.ps.js.service.IJobLocationServices;
import com.ps.js.service.IRolesAndResponsebilityService;
import com.ps.js.service.ISkillServices;

@RestController
@RequestMapping("/jobs")
public class JobsController {

	/**
	 * Injecting JobLocationService
	 */
	@Autowired
	private IJobLocationServices jobLocationServices;
	/**
	 * Injecting JobDetailsService
	 */
	@Autowired
	private IJobDetailsService jobDetailsService;
	/**
	 * Injecting SkillService
	 */
	@Autowired
	private ISkillServices skillServices;
	/**
	 * Injecting DepartmentService
	 */
	@Autowired
	private IDepartmentService departmentService;
	/**
	 * Injecting rolesAndResponsebilityService
	 */
	@Autowired
	private IRolesAndResponsebilityService rolesAndResponsebilityService;
	/**
	 * Injecting JobDetailsMapper
	 */
	@Autowired
	private JobDetailsMapper jobDetailsMapper;

	/**
	 * For fetching all the jobs currently having in record
	 * 
	 * @return {@link ResponseEntity}
	 */

	@GetMapping("/find-all-job")
	public ResponseEntity<List<JobDetails>> findAllJob() {
		List<JobDetails> listJobDetails = jobDetailsService.findAllJob();

		return new ResponseEntity<List<JobDetails>>(listJobDetails, HttpStatus.OK);
	}

	/**
	 * For create a new jobDetails and return jobDetails with generated jobId
	 * 
	 * @param jobDetailsPayload
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/open-job")
	public ResponseEntity<JobDetailsPayload> openJob(@Valid @RequestBody JobDetailsPayload jobDetailsPayload) {
		Set<JobLocation> locations = new HashSet<JobLocation>();
		Set<Skill> primarySkills = new HashSet<Skill>();
		Set<Skill> secondrySkills = new HashSet<Skill>();
		Set<RolesAndResponsebility> rolesAndResponsebilities = new HashSet<RolesAndResponsebility>();
		JobDetails jobDetails = new JobDetails();
          System.out.println("Payload :: "+jobDetailsPayload);
		// Convert job details to job details payload using jobDetailsMapper service
		jobDetails=jobDetailsMapper.jobDetailsPayloadToJobDetailsMapper(jobDetailsPayload, jobDetails);
        
		System.out.println("jobDetails :: "+jobDetails);
		
		// Fetching the location and adding to collection
		for (JobLocation location : jobDetails.getJobLocation()) {
			locations.add(jobLocationServices.fetchJobLocationById(location.getLocationId()).get());
		}
		
        
		// Fetching the primary skills and added to collection
		for (Skill skill : jobDetails.getPrimarySkill()) {
			primarySkills.add(skillServices.fetchSkillById(skill.getSkillId()).get());
		}
        
        
		// Fetching the secondary skills and added to collection
		for (Skill skill : jobDetails.getSecondrySkill()) {
			secondrySkills.add(skillServices.fetchSkillById(skill.getSkillId()).get());
		}
        
		// Fetching the roles and responsebility and added to collection
		for (RolesAndResponsebility responsebility : jobDetails.getRolesAndResponsebilities()) {
			rolesAndResponsebilities
					.add(rolesAndResponsebilityService.fetchRolesAndResponsebilityById(responsebility.getId()).get());
		}
		if(locations.isEmpty())
			throw new LocationsNotFoundException();
		else if(primarySkills.isEmpty())
			throw new SkillNotFoundException("Primary skill not found exception");
		else if(secondrySkills.isEmpty())
			throw new SkillNotFoundException("Secondry skill not found exception");

		// Invoking jobDetailsService to add a new jobDetails
		jobDetailsPayload = jobDetailsService.createJob(jobDetails);

		return new ResponseEntity<JobDetailsPayload>(jobDetailsPayload, HttpStatus.OK);
	}

	/**
	 * To find all the skills set for open a new job
	 * 
	 * @return {@link ResponseEntity}
	 */
	@GetMapping("/find-all-skill")
	public ResponseEntity<List<SkillPayload>> findAllSkill() {
		SkillPayload skillPayload = null;
		List<SkillPayload> listSkillPayload = new ArrayList<SkillPayload>();
		List<Skill> listSkills = skillServices.findAllSkills();
		for (Skill skill : listSkills) {
			skillPayload = new SkillPayload();
			skillPayload.setSkillId(skill.getSkillId());
			skillPayload.setSkillName(skill.getSkillName());
			listSkillPayload.add(skillPayload);
		}
		System.out.println("listSkillPayload :: " + listSkillPayload);
		return new ResponseEntity<List<SkillPayload>>(listSkillPayload, HttpStatus.OK);
	}

	/**
	 * To find all the job location set for open a new job
	 * 
	 * @return {@link ResponseEntity}
	 */
	@GetMapping("/find-all-locations")
	public ResponseEntity<List<JobLocationPayload>> findAllJobLocation() {
		JobLocationPayload jobLocationPayload = null;
		List<JobLocationPayload> listJobLocationPayload = new ArrayList<JobLocationPayload>();
		List<JobLocation> listJobLocations = jobLocationServices.fetchAllLocation();
		for (JobLocation location : listJobLocations) {
			jobLocationPayload = new JobLocationPayload();
			jobLocationPayload.setLocationId(location.getLocationId());
			jobLocationPayload.setLocationName(location.getLocationName());
			listJobLocationPayload.add(jobLocationPayload);
		}

		return new ResponseEntity<List<JobLocationPayload>>(listJobLocationPayload, HttpStatus.OK);
	}

	/**
	 * To find all roles and responsebility for open a new job
	 * 
	 * @return {@link ResponseEntity}
	 */
	@GetMapping("/find-all-roles-and-responsebility")
	public ResponseEntity<List<RolesAndResponsebilityPayload>> findAllRolesAndResponsebility() {
		RolesAndResponsebilityPayload rolesAndResponsebilityPayload = null;
		List<RolesAndResponsebilityPayload> listRolesAndResponsebilityPayload = new ArrayList<RolesAndResponsebilityPayload>();
		List<RolesAndResponsebility> listRolesAndResponsebility = rolesAndResponsebilityService
				.fetchAllRolesAndResponsebility();
		for (RolesAndResponsebility rolesAndResponsebility : listRolesAndResponsebility) {
			rolesAndResponsebilityPayload = new RolesAndResponsebilityPayload();
			rolesAndResponsebilityPayload.setId(rolesAndResponsebility.getId());
			rolesAndResponsebilityPayload.setRolesResponsebility(rolesAndResponsebility.getRolesResponsebility());
			listRolesAndResponsebilityPayload.add(rolesAndResponsebilityPayload);
		}

		return new ResponseEntity<List<RolesAndResponsebilityPayload>>(listRolesAndResponsebilityPayload,
				HttpStatus.OK);
	}

	/**
	 * For delete jobDetails by using job-code and return confirmation for the
	 * deleted record
	 * 
	 * @param jobCode
	 * @return {@link ResponseEntity}
	 */

	@DeleteMapping("/delete-job-details/{job-code}")
	public ResponseEntity<String> deleteJobDetails(@PathVariable("job-code") String jobCode) {
		System.out.println(jobCode);
		JobDetails jobDetails = jobDetailsService.fetchJobDetailsByJobCode(jobCode).get();

		return new ResponseEntity<String>("Job details Delete :" + jobDetails.getJobId(), HttpStatus.OK);
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

	/**
	 * To find all the jobs based on entered skill set
	 * 
	 * @param skills
	 * @return {@link ResponseEntity}
	 */
	@GetMapping("/find-job-by-skills")
	public ResponseEntity<List<JobDetails>> findJobBySkills(@RequestParam List<String> skills) {
		List<JobDetails> listJobDetails = new ArrayList<JobDetails>();
		List<Skill> listSkills = new ArrayList<Skill>();
		for (String skill : skills) {
			Optional<Skill> optionalSkill = skillServices.fetchSkillByName(skill);
			listSkills.add(optionalSkill.get());
		}
		listJobDetails = jobDetailsService.fetchedJobDetailsBySkills(listSkills);
		return new ResponseEntity<List<JobDetails>>(listJobDetails, HttpStatus.OK);
	}

	/**
	 * To added a new job location in the record
	 * 
	 * @param jobLocation
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/add-job-location")
	public ResponseEntity<JobLocation> addJobLocation(@RequestBody JobLocation jobLocation) {
		JobLocation location = jobLocationServices.saveLocation(jobLocation);
		return new ResponseEntity<JobLocation>(location, HttpStatus.OK);
	}

	/**
	 * To added a new skill in the record
	 * 
	 * @param skill
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/add-skill")
	public ResponseEntity<Skill> addSkill(@Valid @RequestBody Skill skill) {
		skill = skillServices.addSkill(skill).get();
		return new ResponseEntity<Skill>(skill, HttpStatus.OK);
	}

	/**
	 * To added a new department in the record
	 * 
	 * @param department
	 * @return
	 */
	@PostMapping("/add-department")
	public ResponseEntity<Department> addDepartment(@Valid @RequestBody Department department) {
		department = departmentService.addDepartment(department).get();
		return new ResponseEntity<Department>(department, HttpStatus.OK);
	}

}