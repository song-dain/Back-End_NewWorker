package com.greedy.newworker.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.newworker.employee.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
}
