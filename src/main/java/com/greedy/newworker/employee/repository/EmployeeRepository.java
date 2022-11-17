package com.greedy.newworker.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.newworker.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	/* 발신, 수신 사원 찾기 */
	Employee findByEmployeeNoAndEmployeeStatus(Employee employee, String status);
}
