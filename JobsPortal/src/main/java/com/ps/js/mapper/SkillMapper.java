package com.ps.js.mapper;

import com.ps.js.entity.Skill;
import com.ps.js.payload.SkillPayload;

public interface SkillMapper {

	public Skill skillPayloadToSkillMapper(SkillPayload skillPayload,Skill skill);
	
	public SkillPayload skillToSkillPayloadMapper(Skill skill,SkillPayload skillPayload);
}
