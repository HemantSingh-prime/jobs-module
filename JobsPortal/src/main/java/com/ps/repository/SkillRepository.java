package com.ps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

   Optional<Skill>	findBySkillName(final String skillName);
}
