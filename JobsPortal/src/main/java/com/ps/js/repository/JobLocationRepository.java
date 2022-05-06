package com.ps.js.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps.js.entity.JobLocation;

@Repository
public interface JobLocationRepository extends JpaRepository<JobLocation, Integer> {

	Optional<JobLocation> findByLocationName(final String locationName);
	
	
}
