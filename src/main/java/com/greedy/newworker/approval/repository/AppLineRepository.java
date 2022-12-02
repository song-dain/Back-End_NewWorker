package com.greedy.newworker.approval.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.greedy.newworker.approval.entity.AppLine;
import com.greedy.newworker.employee.entity.Department;

public interface AppLineRepository extends JpaRepository<AppLine, Long> {
	
//	@Query("select a from Approval a join a.appLines al where al.acceptActivate = 'Y' and al.acceptStatus is null")
	Optional<AppLine> findById(Long appLineNo);

	Optional<AppLine> findByApprovalNoAndAppLineTurn(Long approvalNo, Long appLineTurn);

	//appLineNo과 approvalNo을 조회
	@Query("select a from Approval a join a.appLines al where al.acceptActivate = 'Y' and al.acceptStatus is null")
	Optional<AppLine> findByAppLineNoAndApprovalNo(Long appLineNo, Long approvalNo);
}

