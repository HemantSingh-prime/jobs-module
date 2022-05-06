package com.ps.js.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps.js.entity.JobDetails;

@Repository
public interface JobDetailsRepository extends JpaRepository<JobDetails,Integer> {

	Optional<JobDetails> findByJobCode(final String jobCode);
	
	List<JobDetails> findByJobStatus(final String jobStatus);
}
