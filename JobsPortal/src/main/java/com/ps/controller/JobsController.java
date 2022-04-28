package com.ps.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
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

import com.ps.entity.JobDetails;
import com.ps.entity.JobLocation;
import com.ps.entity.Skill;
import com.ps.payload.JobDetailsPayload;

import com.ps.service.IJobDetailsService;
import com.ps.service.IJobLocation;
import com.ps.service.ISkillServices;

@RestController
@RequestMapping("/jobs")
public class JobsController {

	@Autowired
	private IJobLocation jobLocationServices;
	@Autowired
	private IJobDetailsService jobDetailsService;
	@Autowired
	private ISkillServices skillServices;

	@GetMapping("/get-test")
	public String getTest() {

		return "Test Jobs Controller";
	}

	/**
	 * 
	 * @return
	 */

	@GetMapping("/find-all-job")
	public ResponseEntity<List<JobDetails>> findAllJob() {
		List<JobDetails> listJobDetails = jobDetailsService.findAllJob();

		return new ResponseEntity<List<JobDetails>>(listJobDetails, HttpStatus.OK);
	}

	@PostMapping("/add-job-details")
	public ResponseEntity<JobDetails> addJobDetails(@RequestBody JobDetailsPayload jobDetailsPayload) {
		List<JobLocation> locationList = new ArrayList<JobLocation>();
		Set<Skill> skills = new HashSet<Skill>();
		// Fetching the location and added to list collection
		for (JobLocation location : jobDetailsPayload.getJobLocation()) {
			locationList.add(jobLocationServices.fetchJobLocationByName(location.getLocationName()).get());
		}
		System.out.println(locationList);
		jobDetailsPayload.setJobLocation(locationList);

		// Fetching the skills and added to set collection
		for (Skill skill : jobDetailsPayload.getSkill()) {
			skills.add(skillServices.fetchSkillByName(skill.getSkillName()).get());
		}
		System.out.println(skills);
		jobDetailsPayload.setSkill(skills);
		JobDetails jobDetails = new JobDetails();
		BeanUtils.copyProperties(jobDetailsPayload, jobDetails);
		jobDetails = jobDetailsService.createJob(jobDetails);
		// jobDetails=jobDetailsService.createJob(jobDetails);
		System.out.println(jobDetails);
		return new ResponseEntity<JobDetails>(jobDetails, HttpStatus.OK);
	}

	@DeleteMapping("/delete-job-details/{job-code}")
	public ResponseEntity<String> deleteJobDetails(@PathVariable("job-code") String jobCode) {
		System.out.println(jobCode);
		JobDetails jobDetails = jobDetailsService.fetchJobDetailsByJobCode(jobCode).get();

		return new ResponseEntity<String>("Job details Delete :" + jobDetails.getJobId(), HttpStatus.OK);
	}

	@GetMapping("/find-job-by-job-status/{job-status}")
	public ResponseEntity<List<JobDetails>> findJobByJobStatus(@PathVariable("job-status") String jobStatus) {
		List<JobDetails> listJobDetails = jobDetailsService.findAllJobByJobStatus(jobStatus);

		return new ResponseEntity<List<JobDetails>>(listJobDetails, HttpStatus.OK);
	}

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

}
