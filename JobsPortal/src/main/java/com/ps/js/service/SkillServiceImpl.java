package com.ps.js.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.js.entity.Skill;
import com.ps.js.exception.ErrorMessages;
import com.ps.js.exception.SkillNotFoundException;
import com.ps.js.mapper.SkillMapper;
import com.ps.js.payload.SkillPayload;
import com.ps.js.repository.SkillRepository;

@Service
public class SkillServiceImpl implements ISkillService {

	@Autowired
	private SkillRepository skillRepository;
	@Autowired
	private SkillMapper skillMapper;

	@Override
	public Optional<Skill> fetchSkillByName(String skillName) {
		Optional<Skill> optionalSkill = skillRepository.findBySkillName(skillName);
		return optionalSkill;
	}

	@Override
	public Optional<Skill> addSkill(Skill skill) {
		
		return Optional.of(skillRepository.save(skill));
	}

	/**
	 * To fetch all the available skill  
	 * @param 
	 * @return {@link List<SkillPayload>}
	 * 
	 */
	@Override
	public List<SkillPayload> findAllSkills() {
		List<SkillPayload> listSkillPayloads=new ArrayList<SkillPayload>();
		SkillPayload skillPayload=null;
                List<Skill> listSkill=skillRepository.findAll();
                for(Skill skill:listSkill) {
                	skillPayload=new SkillPayload();
                	skillPayload=skillMapper.skillToSkillPayloadMapper(skill, skillPayload);
                	listSkillPayloads.add(skillPayload);
                }
                	
		return listSkillPayloads;
	}

	/**
	 * To find skill by skill-id
	 * 
	 * @param return {@link Optional<Skill>}
	 */
	@Override
	public Optional<SkillPayload> fetchSkillById(int skillId) throws SkillNotFoundException {
         SkillPayload skillPayload=null;
		Optional<Skill> optionalSkill = skillRepository.findById(skillId);
		skillPayload=skillMapper.skillToSkillPayloadMapper(optionalSkill.get(), skillPayload);
		Optional<SkillPayload> optionalSkillPayload=Optional.of(skillPayload);
		return optionalSkillPayload;
	}

	/**
	 * To update skill by using skill-id
	 * 
	 * @param return {@link skill}
	 */
	@Override
	public Optional<Skill> updateSkillById(Skill skill) {
		Optional<Skill> optionalSkill=skillRepository.findById(skill.getSkillId());
		if(optionalSkill.isEmpty())
			throw new SkillNotFoundException(ErrorMessages.SKILL_NOT_FOUND_EXCEPTION.toString());
		
		Optional<Skill> optionalSkillUpdated = Optional.of(skillRepository.save(skill));

		return optionalSkillUpdated;
	}
	/**
	 * To delete skill by using skill-id
	 * 
	 * @param return {@link skill}
	 */
	@Override
	public Skill deleteSkillById(int skillId) {
		  Optional<Skill>  optionalSkill=skillRepository.findById(skillId);
		  if(optionalSkill.isEmpty())
			  throw new SkillNotFoundException(ErrorMessages.SKILL_NOT_FOUND_EXCEPTION.toString());
		     skillRepository.deleteById(skillId);
		return optionalSkill.get();
	}

}
