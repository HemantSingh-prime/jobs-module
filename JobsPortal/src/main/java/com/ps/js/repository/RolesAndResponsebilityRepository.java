package com.ps.js.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps.js.entity.RolesAndResponsebility;

@Repository
public interface RolesAndResponsebilityRepository extends JpaRepository<RolesAndResponsebility,Integer> {

	Optional<RolesAndResponsebility> findByRolesResponsebility(final String rolesResponsebility);
}
