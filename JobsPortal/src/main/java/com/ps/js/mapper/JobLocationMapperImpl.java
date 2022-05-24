package com.ps.js.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.ps.js.entity.JobLocation;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobLocationPayload;

@Component
public class JobLocationMapperImpl implements JobLocationMapper {

	@Override
	public JobLocationPayload jobLocationToJobLocationPayloadMapper(JobLocation jobLocation,
			JobLocationPayload jobLocationPayload) {
		if(jobLocation!=null)
		BeanUtils.copyProperties(jobLocation, jobLocationPayload);

		
			return jobLocationPayload;
	}

	@Override
	public JobLocation jobLocationPayloadToJobLocationMapper(JobLocationPayload jobLocationPayload,
			JobLocation jobLocation) {
		
		    if(jobLocationPayload!=null)
		    BeanUtils.copyProperties(jobLocationPayload, jobLocation);
		return jobLocation;
	}

}
