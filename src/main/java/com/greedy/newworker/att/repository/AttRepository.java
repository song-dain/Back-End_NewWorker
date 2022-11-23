package com.greedy.newworker.att.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.newworker.att.entity.Att;
import com.greedy.newworker.employee.dto.EmployeeDto;

public interface AttRepository extends JpaRepository<Att, Long>{

	@Query("SELECT a " +
		             "FROM Att a " +
			        "WHERE a.attNo = :attNo " +
		             "AND a.employee.employeeNo = :employeeNo")
	Optional<Att> findByAttNoAndEmployeeNo(@Param("attNo") Long attNo, @Param("employeeNo") Long employeeNo);

	@EntityGraph(attributePaths= {"employee"})
	@Query("SELECT a " +
            "FROM Att a " +
	        "WHERE a.attMonth = :attMonth " +
            "AND a.employee.employeeNo = :employeeNo")
	Page<Att> findByAttMonthAndEmployeeNo(Pageable pageable, @Param("attMonth") String attMonth, @Param("employeeNo") Long employeeNo);

}
