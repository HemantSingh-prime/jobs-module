package com.ps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.entity.JobLocation;
import com.ps.repository.JobLocationRepository;

@Service
public class JobLocationServices implements IJobLocation {

	@Autowired
	private JobLocationRepository jobLocationRepository;
	
	@Override
	public Optional<JobLocation> fetchJobLocationByName(String locationName) {
		Optional<JobLocation> jobLocationOptional=jobLocationRepository.findByLocationName(locationName);
		return jobLocationOptional;
	}

	@Override
	public List<JobLocation> fetchAllLocation() {
		// TODO Auto-generated method stub
		return null;
	}

}
