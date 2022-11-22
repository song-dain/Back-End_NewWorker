package com.greedy.newworker.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Department;
import com.greedy.newworker.employee.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	/* [message] 발신, 수신 사원 찾기 */
	Employee findByEmployeeNoAndEmployeeStatus(Employee employee, String status);



	Optional<Employee> findByEmployeeId(String employeeId);

	



	Optional<Employee> findByEmployeeNameAndEmployeeEmail(String employeeName, String employeeEmail);




	/* [message] 부서별 직원 리스트 */
	List<Employee> findByDep(Department dep);



	Employee findByEmployeeIdAndEmployeeNameAndEmployeeEmail(String employeeId, String employeeName,
			String employeeEmail);




}