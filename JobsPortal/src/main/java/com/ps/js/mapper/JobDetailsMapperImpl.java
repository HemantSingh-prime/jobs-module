package com.ps.js.mapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.ps.js.entity.JobDetails;
import com.ps.js.entity.JobLocation;
import com.ps.js.entity.Skill;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobDetailsResponsePayload;
import com.ps.js.payload.ResponsePayload;

@Component
public class JobDetailsMapperImpl implements JobDetailsMapper {

	@Override
	public JobDetailsResponsePayload jobDetailToJobDetailsResponsePayloadMapper(JobDetails jobDetails, JobDetailsResponsePayload jobDetailsResponsePayload) {
		Set<ResponsePayload> locations = new HashSet<ResponsePayload>();
		Set<ResponsePayload> primarySkills = new HashSet<ResponsePayload>();
		Set<ResponsePayload> secondrySkills = new HashSet<ResponsePayload>();
		//Set<ResponsePayload> rolesAndResponsebilities = new HashSet<ResponsePayload>();
		ResponsePayload response=null;
		BeanUtils.copyProperties(jobDetails, jobDetailsResponsePayload);
		for(JobLocation location:jobDetails.getJobLocation()) {
			 response=new ResponsePayload();
			 response.setId(location.getLocationId());
			 
			locations.add(response);
		}
		for(Skill skill:jobDetails.getPrimarySkill()) {
			response=new ResponsePayload();
			response.setId(skill.getSkillId());
			primarySkills.add(response);
		}
		for(Skill skill:jobDetails.getSecondrySkill()) {
			response=new ResponsePayload();
			response.setId(skill.getSkillId());
			secondrySkills.add(response);
		}
//		for(RolesAndResponsebility rolesAndResponsebility:jobDetails.getRolesAndResponsebilities()) {
//			response=new ResponsePayload();
//			response.setId(rolesAndResponsebility.getId());
//			rolesAndResponsebilities.add(response);
//		}
		jobDetailsResponsePayload.setJobLocation(locations);
		jobDetailsResponsePayload.setPrimarySkill(primarySkills);
		jobDetailsResponsePayload.setSecondrySkill(secondrySkills);
		//jobDetailsResponsePayload.setRolesAndResponsebilities(rolesAndResponsebilities);
		
		
		
		return jobDetailsResponsePayload;
	}

	@Override
	public JobDetails jobDetailsPayloadToJobDetailsMapper(JobDetailsPayload jobDetailsPayload, JobDetails jobDetails) {
		
		Set<JobLocation> locations = new HashSet<JobLocation>();
		Set<Skill> primarySkills = new HashSet<Skill>();
		Set<Skill> secondrySkills = new HashSet<Skill>();
		//Set<RolesAndResponsebility> rolesAndResponsebilities = new HashSet<RolesAndResponsebility>();
		JobLocation location=null;
		Skill skill=null;
		//RolesAndResponsebility rolesAndResponsebility=null;
		
		BeanUtils.copyProperties(jobDetailsPayload, jobDetails);
		if(jobDetailsPayload.getJobLocation()!=null)
		for(Integer locationId:jobDetailsPayload.getJobLocation()) {
			location=new JobLocation();
			location.setLocationId(locationId);
			
			locations.add(location);
		}
		if(jobDetailsPayload.getPrimarySkill()!=null)
		for(Integer skillId:jobDetailsPayload.getPrimarySkill()) {
			skill=new Skill();
			skill.setSkillId(skillId);
			
			primarySkills.add(skill);
		}
		if(jobDetailsPayload.getSecondrySkill()!=null)
		for(Integer skillId:jobDetailsPayload.getSecondrySkill()) {
			skill=new Skill();
			skill.setSkillId(skillId);
			
			secondrySkills.add(skill);
		}
//		for(Integer id:jobDetailsPayload.getRolesAndResponsebilities()) {
//			rolesAndResponsebility=new RolesAndResponsebility();
//			rolesAndResponsebility.setId(id);
//			
//			rolesAndResponsebilities.add(rolesAndResponsebility);
//		}
		jobDetails.setJobLocation(locations);
		jobDetails.setPrimarySkill(primarySkills);
		jobDetails.setSecondrySkill(secondrySkills);
		//jobDetails.setRolesAndResponsebilities(rolesAndResponsebilities);
		  
		return jobDetails;
	}

}
