package com.greedy.newworker.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.newworker.employee.entity.Department;
import com.greedy.newworker.employee.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	/* [message] 발신, 수신 사원 찾기 */
	Employee findByEmployeeNoAndEmployeeStatus(Employee employee, String status);



	Optional<Employee> findByEmployeeId(String employeeId);

	



	Optional<Employee> findByEmployeeNameAndEmployeeEmail(String employeeName, String employeeEmail);




	/* [message] 부서별 직원 리스트 */
	@Query("select e from Employee e where e.dep.depNo =:depNo and e.employeeNo !=:empNo")
	List<Employee> findByDepWithoutEmployee(@Param("depNo")Long depNo, @Param("empNo")Long empNo);



	Employee findByEmployeeIdAndEmployeeNameAndEmployeeEmail(String employeeId, String employeeName,
			String employeeEmail);




}