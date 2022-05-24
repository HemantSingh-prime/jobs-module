package com.ps.js.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.ps.js.entity.Skill;
import com.ps.js.payload.SkillPayload;
/**
 * 
 * @author Hemant
 *
 */
@Component
public class SkillMapperImpl implements SkillMapper {

	
	/**
	 * To mapped object skillPayload to skill 
	 */
	@Override
	public Skill skillPayloadToSkillMapper(SkillPayload skillPayload, Skill skill) {
		BeanUtils.copyProperties(skillPayload, skill);
		return skill;
	}

	@Override
	public SkillPayload skillToSkillPayloadMapper(Skill skill, SkillPayload skillPayload) {
		BeanUtils.copyProperties(skill, skillPayload);
		return skillPayload;
	}

}
