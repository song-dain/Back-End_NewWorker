package com.greedy.newworker.message.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	/* 받은 메시지함 조회 */
	@Query("select m from Message m where m.recipient = :recipient and m.recipientManagement.receiveMessageCategory = '받은 메시지함' and m.recipientManagement.receiveMessageDelete = 'N'")
	Page<Message> findReceiveMessages(Pageable page, @Param("recipient")EmployeeDto recipient);
	

	/* 받은 메시지 조회 */
	@Query("select m from Message m where m.recipient =:recipient and m.messageNo = :messageNo and m.recipientManagement.receiveMessageCategory = '받은 메시지함' and m.recipientManagement.receiveMessageDelete = 'N'")
	Optional<Message> findReceiveMessageById(@Param("messageNo")Long messageNo, @Param("recipient")EmployeeDto recipient);
	
	/* 보낸 메시지함 조회 */
	@Query("select m from Message m where m.sender =:sender and m.senderManagement.sendMessageDelete = 'N'")
	Page<Message> findSendMessages(Pageable page, @Param("sender")EmployeeDto sender);
	
	
	/* 보낸 메시지 조회 */
	@Query("select m from Message m where m.sender =:sender and m.messageNo =:messageNo and m.senderManagement.sendMessageDelete = 'N'")
	Optional<Message> findSendMessageById(@Param("messageNo")Long messageNo, @Param("sender")EmployeeDto sender);
	
	
	/* 중요 메시지함 조회 */
	@Query("select m from Message m where m.recipient =:recipient and m.recipientManagement.receiveMessageCategory = '중요 메시지함' and m.recipientManagement.receiveMessageDelete = 'N'")
	Page<Message> findImpoMessages(@Param("messageNo")Pageable pageable, @Param("recipient")EmployeeDto recipient);
	
	
	/* 중요 메시지 조회 */
	@Query(value = "select m from Message m where m.recipient =:recipient and m.messageNo =:messageNo m.recipientManagement.receiveMessageCategory = '중요 메시지함' and m.recipientManagement.receiveMessageDelete = 'N'", nativeQuery=true)
	Optional<Message> findImpoMessageById(@Param("messageNo")Long messageNo, @Param("recipient")EmployeeDto recipient);
	
	
	/* 휴지통 받은 메시지 조회 */
	@Query("select m from Message m where m.recipient =:recipient and m.recipientManagement.receiveMessageDelete = 'Y'")
	Page<Message> findBinReceiveMessages(Pageable page,  @Param("recipient")EmployeeDto recipient);
	
	
	/* 휴지통 보낸 메시지 조회 */
	@Query("select m from Message m where m.sender =:sender and m.senderManagement.sendMessageDelete = 'Y'")
	Page<Message> findBinSendMessages(Pageable page, @Param("sender")EmployeeDto sender);

}
