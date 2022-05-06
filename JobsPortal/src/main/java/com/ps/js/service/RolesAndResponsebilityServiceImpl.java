package com.ps.js.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.js.entity.RolesAndResponsebility;
import com.ps.js.repository.RolesAndResponsebilityRepository;

@Service
public class RolesAndResponsebilityServiceImpl implements IRolesAndResponsebilityService {

	@Autowired
	private RolesAndResponsebilityRepository rolesAndResponsebilityRepository;
	
	@Override
	public Optional<RolesAndResponsebility> fetchRolesAndResposebilityByResponsebility(final String rolesResponsebility) {
		
		return rolesAndResponsebilityRepository.findByRolesResponsebility(rolesResponsebility);
	}

	@Override
	public List<RolesAndResponsebility> fetchAllRolesAndResponsebility() {
		
		return rolesAndResponsebilityRepository.findAll();
	}

	@Override
	public Optional<RolesAndResponsebility> fetchRolesAndResponsebilityById(int id) {
		
		return rolesAndResponsebilityRepository.findById(id);
	}

}
