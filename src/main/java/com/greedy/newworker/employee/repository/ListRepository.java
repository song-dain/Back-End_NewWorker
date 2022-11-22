package com.greedy.newworker.employee.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.newworker.employee.entity.Employee;

public interface ListRepository extends JpaRepository<Employee, Long> {

	/* 회원조회(전체정보) */
	@EntityGraph(attributePaths= {"employeeNo"})
	Page<Employee> findAll(Pageable pageable);
	
//	/* 상세조회(관리자)*/
//	@Query("SELECT e " +
//			 "FROM Employee e" +
//			"WHERE e.employeeNo = :employeeNo "
//			 )
//	Optional<Employee> findByEmployeeNo(@Param("employeeNo") Long employeeNo);

	Optional<Employee> findByEmployeeNo(Long employeeNo);
}
