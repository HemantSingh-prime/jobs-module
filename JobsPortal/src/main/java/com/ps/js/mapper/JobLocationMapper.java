package com.ps.js.mapper;

import com.ps.js.entity.JobLocation;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobLocationPayload;

public interface JobLocationMapper {

	
	public JobLocationPayload jobLocationToJobLocationPayloadMapper(JobLocation jobLocation,JobLocationPayload jobLocationPayload);

	public JobLocation jobLocationPayloadToJobLocationMapper(JobLocationPayload jobLocationPayload,JobLocation jobLocation);
}
