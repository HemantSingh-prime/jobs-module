package com.ps.js.service;

import java.util.List;
import java.util.Optional;

import com.ps.js.entity.Skill;

public interface ISkillServices {

	public Optional<Skill> fetchSkillByName(final String skillName);
	
	public Optional<Skill> addSkill(Skill skill);
	 
	public List<Skill> findAllSkills();
	
	public Optional<Skill> fetchSkillById(final int skillId);
	
}
