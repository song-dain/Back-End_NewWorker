package com.greedy.newworker.approval.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.newworker.approval.entity.Approval;


public interface ApprovalRepository extends JpaRepository<Approval, Long> {
	


	// 결재 상신함 조회
	@Query("select a from Approval a where a.employee.employeeNo =:employeeNo")
	Page<Approval> findSendApproval(Pageable page, @Param("employeeNo")Long employeeNo);
	
	
	// 결재 수신함 조회
	@Query("select a from Approval a join a.appLines al where al.employee.employeeNo =:employeeNo and al.acceptActivate = 'Y' and a.appStatus != '회수'")
	Page<Approval> findReceiveApproval(Pageable page, @Param("employeeNo")Long approver);

	
	// 결재문서 상세 조회
	@Query("select a from Approval a join fetch a.appLines al where a.appNo =:appNo order by al.appLineNo")
	Optional<Approval> findByAppNo(@Param("appNo") Long appNo);

	

}








