package com.ps.js;

import static org.assertj.core.api.Assertions.assertThat;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
import com.ps.js.entity.JobDetails;

import com.ps.js.mapper.JobDetailsMapperImpl;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobDetailsResponsePayload;
import com.ps.js.repository.JobDetailsRepository;
import com.ps.js.service.JobDetailsServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class JosPortalServiceTest {

	@Mock
	private JobDetailsServiceImpl jobDetailsServiceImpl;
	@MockBean
	private JobDetailsMapperImpl jobDetailsMapperImpl;
	@MockBean
	private JobDetailsRepository jobDetailsRepository;
	private static ObjectMapper objectMapper=new ObjectMapper();
	private JobDetails jobDetails;
	private JobDetails jobDetailsResponse;
	private JobDetailsResponsePayload jobDetailsResponsePayload;
	
	@BeforeAll
	void initUseCase() throws Exception{
	jobDetails=new JobDetails();
	jobDetailsResponsePayload=new JobDetailsResponsePayload();
	jobDetailsResponse=new JobDetails();
	jobDetails=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/job-details.json"),JobDetails.class);
	jobDetailsResponse=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/job-details-response.json"),JobDetails.class);
	jobDetailsResponsePayload=objectMapper.readValue(new File("src/test/java/com/ps/js/payload/job-details-response-payload.json"),JobDetailsResponsePayload.class);
	}
	/**
	 * To test create job method success
	 * @throws Exception
	 */
	@Test
	void createJob_success()throws Exception{
		JobDetailsResponsePayload response=new JobDetailsResponsePayload();
		Mockito.when(jobDetailsRepository.save(jobDetails)).thenReturn(jobDetailsResponse);
		Mockito.when(jobDetailsMapperImpl.jobDetailToJobDetailsResponsePayloadMapper(jobDetailsResponse, jobDetailsResponsePayload)).thenReturn(jobDetailsResponsePayload);
		Mockito.when(jobDetailsServiceImpl.createJob(jobDetails)).thenReturn(jobDetailsResponsePayload);
		response=jobDetailsServiceImpl.createJob(jobDetails);
		assertThat(response.getJobId()).isEqualTo(jobDetailsResponse.getJobId());
	}
	
	@Test
	void findAllJob_successful() throws Exception{
		List<JobDetails> listJobDetails=new ArrayList<JobDetails>();
		listJobDetails.add(jobDetailsResponse);
		Mockito.when(jobDetailsServiceImpl.findAllJob()).thenReturn(listJobDetails);
		List<JobDetails> list=jobDetailsServiceImpl.findAllJob();
		assertThat(list.size()).isGreaterThanOrEqualTo(1);
				
	}
	
	@Test
	void updateJobDetails_successful() throws Exception {
		//JobDetailsPayload jobDetailsPayload = jobDetailsRequestPayload;
		//jobDetailsPayload.setJobId(1);
		JobDetails jobDetailsUpdated = new JobDetails();
		jobDetails.setJobId(1);
		JobDetails jobDetailsResponse = objectMapper
				.readValue(new File("src/test/java/com/ps/js/payload/job-details-response.json"), JobDetails.class);
		Optional<JobDetails> optionalJobDetails = Optional.of(jobDetailsResponse);
		Mockito.when(jobDetailsRepository.findById(1)).thenReturn(optionalJobDetails);
		//Mockito.when(jobLocationServices.fetchJobLocationById(1)).thenReturn(optionalLocation);
		//Mockito.when(skillServicesImpl.fetchSkillById(1)).thenReturn(optionalPrimarySkill);
		//Mockito.when(skillServicesImpl.fetchSkillById(2)).thenReturn(optionalSecondrySkill);
		
		Mockito.when(jobDetailsServiceImpl.updateJobDetails(jobDetails)).thenReturn(jobDetails);
		jobDetailsUpdated=jobDetailsServiceImpl.updateJobDetails(jobDetails);
		assertThat(jobDetails.getJobId()).isEqualTo(jobDetailsUpdated.getJobId());
	}
	
	
}
