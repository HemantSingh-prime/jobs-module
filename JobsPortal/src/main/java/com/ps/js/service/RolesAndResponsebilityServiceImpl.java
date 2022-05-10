package com.ps.js.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.js.entity.RolesAndResponsebility;
import com.ps.js.exception.ErrorMessages;
import com.ps.js.exception.RolesAndResponsebilityNotFoundException;
import com.ps.js.repository.RolesAndResponsebilityRepository;

@Service
public class RolesAndResponsebilityServiceImpl implements IRolesAndResponsebilityService {

	@Autowired
	private RolesAndResponsebilityRepository rolesAndResponsebilityRepository;
	
	@Override
	public Optional<RolesAndResponsebility> fetchRolesAndResposebilityByResponsebility(final String rolesResponsebility) {
		
		return rolesAndResponsebilityRepository.findByRolesResponsebility(rolesResponsebility);
	}
     /**
      * To fetch all roles and responsebility
      */
	@Override
	public List<RolesAndResponsebility> fetchAllRolesAndResponsebility() {
		      
		return rolesAndResponsebilityRepository.findAll();
	}
    /**
     * To find roles and responsebility by id
     */
	@Override
	public Optional<RolesAndResponsebility> fetchRolesAndResponsebilityById(int id) {
		   Optional<RolesAndResponsebility> rolesAndResponsebility=rolesAndResponsebilityRepository.findById(id); 
		       if(rolesAndResponsebility.isEmpty())
		    	   throw new RolesAndResponsebilityNotFoundException(ErrorMessages.ROLES_AND_RESPONSEBILITY_NOT_FOUND.toString());
		return rolesAndResponsebility;
	}

}
