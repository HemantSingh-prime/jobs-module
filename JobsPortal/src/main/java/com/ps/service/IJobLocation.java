package com.ps.service;

import java.util.List;
import java.util.Optional;

import com.ps.entity.JobLocation;

public interface IJobLocation {

	public Optional<JobLocation> fetchJobLocationByName(final String locationName );
	
	public List<JobLocation> fetchAllLocation();
}
