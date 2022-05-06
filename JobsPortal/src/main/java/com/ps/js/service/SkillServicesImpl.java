package com.ps.js.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.js.entity.Skill;
import com.ps.js.exception.SkillNotFoundException;
import com.ps.js.repository.SkillRepository;

@Service
public class SkillServicesImpl implements ISkillServices {

	@Autowired
	private SkillRepository skillRepository;
	
	@Override
	public Optional<Skill> fetchSkillByName(String skillName) {
		Optional<Skill> optionalSkill=skillRepository.findBySkillName(skillName);
		return optionalSkill;
	}

	@Override
	public Optional<Skill> addSkill(Skill skill) {
		// TODO Auto-generated method stub
		return Optional.of(skillRepository.save(skill));
	}

	@Override
	public List<Skill> findAllSkills() {
		  
		return skillRepository.findAll();
	}

	@Override
	public Optional<Skill> fetchSkillById(int skillId) throws SkillNotFoundException {
		Optional<Skill> skill=null;
		
		      skill=skillRepository.findById(skillId);
		     if (skill.isEmpty())
			     throw new SkillNotFoundException();
		
		   
		return skill;
	}

	
}