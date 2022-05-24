package com.ps.js.service;

import java.util.List;
import java.util.Optional;

import com.ps.js.entity.RolesAndResponsebility;

public interface IRolesAndResponsebilityService {

	public Optional<RolesAndResponsebility> fetchRolesAndResposebilityByResponsebility(final String rolesResponsebility);
	
	public List<RolesAndResponsebility> fetchAllRolesAndResponsebility();
	
	public Optional<RolesAndResponsebility> fetchRolesAndResponsebilityById(final int id);
}
