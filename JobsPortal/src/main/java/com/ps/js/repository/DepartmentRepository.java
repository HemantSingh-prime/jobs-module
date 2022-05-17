package com.ps.js.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps.js.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {

}
