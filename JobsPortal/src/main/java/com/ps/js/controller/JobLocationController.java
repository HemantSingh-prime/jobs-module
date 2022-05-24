package com.ps.js.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ps.js.entity.JobLocation;
import com.ps.js.exception.ErrorMessages;
import com.ps.js.exception.JobDetailsNotFoundException;
import com.ps.js.exception.LocationsNotFoundException;
import com.ps.js.mapper.JobLocationMapper;
import com.ps.js.payload.JobLocationPayload;
import com.ps.js.service.IJobLocationService;
/**
 * 
 * @author Hemant
 *
 */
@RestController
@RequestMapping("/locations")
public class JobLocationController {

	@Autowired
	private IJobLocationService jobLocationService;
	@Autowired
	private JobLocationMapper jobLocationMapper;
	/**
	 * To added a new job location in the record
	 * 
	 * @param jobLocationPayload
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/add-job-location")
	public ResponseEntity<JobLocationPayload> addJobLocation(@RequestBody JobLocationPayload jobLocationPayload){
		  JobLocation jobLocation=new JobLocation();
		  jobLocation=jobLocationMapper.jobLocationPayloadToJobLocationMapper(jobLocationPayload, jobLocation);
		JobLocationPayload location = jobLocationService.saveLocation(jobLocation);
		return new ResponseEntity<JobLocationPayload>(location,HttpStatus.OK);
	}
	
	/**
	 * To find all the job location set for open a new job
	 * @param
	 * @return {@link ResponseEntity}
	 */
	@GetMapping("/find-all-locations")
	public ResponseEntity<List<JobLocationPayload>> findAllJobLocation() {
		JobLocationPayload jobLocationPayload = null;
		List<JobLocationPayload> listJobLocationPayload = new ArrayList<JobLocationPayload>();
		listJobLocationPayload = jobLocationService.fetchAllLocation();
		

		return new ResponseEntity<List<JobLocationPayload>>(listJobLocationPayload, HttpStatus.OK);
	}
	/**
	 * To find the job location by using location-id
	 * @param locationId
	 * @return {@link ResponseEntity}
	 */
	@GetMapping("/find-job-location/{location-id}")
	public ResponseEntity<JobLocation> findJobLocation(@PathVariable("location-id") int locationId){
		Optional<JobLocation> optionalJobLocation=jobLocationService.fetchJobLocationById(locationId);
		
		if(optionalJobLocation.isEmpty())
			throw new JobDetailsNotFoundException(ErrorMessages.LOCATION_NOT__FOUND_EXCEPTION.toString());
		JobLocation jobLocation=optionalJobLocation.get();
		
		return new ResponseEntity<JobLocation>(jobLocation,HttpStatus.OK);
	}
	
	/**
	 * To update the job location by using location-id
	 * @param jobLocation
	 * @return {@link ResponseEntity}
	 */
	@PatchMapping("/update-location")
	public ResponseEntity<JobLocation> updateLocation(@RequestBody JobLocation jobLocation){
		    //Checking locationId is available in data base record or not
		      Optional<JobLocation> optionalJobLocation=jobLocationService.fetchJobLocationById(jobLocation.getLocationId());
		      if(optionalJobLocation.isEmpty())
		    	  throw new LocationsNotFoundException(ErrorMessages.LOCATION_NOT__FOUND_EXCEPTION.toString());
		      //Updating location record in data base table 
		      JobLocation jobLocationUpdated=jobLocationService.updateJobLoactionById(jobLocation);
		      
		return new ResponseEntity<JobLocation>(jobLocationUpdated,HttpStatus.OK);
	}
	/**
	 * To update the job location by using location-id
	 * @param jobLocation
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping("/delete-location")
	public ResponseEntity<String> deleteLocation(@PathVariable("location-id") int locationId){
		    //Checking locationId is available in data base record or not
		      Optional<JobLocation> optionalJobLocation=jobLocationService.fetchJobLocationById(locationId);
		      if(optionalJobLocation.isEmpty())
		    	  throw new LocationsNotFoundException(ErrorMessages.LOCATION_NOT__FOUND_EXCEPTION.toString());
		      //Updating location record in data base table 
		      JobLocation jobLocationUpdated=jobLocationService.deleteJobLoactionById(locationId);
		      
		return new ResponseEntity<String>("Job Location deleted id is ::"+jobLocationUpdated.getLocationId(),HttpStatus.OK);
	}
}
