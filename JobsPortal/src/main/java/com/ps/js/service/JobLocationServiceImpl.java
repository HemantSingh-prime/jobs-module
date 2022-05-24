package com.ps.js.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.Option;
import com.ps.js.entity.JobLocation;
import com.ps.js.exception.ErrorMessages;
import com.ps.js.exception.LocationsNotFoundException;
import com.ps.js.mapper.JobLocationMapper;
import com.ps.js.payload.JobDetailsPayload;
import com.ps.js.payload.JobLocationPayload;
import com.ps.js.repository.JobLocationRepository;
/**
 * 
 * @author Hemant
 *
 */
@Service
public class JobLocationServiceImpl implements IJobLocationService {

	@Autowired
	private JobLocationRepository jobLocationRepository;
	@Autowired
	private JobLocationMapper jobLocationMapper;
	
	
	/**
	 * To fetch all the available job locations  
	 * @param 
	 * @return {@link List<JobLocationPayload>}
	 * 
	 */
	@Override
	public List<JobLocationPayload> fetchAllLocation() {
		List<JobLocationPayload> listJobLocationPayloads=new ArrayList<JobLocationPayload>();
		JobLocationPayload jobLocationPayload=null;
		
		List<JobLocation> listJobLocations=jobLocationRepository.findAll();
		//Mapping joblocation to jobLocationPayload
		 for(JobLocation jobLocation:listJobLocations) {
			 jobLocationPayload=new JobLocationPayload();
			   jobLocationPayload=jobLocationMapper.jobLocationToJobLocationPayloadMapper(jobLocation, jobLocationPayload);
			   listJobLocationPayloads.add(jobLocationPayload);
		 }
		 // System.out.println(listJobLocationPayloads);
		return listJobLocationPayloads;
	}
	/**
	 * To open new location and save in job locations  
	 * @param 
	 * @return {@link JobLocationPayload}
	 * 
	 */
	@Override
	public JobLocationPayload saveLocation(JobLocation jobLocation) {
		    JobLocationPayload jobLocationPayload=new JobLocationPayload();
		    JobLocation jobLocationCreated=jobLocationRepository.save(jobLocation);
		     //Mapping jobLocation to jobLocationPayload
		    jobLocationPayload=jobLocationMapper.jobLocationToJobLocationPayloadMapper(jobLocationCreated, jobLocationPayload);
		return jobLocationPayload;
		
	}
	
	/**
	 * To find job location by location-id
	 * @param locationId
	 * @return {@link Optional<JobLocation>}
	 * 
	 */
	@Override
	public Optional<JobLocation> fetchJobLocationById(int locationId)throws LocationsNotFoundException {
		Optional<JobLocation> optionalJobLocation=jobLocationRepository.findById(locationId);
		if(optionalJobLocation.isEmpty())
			throw new LocationsNotFoundException();
		return optionalJobLocation;
	}

	/**
	 * To update job location by location-id
	 * @param locationId
	 * @return {@link JobLocation}
	 * 
	 */
	@Override
	public JobLocation updateJobLoactionById(JobLocation jobLocation) {
		Optional<JobLocation> optionalJobLocation=jobLocationRepository.findById(jobLocation.getLocationId());
		if(optionalJobLocation.isEmpty())
			throw new LocationsNotFoundException(ErrorMessages.LOCATION_NOT__FOUND_EXCEPTION.toString());
		
		JobLocation jobLocationUpdated=jobLocationRepository.save(jobLocation);
		    
		return jobLocationUpdated;
	}

	/**
	 * To delete job location by location-id
	 * @param locationId
	 * @return {@link JobLocation>}
	 * 
	 */
	@Override
	public JobLocation deleteJobLoactionById(int locationId) {
		  Optional<JobLocation> optionalJobLocation=jobLocationRepository.findById(locationId);
		  if(optionalJobLocation.isEmpty())
			  throw new LocationsNotFoundException(ErrorMessages.LOCATION_NOT__FOUND_EXCEPTION.toString());
		  jobLocationRepository.deleteById(locationId);
		
		return optionalJobLocation.get();
	}

}
