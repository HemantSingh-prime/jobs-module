package com.ps.js.mapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.ps.js.entity.JobDetails;
import com.ps.js.entity.JobLocation;
import com.ps.js.entity.RolesAndResponsebility;
import com.ps.js.entity.Skill;
import com.ps.js.payload.JobDetailsPayload;

@Component
public class JobDetailsMapperImpl implements JobDetailsMapper {

	@Override
	public JobDetailsPayload jobDetailToJobDetailsPayloadMapper(JobDetails jobDetails, JobDetailsPayload jobDetailsPayload) {
		Set<Integer> locations = new HashSet<Integer>();
		Set<Integer> primarySkills = new HashSet<Integer>();
		Set<Integer> secondrySkills = new HashSet<Integer>();
		Set<Integer> rolesAndResponsebilities = new HashSet<Integer>();
		
		
		for(JobLocation location:jobDetails.getJobLocation()) {
			
			locations.add(location.getLocationId());
		}
		for(Skill skill:jobDetails.getPrimarySkill()) {
			
			primarySkills.add(skill.getSkillId());
		}
		for(Skill skill:jobDetails.getSecondrySkill()) {
			
			secondrySkills.add(skill.getSkillId());
		}
		for(RolesAndResponsebility rolesAndResponsebility:jobDetails.getRolesAndResponsebilities()) {
			
			rolesAndResponsebilities.add(rolesAndResponsebility.getId());
		}
		jobDetailsPayload.setJobLocation(locations);
		jobDetailsPayload.setPrimarySkill(primarySkills);
		jobDetailsPayload.setSecondrySkill(secondrySkills);
		jobDetailsPayload.setRolesAndResponsebilities(rolesAndResponsebilities);
		BeanUtils.copyProperties(jobDetails, jobDetailsPayload);
		
		
		return jobDetailsPayload;
	}

	@Override
	public JobDetails jobDetailsPayloadToJobDetailsMapper(JobDetailsPayload jobDetailsPayload, JobDetails jobDetails) {
		System.out.println("---------------------------");
		Set<JobLocation> locations = new HashSet<JobLocation>();
		Set<Skill> primarySkills = new HashSet<Skill>();
		Set<Skill> secondrySkills = new HashSet<Skill>();
		Set<RolesAndResponsebility> rolesAndResponsebilities = new HashSet<RolesAndResponsebility>();
		JobLocation location=null;
		Skill skill=null;
		RolesAndResponsebility rolesAndResponsebility=null;
		System.out.println("Mapper :: "+jobDetails);
		BeanUtils.copyProperties(jobDetailsPayload, jobDetails);
		System.out.println("Mapper :: "+jobDetails);
		for(Integer locationId:jobDetailsPayload.getJobLocation()) {
			location=new JobLocation();
			location.setLocationId(locationId);
			
			locations.add(location);
		}
		for(Integer skillId:jobDetailsPayload.getPrimarySkill()) {
			skill=new Skill();
			skill.setSkillId(skillId);
			
			primarySkills.add(skill);
		}
		for(Integer skillId:jobDetailsPayload.getSecondrySkill()) {
			skill=new Skill();
			skill.setSkillId(skillId);
			
			secondrySkills.add(skill);
		}
		for(Integer id:jobDetailsPayload.getRolesAndResponsebilities()) {
			rolesAndResponsebility=new RolesAndResponsebility();
			rolesAndResponsebility.setId(id);
			
			rolesAndResponsebilities.add(rolesAndResponsebility);
		}
		jobDetails.setJobLocation(locations);
		jobDetails.setPrimarySkill(primarySkills);
		jobDetails.setSecondrySkill(secondrySkills);
		jobDetails.setRolesAndResponsebilities(rolesAndResponsebilities);
		  System.out.println("Mapper :: "+jobDetails);
		return jobDetails;
	}

}
