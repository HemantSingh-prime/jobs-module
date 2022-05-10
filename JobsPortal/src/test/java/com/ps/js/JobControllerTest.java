package com.ps.js;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import com.ps.js.entity.RolesAndResponsebility;
import com.ps.js.entity.Skill;
import com.ps.js.exception.ErrorMessages;
import com.ps.js.exception.SkillNotFoundException;
import com.ps.js.mapper.JobDetailsMapperImpl;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobDetailsResponsePayload;
import com.ps.js.service.DepartmentServiceImpl;
import com.ps.js.service.JobDetailsServiceImpl;
import com.ps.js.service.JobLocationServices;
import com.ps.js.service.RolesAndResponsebilityServiceImpl;
import com.ps.js.service.SkillServicesImpl;

@WebMvcTest
@RunWith(MockitoJUnitRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class JobControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private JobDetailsServiceImpl jobDetailsServiceImpl;
    @MockBean
    private SkillServicesImpl skillServicesImpl;
    @MockBean
    private JobLocationServices jobLocationServices;
    @MockBean
    private RolesAndResponsebilityServiceImpl rolesAndResponsebilityServiceImpl; 
    @MockBean
    private DepartmentServiceImpl departmentServiceImpl;
    @MockBean
    private JobDetailsMapperImpl jobDetailsMapperImpl;
    private static ObjectMapper objectMapper=new ObjectMapper();
    private JobDetailsPayload jobDetailsRequestPayload;
    private JobDetailsResponsePayload jobDetailsResponsePayload;
    private JobDetails jobDetails;
    private JobLocation jobLocation;
    private Optional<JobLocation> optionalLocation;
    private Skill primarySkill;
    private Skill secondrySkill;
    private RolesAndResponsebility rolesAndResponsebility;
    private RolesAndResponsebility rolesAndResponsebility2;
    private Optional<Skill> optionalPrimarySkill;
    private Optional<Skill> optionalSecondrySkill;
    private Optional<RolesAndResponsebility> optionalRolesAndResponsebility;
    private Optional<RolesAndResponsebility> optionalRolesAndResponsebility2;
    @BeforeAll
    void initUseCase() throws Exception{
    	jobDetailsResponsePayload=new JobDetailsResponsePayload();
    	jobDetailsRequestPayload=new JobDetailsPayload();
    	primarySkill=new Skill();
    	secondrySkill=new Skill();
    	rolesAndResponsebility=new RolesAndResponsebility();
    	jobDetails=new JobDetails();
    	jobLocation=new JobLocation();
    	primarySkill=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/primary-skill.json"),Skill.class);
    	secondrySkill=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/secondry-skill.json"),Skill.class);
    	rolesAndResponsebility=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/roles-and-responsebility.json"),RolesAndResponsebility.class); 
    	rolesAndResponsebility2=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/roles-and-responsebility2.json"),RolesAndResponsebility.class);
    	jobLocation=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/job-location.json"),JobLocation.class);
    	optionalLocation=Optional.of(jobLocation);
    	optionalPrimarySkill=Optional.of(primarySkill);
    	optionalSecondrySkill=Optional.of(secondrySkill);
    	optionalRolesAndResponsebility=Optional.of(rolesAndResponsebility);
    	optionalRolesAndResponsebility2=Optional.of(rolesAndResponsebility2);
    	jobDetailsRequestPayload=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/job-details-request-payload.json"),JobDetailsPayload.class);
    	jobDetails=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/job-details.json"),JobDetails.class);
    	jobDetailsResponsePayload=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/job-details-response-payload.json"),JobDetailsResponsePayload.class);
    	
    }
    
    /**
     * Successful test case when job details added to record and jobId is generated
     * @throws Exception
     */
    @Test
    void openJobs_successful() throws Exception{
    	JobDetails jobDetailsTest=new JobDetails();
    	
    	//System.out.println(jobDetails);
    	Mockito.when(jobDetailsMapperImpl.jobDetailsPayloadToJobDetailsMapper(jobDetailsRequestPayload, jobDetailsTest)).thenReturn(jobDetails);
    	Mockito.when(jobLocationServices.fetchJobLocationById(1)).thenReturn(optionalLocation);
    	Mockito.when(skillServicesImpl.fetchSkillById(1)).thenReturn(optionalPrimarySkill);
    	Mockito.when(skillServicesImpl.fetchSkillById(2)).thenReturn(optionalSecondrySkill);
    	Mockito.when(rolesAndResponsebilityServiceImpl.fetchRolesAndResponsebilityById(1)).thenReturn(optionalRolesAndResponsebility);
    	Mockito.when(rolesAndResponsebilityServiceImpl.fetchRolesAndResponsebilityById(2)).thenReturn(optionalRolesAndResponsebility2);
    	Mockito.when(jobDetailsServiceImpl.createJob(jobDetails)).thenReturn(jobDetailsResponsePayload);
    	String json=objectMapper.writeValueAsString(jobDetailsRequestPayload);
    	mockMvc.perform(post("/jobs/open-job/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
		.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()); 
    	
    }
    /**
     * Failed test case when skillName is not available for given skillId
     * @throws Exception
     */
     @Test
     void openJobs_unsuccessful() throws Exception{
    	 JobDetails jobDetailsTest=new JobDetails();
//    	 Set<Skill> pSkills=new HashSet<Skill>();
//    	 Skill skill=new Skill();
    	 
//     	 optionalPrimarySkill=Optional.of(primarySkill);
    	// jobDetails.setPrimarySkill(null);
//    	 skill.setSkillId(3);//skill.setSkillName("SQL");
//    	 pSkills.add(skill);
//    	jobDetails.setPrimarySkill(pSkills);
     	//System.out.println(jobDetails);
     	Mockito.when(jobDetailsMapperImpl.jobDetailsPayloadToJobDetailsMapper(jobDetailsRequestPayload, jobDetailsTest)).thenReturn(jobDetails);
     	Mockito.when(jobLocationServices.fetchJobLocationById(1)).thenReturn(optionalLocation);
     	Mockito.when(skillServicesImpl.fetchSkillById(1)).thenThrow(new SkillNotFoundException(ErrorMessages.PRIMARY_SKILL_NOT__FOUND_EXCEPTION.toString()));
     	Mockito.when(skillServicesImpl.fetchSkillById(2)).thenReturn(optionalSecondrySkill);
     	Mockito.when(rolesAndResponsebilityServiceImpl.fetchRolesAndResponsebilityById(1)).thenReturn(optionalRolesAndResponsebility);
     	Mockito.when(rolesAndResponsebilityServiceImpl.fetchRolesAndResponsebilityById(2)).thenReturn(optionalRolesAndResponsebility2);
     	Mockito.when(jobDetailsServiceImpl.createJob(jobDetails)).thenReturn(jobDetailsResponsePayload);
     	String json=objectMapper.writeValueAsString(jobDetailsRequestPayload);
     	mockMvc.perform(post("/jobs/open-job/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
 		.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()); 
     }
     
     @Test
     void findAllSkill_successful() {
    	 
     }
}
