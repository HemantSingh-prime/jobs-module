package com.ps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.entity.Skill;
import com.ps.repository.SkillRepository;

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
		return null;
	}

}
