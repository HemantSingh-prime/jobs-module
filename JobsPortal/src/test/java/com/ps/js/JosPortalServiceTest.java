package com.ps.js;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockitoSession;

import java.io.File;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
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
import com.ps.js.entity.JobLocation;

import com.ps.js.entity.Skill;
import com.ps.js.exception.JobDetailsNotCreatedException;
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
}
