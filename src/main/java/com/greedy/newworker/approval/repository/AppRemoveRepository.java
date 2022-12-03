package com.greedy.newworker.approval.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.newworker.approval.entity.Approval;

public interface AppRemoveRepository extends JpaRepository<Approval, Long> {

	/* 결재 삭제 */
	Approval findByAppNo(Long appNo);
	

}
