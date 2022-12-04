package com.greedy.newworker.rest.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.rest.entity.Rest;

import io.lettuce.core.dynamic.annotation.Param;

public interface RestRepository extends JpaRepository<Rest, Long>{


	/* 조회*/
	@EntityGraph(attributePaths= {"restNo"})
	Page<Rest> findAll(Pageable pageable);

	/* 휴가 인가 페이지 */
	@EntityGraph(attributePaths= {"restNo"})
	Page<Rest> findByRestOk(Pageable pageable, String restOk);
	
	/* [calendar] 사원별 승인 연차 정보 */
	List<Rest> findByEmployeeNoAndRestOk(Employee employeeNo, String restOk);

	/* [메인] 사원별 오늘 연차 정보 */
	@Query(value="SELECT * FROM TBL_REST "
			+ "WHERE EMPLOYEE_NO =:employeeNo AND REST_OK = 'Y' "
			+ "AND TO_CHAR(SYSDATE, 'yy/MM/dd') >= TO_CHAR(REST_FDATE, 'yy/MM/dd') "
			+ "AND TO_CHAR(SYSDATE, 'yy/MM/dd') <= TO_CHAR(REST_LDATE, 'yy/MM/dd')", nativeQuery=true)
	List<Rest> findByEmployeeNoAndRestOkAndRestFdate(@Param("employeeNo")Long employeeNo);
	
}
