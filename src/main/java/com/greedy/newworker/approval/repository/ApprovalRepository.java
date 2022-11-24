package com.greedy.newworker.approval.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.newworker.approval.entity.Approval;
import com.greedy.newworker.employee.entity.Employee;


public interface ApprovalRepository extends JpaRepository<Approval, Long> {


//	@Query("select a from Approval a where a.employee.employeeNo =:employeeNo")
//	Page<Approval> findSendApproval(Pageable page, @Param("employeeNo")Long employeeNo drafter);
	
	@Query("select a from Approval a where a.employee.employeeNo =:employeeNo")
	Page<Approval> findSendApproval(Pageable pageable, @Param("employeeNo")Long employeeNo);
}


//	/* 보낸 메시지함 조회 완!!!!!!!! */
//	@EntityGraph(attributePaths= {"recipient", "sender"})
//	@Query("select m from Message m where m.sender =:sender and m.senderManagement.sendMessageDelete = 'N'")
//	Page<Message> findSendMessages(Pageable page, @Param("sender")Employee sender);
	





