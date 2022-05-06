package com.ps.js.service;

import java.util.List;
import java.util.Optional;

import com.ps.js.entity.JobLocation;

public interface IJobLocationServices {

	public Optional<JobLocation> fetchJobLocationByName(final String locationName );
	
	public List<JobLocation> fetchAllLocation();
	
	public JobLocation saveLocation(JobLocation jobLocation);
	
	public Optional<JobLocation> fetchJobLocationById(final int locationId);
}
