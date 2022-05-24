package com.ps.js.service;

import java.util.List;
import java.util.Optional;

import com.ps.js.entity.JobLocation;
import com.ps.js.payload.JobLocationPayload;

public interface IJobLocationService {

	
	
	public List<JobLocationPayload> fetchAllLocation();
	
	public JobLocationPayload saveLocation(JobLocation jobLocation);
	
	public Optional<JobLocation> fetchJobLocationById(final int locationId);
	
	public JobLocation updateJobLoactionById(JobLocation jobLocation);
	
	public JobLocation deleteJobLoactionById(final int locationId);
}
