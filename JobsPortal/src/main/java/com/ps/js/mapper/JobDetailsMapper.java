package com.ps.js.mapper;

import com.ps.js.entity.JobDetails;
import com.ps.js.payload.JobDetailsPayload;

public interface JobDetailsMapper {

	public JobDetailsPayload  jobDetailToJobDetailsPayloadMapper(JobDetails jobDetails,JobDetailsPayload jobDetailsPayload);
	
	public JobDetails  jobDetailsPayloadToJobDetailsMapper(JobDetailsPayload jobDetailsPayload,JobDetails jobDetails);
}
