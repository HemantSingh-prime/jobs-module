package com.ps.service;

import java.util.Optional;

import com.ps.entity.Skill;

public interface ISkillServices {

	public Optional<Skill> fetchSkillByName(final String skillName);
	
	public Optional<Skill> addSkill(Skill skill);
	
	
}
