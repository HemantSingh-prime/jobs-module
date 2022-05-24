package com.ps.js;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.js.entity.Skill;
import com.ps.js.exception.SkillNotFoundException;
import com.ps.js.repository.SkillRepository;
import com.ps.js.service.SkillServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SkillServiceTest {

	@Mock
	private SkillServiceImpl skillServicesImpl;
	@MockBean
	private SkillRepository repository;
	private static ObjectMapper objectMapper=new ObjectMapper();
	private Skill primarySkill;
	private Skill secondrySkill;
	private Optional<Skill> optionalPrimarySkill;
	private Optional<Skill> optionalSecondrySkill;
	@BeforeAll
	void initUseCase() throws Exception{
		primarySkill=new Skill();
		secondrySkill=new Skill();
		primarySkill = objectMapper.readValue(new File("src/test/java/com/ps/js/payload/primary-skill.json"),
				Skill.class);
		secondrySkill = objectMapper.readValue(new File("src/test/java/com/ps/js/payload/secondry-skill.json"),
				Skill.class);
		optionalPrimarySkill = Optional.of(primarySkill);
		optionalSecondrySkill = Optional.of(secondrySkill);
	}
	@Test
	void fetchSkillById_successful() {
		Mockito.when(repository.findById(1)).thenReturn(optionalPrimarySkill);
		Mockito.when(skillServicesImpl.fetchSkillById(1)).thenReturn(optionalPrimarySkill);
		
		Skill skill=skillServicesImpl.fetchSkillById(1).get();
		
		assertThat(skill.getSkillId()).isEqualTo(optionalPrimarySkill.get().getSkillId());
	}
	
	@Test
	void fetchSkillById_unsuccessful() {
		Optional<Skill> optionalSkill=Optional.empty();
		Mockito.when(repository.findById(1)).thenReturn(optionalSkill);
		Mockito.when(skillServicesImpl.fetchSkillById(1)).thenThrow(new SkillNotFoundException());
		
		Skill skill=skillServicesImpl.fetchSkillById(1).get();
		
		assertThat(skill.getSkillId()).isEqualTo(optionalPrimarySkill.get().getSkillId());
	}
}
