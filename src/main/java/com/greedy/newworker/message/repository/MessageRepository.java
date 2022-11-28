package com.greedy.newworker.message.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	/* 받은 메시지함 조회 완!!!!!! */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.recipient = :recipient and m.recipientManagement.receiveMessageCategory = 'receiveMessageBox' and m.recipientManagement.receiveMessageDelete = 'N'")
	Page<Message> findReceiveMessages(Pageable pageable, @Param("recipient")Employee recipient);
	

	/* 받은 메시지 조회 완!!!!!! */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.recipient =:recipient and m.messageNo = :messageNo and m.recipientManagement.receiveMessageDelete = 'N'")
	Optional<Message> findReceiveMessageById(@Param("messageNo")Long messageNo, @Param("recipient")Employee recipient);
	
	/* 보낸 메시지함 조회 완!!!!!!!! */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.sender =:sender and m.senderManagement.sendMessageDelete = 'N'")
	Page<Message> findSendMessages(Pageable page, @Param("sender")Employee sender);
	
	
	/* 보낸 메시지 조회 완!!!!!!!!! */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.sender =:sender and m.messageNo =:messageNo and m.senderManagement.sendMessageDelete = 'N'")
	Optional<Message> findSendMessageById(@Param("messageNo")Long messageNo, @Param("sender")Employee sender);
	
	
	/* 중요 메시지함 조회 완!!!!!! */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.recipient =:recipient and m.recipientManagement.receiveMessageCategory = 'impoMessageBox' and m.recipientManagement.receiveMessageDelete = 'N'")
	Page<Message> findImpoMessages(@Param("messageNo")Pageable pageable, @Param("recipient")Employee recipient);
	
	
	/* 중요 메시지 조회 완!!!!!!!! */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.recipient =:recipient and m.messageNo = :messageNo and m.recipientManagement.receiveMessageCategory = 'impoMessageBox' and m.recipientManagement.receiveMessageDelete = 'N'")
	Optional<Message> findImpoMessageById(@Param("messageNo")Long messageNo, @Param("recipient")Employee recipient);
	
	
	/* 휴지통 받은 메시지 조회 */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.recipient =:recipient and m.recipientManagement.receiveMessageDelete = 'Y'")
	Page<Message> findBinReceiveMessages(Pageable page,  @Param("recipient")Employee recipient);
	
	
	/* 휴지통 보낸 메시지 조회 */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.sender =:sender and m.senderManagement.sendMessageDelete = 'Y'")
	Page<Message> findBinSendMessages(Pageable page, @Param("sender")Employee sender);


	/* 받은 메시지 검색 */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.recipient =:recipient and m.recipientManagement.receiveMessageCategory = 'receiveMessageBox' and m.recipientManagement.receiveMessageDelete = 'N' and m.messageContent like %:keyword%")
	Page<Message> findByReceiveMessageContentContains(Pageable page, @Param("keyword")String keyword, @Param("recipient")Employee recipient);

	
	/* 보낸 메시지 검색 */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.sender =:sender and m.senderManagement.sendMessageDelete = 'N' and m.messageContent like %:keyword%")
	Page<Message> findBySendMessageContentContains(Pageable page, @Param("keyword")String keyword, @Param("sender")Employee sender);
	
	/* 중요 메시지 검색 */
	@EntityGraph(attributePaths= {"recipient", "sender"})
	@Query("select m from Message m where m.recipient =:recipient and m.recipientManagement.receiveMessageCategory = 'impoMessageBox' and m.recipientManagement.receiveMessageDelete = 'N' and m.messageContent like %:keyword%")
	Page<Message> findByImpoMessageContentContains(Pageable page, @Param("keyword")String keyword, @Param("recipient")Employee recipient);

	/* 안 읽은 메시지 개수 */
	@Query("select count(messageStatus) from Message m where messageStatus = 'send' and recipient =:employee")
	Long countUnreadMessage(@Param("employee")Employee emp);
	
}
