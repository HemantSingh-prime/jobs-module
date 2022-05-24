package com.ps.js.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.ps.js.entity.Skill;
import com.ps.js.exception.ErrorMessages;
import com.ps.js.exception.SkillNotFoundException;
import com.ps.js.mapper.SkillMapper;
import com.ps.js.payload.SkillPayload;
import com.ps.js.service.ISkillService;
/**
 * 
 * @author Hemant
 *
 */
@RestController
@RequestMapping("/skills")
public class SkillController {

	@Autowired
	private ISkillService skillService;
    @Autowired
	private SkillMapper skillMapper;

	/**
	 * To find all the skills set for open a new job
	 * 
	 * @return {@link ResponseEntity}
	 */
	@GetMapping("/find-all-skill")
	public ResponseEntity<List<SkillPayload>> findAllSkill() {
		SkillPayload skillPayload = null;
		List<SkillPayload> listSkillPayload = new ArrayList<SkillPayload>();
		listSkillPayload = skillService.findAllSkills();
		
		
		return new ResponseEntity<List<SkillPayload>>(listSkillPayload, HttpStatus.OK);
	}
	
	/**
	 * To find skill by using skill-id
	 * 
	 * @param skillId
	 * @return {@link ResponseEntity<Skill>}
	 */
	@GetMapping("/find-skill-by-id/{skill-id}")
	public ResponseEntity<SkillPayload> findSKillById(@PathVariable("skill-id") int skillId) {
		SkillPayload skillPayload = null;
		// Fetch skill by skill-id
		Optional<SkillPayload> optionalSkill = skillService.fetchSkillById(skillId);

		if (optionalSkill.isEmpty())
			throw new SkillNotFoundException(ErrorMessages.SKILL_NOT_FOUND_EXCEPTION.toString());
		
		skillPayload = optionalSkill.get();
		return new ResponseEntity<SkillPayload>(skillPayload, HttpStatus.OK);
	}
	
	/**
	 * To added a new skill in the record
	 * 
	 * @param skill
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/add-skill")
	public ResponseEntity<Skill> addSkill(@Valid @RequestBody SkillPayload skillPayload) {
	   Skill skill=new Skill();
		skill=skillMapper.skillPayloadToSkillMapper(skillPayload, skill);
		Optional<Skill>	optionalSkill = skillService.addSkill(skill);
	   if(optionalSkill.isEmpty())
		   throw new SkillNotFoundException(ErrorMessages.SKILL_NOT_CREATED_EXCEPTION.toString());
	      skill=optionalSkill.get();
		return new ResponseEntity<Skill>(skill, HttpStatus.OK);
	}
	
	/**
	 * To update a skill in the record
	 * 
	 * @param skill
	 * @return {@link ResponseEntity}
	 */
    @PatchMapping("/update-skill")
    public ResponseEntity<Skill> updateSkill(@RequestBody SkillPayload skillPayload){
    	Skill skill=new Skill();   
    	//Mapping skillPayload to skill 
	     skill=skillMapper.skillPayloadToSkillMapper(skillPayload, skill);
    	//Checking skillId is available or not
    	  Optional<SkillPayload> optionalSkill=skillService.fetchSkillById(skill.getSkillId());
    	   if(optionalSkill.isEmpty())
    		   throw new SkillNotFoundException(ErrorMessages.SKILL_NOT_FOUND_EXCEPTION.toString());
    	   
    	     Optional<Skill> optionalSkillUpdated=skillService.updateSkillById(skill);
    	     if(optionalSkillUpdated.isEmpty())
    	    	   throw new SkillNotFoundException(ErrorMessages.SKILL_NOT_UPDATED_EXCEPTION.toString());
    	     
    	return new ResponseEntity<Skill>(optionalSkillUpdated.get(),HttpStatus.OK);
    	
    }
    
    /**
	 * To delete a available skill by skillId 
	 * 
	 * @param skill
	 * @return {@link ResponseEntity}
	 */
    @DeleteMapping("/delete-skill/{skill-id}")
    public ResponseEntity<String> deleteSkill(@PathVariable("skill-id") int skillId){ 
    	
    	//Checking skillId is available or not
    	  Optional<SkillPayload> optionalSkill=skillService.fetchSkillById(skillId);
    	   if(optionalSkill.isEmpty())
    		   throw new SkillNotFoundException(ErrorMessages.SKILL_NOT_FOUND_EXCEPTION.toString());
    	   Skill skill=skillService.deleteSkillById(skillId);
    	     
    	    	  
    	   return new ResponseEntity<String>("Skill is deleted skill-id ::"+skill.getSkillId(),HttpStatus.OK);
    	   
    }
    @GetMapping("/find-skill/{skill-id}")
    public ResponseEntity<SkillPayload> findSkill(@PathVariable("skill-id") int skillId){
    	Optional<SkillPayload> skillPayload=skillService.fetchSkillById(skillId);
    	  if(skillPayload.isEmpty())
    		  throw new SkillNotFoundException(ErrorMessages.SKILL_NOT_FOUND_EXCEPTION.toString());
    	return new ResponseEntity<SkillPayload>(skillPayload.get(),HttpStatus.OK);
    }
}
