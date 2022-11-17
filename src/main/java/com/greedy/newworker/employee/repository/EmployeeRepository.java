package com.greedy.newworker.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.newworker.employee.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	
	/* memberId로 조회 */
	Optional<Employee> findByEmployeeId(String employeeId);

}
