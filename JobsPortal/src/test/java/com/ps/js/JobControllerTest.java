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
import com.ps.js.entity.JobDetails;
import com.ps.js.entity.JobLocation;

import com.ps.js.entity.Skill;
import com.ps.js.mapper.JobDetailsMapperImpl;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobDetailsResponsePayload;
import com.ps.js.service.DepartmentServiceImpl;
import com.ps.js.service.JobDetailsServiceImpl;
import com.ps.js.service.JobLocationServiceImpl;
import com.ps.js.service.SkillServiceImpl;

@WebMvcTest
@RunWith(MockitoJUnitRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class JobControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private JobDetailsServiceImpl jobDetailsServiceImpl;
	@MockBean
	private SkillServiceImpl skillServicesImpl;
	@MockBean
	private JobLocationServiceImpl jobLocationServices;
	// @MockBean
	// private RolesAndResponsebilityServiceImpl rolesAndResponsebilityServiceImpl;
	@MockBean
	private DepartmentServiceImpl departmentServiceImpl;
	@MockBean
	private JobDetailsMapperImpl jobDetailsMapperImpl;
	private static ObjectMapper objectMapper = new ObjectMapper();
	private JobDetailsPayload jobDetailsRequestPayload;
	private JobDetailsResponsePayload jobDetailsResponsePayload;
	private JobDetails jobDetails;
	private JobLocation jobLocation;
	private Optional<JobLocation> optionalLocation;
	private Skill primarySkill;
	private Skill secondrySkill;
	// private RolesAndResponsebility rolesAndResponsebility;
	// private RolesAndResponsebility rolesAndResponsebility2;
	private Optional<Skill> optionalPrimarySkill;
	private Optional<Skill> optionalSecondrySkill;
	// private Optional<RolesAndResponsebility> optionalRolesAndResponsebility;
	// private Optional<RolesAndResponsebility> optionalRolesAndResponsebility2;

	@BeforeAll
	void initUseCase() throws Exception {
		jobDetailsResponsePayload = new JobDetailsResponsePayload();
		jobDetailsRequestPayload = new JobDetailsPayload();
		primarySkill = new Skill();
		secondrySkill = new Skill();
		// rolesAndResponsebility = new RolesAndResponsebility();
		jobDetails = new JobDetails();
		jobLocation = new JobLocation();
		primarySkill = objectMapper.readValue(new File("src/test/java/com/ps/js/payload/primary-skill.json"),
				Skill.class);
		secondrySkill = objectMapper.readValue(new File("src/test/java/com/ps/js/payload/secondry-skill.json"),
				Skill.class);
		/*
		 * rolesAndResponsebility = objectMapper.readValue( new
		 * File("src/test/java/com/ps/js/payload/roles-and-responsebility.json"),
		 * RolesAndResponsebility.class); rolesAndResponsebility2 =
		 * objectMapper.readValue( new
		 * File("src/test/java/com/ps/js/payload/roles-and-responsebility2.json"),
		 * RolesAndResponsebility.class);
		 */
		jobLocation = objectMapper.readValue(new File("src/test/java/com/ps/js/payload/job-location.json"),
				JobLocation.class);
		optionalLocation = Optional.of(jobLocation);
		optionalPrimarySkill = Optional.of(primarySkill);
		optionalSecondrySkill = Optional.of(secondrySkill);
		// optionalRolesAndResponsebility = Optional.of(rolesAndResponsebility);
		// optionalRolesAndResponsebility2 = Optional.of(rolesAndResponsebility2);
		jobDetailsRequestPayload = objectMapper.readValue(
				new File("src/test/java/com/ps/js/payload/job-details-request-payload.json"), JobDetailsPayload.class);
		jobDetails = objectMapper.readValue(new File("src/test/java/com/ps/js/payload/job-details.json"),
				JobDetails.class);
		jobDetailsResponsePayload = objectMapper.readValue(
				new File("src/test/java/com/ps/js/payload/job-details-response-payload.json"),
				JobDetailsResponsePayload.class);

	}

	/**
	 * Successful test case when job details added to record and jobId is generated
	 * 
	 * @throws Exception
	 */
	@Test
	void openJobs_successful() throws Exception {
		JobDetails jobDetailsTest = new JobDetails();

		// System.out.println(jobDetails);
		Mockito.when(jobDetailsMapperImpl.jobDetailsPayloadToJobDetailsMapper(jobDetailsRequestPayload, jobDetailsTest))
				.thenReturn(jobDetails);
		Mockito.when(jobLocationServices.fetchJobLocationById(1)).thenReturn(optionalLocation);
		Mockito.when(skillServicesImpl.fetchSkillById(1)).thenReturn(optionalPrimarySkill);
		Mockito.when(skillServicesImpl.fetchSkillById(2)).thenReturn(optionalSecondrySkill);
		/*
		 * Mockito.when(rolesAndResponsebilityServiceImpl.
		 * fetchRolesAndResponsebilityById(1))
		 * .thenReturn(optionalRolesAndResponsebility);
		 * Mockito.when(rolesAndResponsebilityServiceImpl.
		 * fetchRolesAndResponsebilityById(2))
		 * .thenReturn(optionalRolesAndResponsebility2);
		 */
		Mockito.when(jobDetailsServiceImpl.createJob(jobDetails)).thenReturn(jobDetailsResponsePayload);
		String json = objectMapper.writeValueAsString(jobDetailsRequestPayload);
		mockMvc.perform(post("/jobs/open-job/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

	}

	/**
	 * Failed test case when skillName is not available for given skillId
	 * 
	 * @throws Exception
	 */
	@Test
	void openJobs_unsuccessful_for_null_field() throws Exception {
		JobDetails jobDetailsTest = new JobDetails();
		jobDetailsRequestPayload.setPrimarySkill(null);
		Mockito.when(jobDetailsMapperImpl.jobDetailsPayloadToJobDetailsMapper(jobDetailsRequestPayload, jobDetailsTest))
				.thenReturn(jobDetails);
		Mockito.when(jobLocationServices.fetchJobLocationById(1)).thenReturn(optionalLocation);
		Mockito.when(skillServicesImpl.fetchSkillById(1)).thenReturn(optionalPrimarySkill);// thenThrow(new
																							// SkillNotFoundException(ErrorMessages.PRIMARY_SKILL_NOT__FOUND_EXCEPTION.toString()));
		Mockito.when(skillServicesImpl.fetchSkillById(2)).thenReturn(optionalSecondrySkill);
		/*
		 * Mockito.when(rolesAndResponsebilityServiceImpl.
		 * fetchRolesAndResponsebilityById(1))
		 * .thenReturn(optionalRolesAndResponsebility);
		 * Mockito.when(rolesAndResponsebilityServiceImpl.
		 * fetchRolesAndResponsebilityById(2))
		 * .thenReturn(optionalRolesAndResponsebility2);
		 */
		Mockito.when(jobDetailsServiceImpl.createJob(jobDetails)).thenReturn(jobDetailsResponsePayload);
		String json = objectMapper.writeValueAsString(jobDetailsRequestPayload);
		mockMvc.perform(post("/jobs/open-job/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}

	/**
	 * To test find all skill set
	 * 
	 * @throws Exception
	 */
//	@Test
//	void findAllSkill_successful() throws Exception {
//		List<Skill> listSkill = new ArrayList<Skill>();
//		listSkill.add(primarySkill);
//		listSkill.add(secondrySkill);
//		Mockito.when(skillServicesImpl.findAllSkills()).thenReturn(listSkill);
//		mockMvc.perform(get("/jobs/find-all-skill/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
//				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
//	}

	/**
	 * To test find all job location
	 * 
	 * @throws Exception
	 */
	@Test
	void findAllJobLocation_successful() throws Exception {
		List<JobLocation> listLocation = new ArrayList<JobLocation>();
		listLocation.add(jobLocation);

		//Mockito.when(jobLocationServices.fetchAllLocation());//.thenReturn(listLocation);
		mockMvc.perform(get("/jobs/find-all-locations/").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}

	/**
	 * To test find all roles and responsebility
	 * 
	 * @throws Exception
	 */
//	@Test
//	void findAllRolesAndResponsebility_successful() throws Exception {
//		List<RolesAndResponsebility> listRolesAndResponsebility = new ArrayList();
//		listRolesAndResponsebility.add(rolesAndResponsebility);
//		listRolesAndResponsebility.add(rolesAndResponsebility2);
//		Mockito.when(rolesAndResponsebilityServiceImpl.fetchAllRolesAndResponsebility())
//				.thenReturn(listRolesAndResponsebility);
//		mockMvc.perform(get("/jobs/find-all-roles-and-responsebility/").contentType(MediaType.APPLICATION_JSON)
//				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
//	}

	/**
	 * To test find skill by id successful
	 * 
	 * @throws Exception
	 */
	@Test
	void findSKillById_successful() throws Exception {
		Mockito.when(skillServicesImpl.fetchSkillById(1)).thenReturn(optionalPrimarySkill);
		Mockito.when(skillServicesImpl.fetchSkillById(2)).thenReturn(optionalSecondrySkill);
		mockMvc.perform(get("/jobs/find-skill-by-id/{skill-id}", 1).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}

	/**
	 * To test find skill by id unsuccessful
	 * 
	 * @throws Exception
	 */
	@Test
	void findSKillById_unsuccessful() throws Exception {
		Optional<Skill> optionalSkill = Optional.empty();
		Mockito.when(skillServicesImpl.fetchSkillById(1)).thenReturn(optionalSkill);
		// Mockito.when(skillServicesImpl.fetchSkillById(2)).thenReturn(optionalSecondrySkill);
		mockMvc.perform(get("/jobs/find-skill-by-id/{skill-id}", 1).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	/**
	 * To test find job location by id successful
	 * 
	 * @throws Exception
	 */
	@Test
	void findLocationById_successful() throws Exception {
		Mockito.when(jobLocationServices.fetchJobLocationById(1)).thenReturn(optionalLocation);
		mockMvc.perform(get("/jobs/find-location-by-id/{location-id}", 1).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

	}

	/**
	 * To test find job location by id unsuccessful
	 * 
	 * @throws Exception
	 */
	@Test
	void findLocationById_unsuccessful() throws Exception {

		Optional<JobLocation> optLocation = Optional.empty();
		Mockito.when(jobLocationServices.fetchJobLocationById(1)).thenReturn(optLocation);
		mockMvc.perform(get("/jobs/find-location-by-id/{location-id}", 1).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

	}

	/**
	 * To test find roles and responsebility by id successful
	 * 
	 * @throws Exception
	 */
//	@Test
//	void findRolesAndResponsebilityById_successful()throws Exception {
//		Mockito.when(rolesAndResponsebilityServiceImpl.fetchRolesAndResponsebilityById(1)).thenReturn(optionalRolesAndResponsebility);
//		Mockito.when(rolesAndResponsebilityServiceImpl.fetchRolesAndResponsebilityById(2)).thenReturn(optionalRolesAndResponsebility2);
//		mockMvc.perform(get("/jobs/find-roles-and-responsebility-by-id/{id}", 1).contentType(MediaType.APPLICATION_JSON)
//				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
//	}

	/**
	 * To test find roles and responsebility by id unsuccessful
	 * 
	 * @throws Exception
	 */
//	@Test
//	void findRolesAndResponsebilityById_unsuccessful()throws Exception {
//		Optional<RolesAndResponsebility> optionalRoleasAndResponsebility=Optional.empty(); 
//		Mockito.when(rolesAndResponsebilityServiceImpl.fetchRolesAndResponsebilityById(1)).thenReturn(optionalRoleasAndResponsebility);
//		//Mockito.when(rolesAndResponsebilityServiceImpl.fetchRolesAndResponsebilityById(2)).thenReturn(optionalRolesAndResponsebility2);
//		mockMvc.perform(get("/jobs/find-roles-and-responsebility-by-id/{id}", 1).contentType(MediaType.APPLICATION_JSON)
//				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
//	}
	/**
	 * To test find all jobDetails successful
	 * 
	 * @throws Exception
	 */
	@Test
	void findAllJob_successful() throws Exception {
		List<JobDetails> listJobDetails = new ArrayList<JobDetails>();
		listJobDetails.add(jobDetails);
		Mockito.when(jobDetailsServiceImpl.findAllJob()).thenReturn(listJobDetails);
		mockMvc.perform(get("/jobs/find-all-job/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}

	/**
	 * To test delete jobDetails successful
	 * 
	 * @throws Exception
	 */
	@Test
	void deleteJobDetails_successful() throws Exception {
		Optional<JobDetails> optionalJobDetails = Optional.of(jobDetails);

		Mockito.when(jobDetailsServiceImpl.findJobByJobId(1)).thenReturn(optionalJobDetails);
		Mockito.when(jobDetailsServiceImpl.deleteJobDetails(jobDetails)).thenReturn(optionalJobDetails);
		mockMvc.perform(delete("/jobs/delete-job-details/{job-id}", 1).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}

	/**
	 * To test delete jobDetails unsuccessful
	 * 
	 * @throws Exception
	 */
	@Test
	void deleteJobDetails_unsuccessful() throws Exception {
		Optional<JobDetails> optionalJobDetails = Optional.empty();

		Mockito.when(jobDetailsServiceImpl.findJobByJobId(1)).thenReturn(optionalJobDetails);
		Mockito.when(jobDetailsServiceImpl.deleteJobDetails(jobDetails)).thenReturn(optionalJobDetails);
		mockMvc.perform(delete("/jobs/delete-job-details/{job-id}", 1).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}

	/**
	 * To test update jobDetails successful
	 * 
	 * @throws Exception
	 */
	@Test
	void updateJobDetails_successful() throws Exception {
		JobDetailsPayload jobDetailsPayload = jobDetailsRequestPayload;
		jobDetailsPayload.setJobId(1);
		JobDetails jobDetailsUpdated = new JobDetails();
		jobDetails.setJobId(1);
		JobDetails jobDetailsResponse = objectMapper
				.readValue(new File("src/test/java/com/ps/js/payload/job-details-response.json"), JobDetails.class);
		Optional<JobDetails> optionalJobDetails = Optional.of(jobDetailsResponse);
		Mockito.when(jobDetailsServiceImpl.findJobByJobId(1)).thenReturn(optionalJobDetails);
		Mockito.when(jobLocationServices.fetchJobLocationById(1)).thenReturn(optionalLocation);
		Mockito.when(skillServicesImpl.fetchSkillById(1)).thenReturn(optionalPrimarySkill);
		Mockito.when(skillServicesImpl.fetchSkillById(2)).thenReturn(optionalSecondrySkill);
		Mockito.when(jobDetailsMapperImpl.jobDetailsPayloadToJobDetailsMapper(jobDetailsPayload, jobDetailsUpdated))
				.thenReturn(jobDetails);
		Mockito.when(jobDetailsServiceImpl.updateJobDetails(jobDetails)).thenReturn(jobDetails);

		String json = objectMapper.writeValueAsString(jobDetailsPayload);
		mockMvc.perform(patch("/jobs/update-job-details/").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	/**
	 * To test update jobDetails unsuccessful
	 * 
	 * @throws Exception
	 */
	@Test
	void updateJobDetails_unsuccessful() throws Exception {
		JobDetailsPayload jobDetailsPayload = jobDetailsRequestPayload;
		jobDetailsPayload.setJobId(1);
		JobDetails jobDetailsUpdated = new JobDetails();
		jobDetails.setJobId(1);
		// JobDetails jobDetailsResponse=objectMapper.readValue(new
		// File("src/test/java/com/ps/js/payload/job-details-response.json"),JobDetails.class);
		Optional<JobDetails> optionalJobDetails = Optional.empty();
		Mockito.when(jobDetailsServiceImpl.findJobByJobId(1)).thenReturn(optionalJobDetails);
		Mockito.when(jobLocationServices.fetchJobLocationById(1)).thenReturn(optionalLocation);
		Mockito.when(skillServicesImpl.fetchSkillById(1)).thenReturn(optionalPrimarySkill);
		Mockito.when(skillServicesImpl.fetchSkillById(2)).thenReturn(optionalSecondrySkill);
		Mockito.when(jobDetailsMapperImpl.jobDetailsPayloadToJobDetailsMapper(jobDetailsPayload, jobDetailsUpdated))
				.thenReturn(jobDetails);
		Mockito.when(jobDetailsServiceImpl.updateJobDetails(jobDetails)).thenReturn(jobDetails);

		String json = objectMapper.writeValueAsString(jobDetailsPayload);
		mockMvc.perform(patch("/jobs/update-job-details/").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
}
