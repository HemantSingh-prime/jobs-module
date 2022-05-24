package com.ps.js.service;

import java.util.List;
import java.util.Optional;

import com.ps.js.entity.Skill;
import com.ps.js.payload.SkillPayload;


public interface ISkillService {

	public Optional<Skill> fetchSkillByName(final String skillName);
	
	public Optional<Skill> addSkill(Skill skill);
	 
	public List<SkillPayload> findAllSkills();
	
	public Optional<SkillPayload> fetchSkillById(final int skillId);
	
	public Optional<Skill> updateSkillById(Skill skill);
	
	public Skill deleteSkillById(final int skillId);
	
}
