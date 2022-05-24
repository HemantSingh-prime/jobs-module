package com.ps.js;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.js.controller.SkillController;
import com.ps.js.entity.Skill;
import com.ps.js.mapper.SkillMapper;
import com.ps.js.payload.SkillPayload;
import com.ps.js.service.SkillServiceImpl;

@WebMvcTest(value=SkillController.class)
@RunWith(MockitoJUnitRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SkillControllerTest {

	@Autowired 
	private MockMvc mockMvc;
	@MockBean
	private SkillServiceImpl skillServiceImpl;
	@MockBean
	private SkillMapper skillMapper;
	
	private static ObjectMapper objectMapper=new ObjectMapper();
	
	private Skill skillRequest;
	private Skill skillResponse;
	private SkillPayload skillPayloadRequest;
	private SkillPayload skillPayloadResponse;
	private SkillPayload skillPayloadResponse1;
	
	@BeforeAll
	void init() throws Exception{
		skillRequest=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/primary-skill.json"), Skill.class);
		skillResponse=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/primary-skill.json"), Skill.class);
		skillPayloadRequest=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/primary-skill.json"), SkillPayload.class);
		skillPayloadResponse=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/primary-skill.json"), SkillPayload.class);
		skillPayloadResponse1=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/secondry-skill.json"), SkillPayload.class);
	}
	
	/**
	 * To test find all skill end point successful
	 * @throws Exception
	 */
	@Test
	void findAllSkill_successful() throws Exception{
		List<SkillPayload> listSkillPayloads=new ArrayList<SkillPayload>();
		listSkillPayloads.add(skillPayloadResponse);listSkillPayloads.add(skillPayloadResponse1);
		Mockito.when(skillServiceImpl.findAllSkills()).thenReturn(listSkillPayloads);
		
		mockMvc.perform(get("/skills/find-all-skill/").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * To test update skill end point successful
	 * @throws Exception
	 */
	@Test
	void updateSkill_successful()throws Exception{
		Skill skill=new Skill();
		Optional<Skill> optionalSKill=Optional.of(skillRequest);
		Optional<Skill> optionalSkillUpdated=Optional.of(skillResponse); 
		Mockito.when(skillMapper.skillPayloadToSkillMapper(skillPayloadRequest, skill)).thenReturn(skillRequest);
		Mockito.when(skillServiceImpl.fetchSkillById(1)).thenReturn(optionalSKill);
		Mockito.when(skillServiceImpl.updateSkillById(skillRequest)).thenReturn(optionalSkillUpdated);
		
       String json=objectMapper.writeValueAsString(skillPayloadRequest);
		
		mockMvc.perform(patch("/skills/update-skill/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * To test update skill end point unsuccessful
	 * @throws Exception
	 */
	@Test
	void updateSkill_unsuccessful()throws Exception{
		Skill skill=new Skill();
		Optional<Skill> optionalSKill=Optional.empty();
		Optional<Skill> optionalSkillUpdated=Optional.of(skillResponse); 
		Mockito.when(skillMapper.skillPayloadToSkillMapper(skillPayloadRequest, skill)).thenReturn(skillRequest);
		Mockito.when(skillServiceImpl.fetchSkillById(1)).thenReturn(optionalSKill);
		Mockito.when(skillServiceImpl.updateSkillById(skillRequest)).thenReturn(optionalSkillUpdated);
		
       String json=objectMapper.writeValueAsString(skillPayloadRequest);
		
		mockMvc.perform(patch("/skills/update-skill/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}
	
	/**
	 * To test add skill end point successful
	 * @throws Exception
	 */
	@Test
	void addSkill_successful() throws Exception {
		Skill skill=new Skill();
		Optional<Skill> optionalSKill=Optional.of(skillResponse);
		Mockito.when(skillMapper.skillPayloadToSkillMapper(skillPayloadRequest, skill)).thenReturn(skillRequest);
		Mockito.when(skillServiceImpl.addSkill(skillRequest)).thenReturn(optionalSKill);
		
      String json=objectMapper.writeValueAsString(skillPayloadRequest);
		
		mockMvc.perform(post("/skills/add-skill/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

	}
	
	/**
	 * To test add skill end point unsuccessful
	 * @throws Exception
	 */
	@Test
	void addSkill_unsuccessful() throws Exception {
		Skill skill=new Skill();
		Optional<Skill> optionalSKill=Optional.empty();
		Mockito.when(skillMapper.skillPayloadToSkillMapper(skillPayloadRequest, skill)).thenReturn(skillRequest);
		Mockito.when(skillServiceImpl.addSkill(skillRequest)).thenReturn(optionalSKill);
		
      String json=objectMapper.writeValueAsString(skillPayloadRequest);
		
		mockMvc.perform(post("/skills/add-skill/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());

	}
	/**
	 * To test delete skill end point successful
	 * @throws Exception
	 */
	@Test
	void deleteSkill_successful() throws Exception {
		Optional<Skill> optionalSKill=Optional.of(skillResponse);
		Mockito.when(skillServiceImpl.fetchSkillById(1)).thenReturn(optionalSKill);
		Mockito.when(skillServiceImpl.deleteSkillById(1)).thenReturn(skillResponse);
		
		mockMvc.perform(delete("/skills/delete-skill/{skill-id}",1).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * To test delete skill end point unsuccessful
	 * @throws Exception
	 */
	@Test
	void deleteSkill_unsuccessful() throws Exception {
		Optional<Skill> optionalSKill=Optional.empty();
		Mockito.when(skillServiceImpl.fetchSkillById(1)).thenReturn(optionalSKill);
		Mockito.when(skillServiceImpl.deleteSkillById(1)).thenReturn(skillResponse);
		
		mockMvc.perform(delete("/skills/delete-skill/{skill-id}",1).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}
}
