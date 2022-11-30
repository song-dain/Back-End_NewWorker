package com.greedy.newworker.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.newworker.employee.entity.Department;
import com.greedy.newworker.employee.entity.Position;

public interface DepartmentRepository extends JpaRepository<Department, Long> {


	
}
