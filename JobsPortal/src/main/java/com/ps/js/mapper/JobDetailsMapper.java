package com.ps.js.mapper;

import com.ps.js.entity.JobDetails;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobDetailsResponsePayload;

public interface JobDetailsMapper {

	public JobDetailsResponsePayload  jobDetailToJobDetailsResponsePayloadMapper(JobDetails jobDetails,JobDetailsResponsePayload jobDetailsPayload);
	
	public JobDetails  jobDetailsPayloadToJobDetailsMapper(JobDetailsPayload jobDetailsPayload,JobDetails jobDetails);
}
