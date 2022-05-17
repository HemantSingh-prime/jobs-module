package com.ps.js.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.js.entity.JobLocation;
import com.ps.js.exception.LocationsNotFoundException;
import com.ps.js.repository.JobLocationRepository;

@Service
public class JobLocationServices implements IJobLocationServices {

	@Autowired
	private JobLocationRepository jobLocationRepository;
	
	

	@Override
	public List<JobLocation> fetchAllLocation() {
		
		return jobLocationRepository.findAll();
	}

	@Override
	public JobLocation saveLocation(JobLocation jobLocation) {
		    JobLocation jobLocation1=jobLocationRepository.save(jobLocation);
		    System.out.println(jobLocation1);
		return jobLocation1;
	}
    /**
     * To find job location by location-id
     * 
     */
	@Override
	public Optional<JobLocation> fetchJobLocationById(int locationId)throws LocationsNotFoundException {
		Optional<JobLocation> optionalJobLocation=jobLocationRepository.findById(locationId);
		if(optionalJobLocation.isEmpty())
			throw new LocationsNotFoundException();
		return optionalJobLocation;
	}

}
