package com.greedy.newworker.message.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.EmployeeRepository;
import com.greedy.newworker.message.dto.MessageDto;
import com.greedy.newworker.message.dto.RecipientManagementDto;
import com.greedy.newworker.message.dto.SenderManagementDto;
import com.greedy.newworker.message.entity.Message;
import com.greedy.newworker.message.entity.RecipientManagement;
import com.greedy.newworker.message.entity.SenderManagement;
import com.greedy.newworker.message.repository.MessageRepository;
import com.greedy.newworker.message.repository.RecipientManagementRepository;
import com.greedy.newworker.message.repository.SenderManagementRepository;

@Service
public class MessageService {
	
	private final MessageRepository messageRepository;
	private final RecipientManagementRepository recipientManagementRepository;
	private final SenderManagementRepository senderManagementRepository;
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMappler;
	
	public MessageService(MessageRepository messageRepository, RecipientManagementRepository recipientManagementRepository, SenderManagementRepository senderManagementRepository, 
			EmployeeRepository employeeRepository, ModelMapper modelMappler) {
		
		this.messageRepository = messageRepository;
		this.recipientManagementRepository = recipientManagementRepository;
		this.senderManagementRepository = senderManagementRepository;
		this.employeeRepository = employeeRepository;
		this.modelMappler = modelMappler;
	}
	
	
	/* 새로운 메시지 보내기 */
	@Transactional
	public MessageDto newMessage(MessageDto messageDto) {
		
		/* 수신자, 발신자 정보 */
		Employee recipient = employeeRepository
				.findByEmployeeNoAndEmployeeStatus(modelMappler.map(messageDto.getRecipient(), Employee.class), "Y");

		Employee sender = employeeRepository
				.findByEmployeeNoAndEmployeeStatus(modelMappler.map(messageDto.getRecipient(), Employee.class), "Y");
		
		Message newMessage = new Message();
		newMessage.setRecipient(recipient);
		newMessage.setSender(sender);
		newMessage.setMessageContent(messageDto.getMessageContent());
		
		messageRepository.save(newMessage);
		
		return messageDto;
	}
	
	
	/* 받은 메시지함 */
	public Page<MessageDto> receiveMessages(int page, EmployeeDto recipient){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productCode").descending());
		
		Page<Message> receiveMessages = messageRepository.findReceiveMessages(pageable, recipient);
		Page<MessageDto> receiveMessageBox = receiveMessages.map(message -> modelMappler.map(message, MessageDto.class));
		
		return receiveMessageBox;
	}
	
	
	/* 받은 메시지 조회 */
	public MessageDto selectReceiveMessage(Long messageNo, EmployeeDto recipient){
		
		return  modelMappler.map(messageRepository.findReceiveMessageById(messageNo, recipient), MessageDto.class);
	}
	
	
	/* 받은 메시지 중요 메시지함 이동 */
	public RecipientManagementDto receiveMessageToImpoMessage(Long messageNo) {
		
		RecipientManagement targetMessage = recipientManagementRepository.findById(messageNo).orElseThrow();
		targetMessage.setReceiveMessageCategory("중요 메시지함");
		
		recipientManagementRepository.save(targetMessage);
		
		RecipientManagementDto statusModify = modelMappler.map(targetMessage, RecipientManagementDto.class);
		
		return statusModify;
	}
	
	
	/* 보낸 메시지함 */
	public Page<MessageDto> sendMessages(int page, EmployeeDto sender){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productCode").descending());
		
		Page<Message> sendMessages = messageRepository.findSendMessages(pageable, sender);
		Page<MessageDto> sendMessageBox = sendMessages.map(message -> modelMappler.map(message, MessageDto.class));
		
		return sendMessageBox;	
	}
	
	
	/* 보낸 메시지 조회 */
	public MessageDto selectSendMessage(Long messageNo, EmployeeDto recipient){
		
		return  modelMappler.map(messageRepository.findSendMessageById(messageNo, recipient), MessageDto.class);
	}
	
	
	/* 보낸 메시지 휴지통 이동 */
	public SenderManagementDto sendMessageToBinMessage(Long messageNo) {
		
		SenderManagement targetMessage = senderManagementRepository.findById(messageNo).orElseThrow();
		targetMessage.setSendMessageDelete("Y");
		
		senderManagementRepository.save(targetMessage);
		
		SenderManagementDto statusModify = modelMappler.map(targetMessage, SenderManagementDto.class);
		
		return statusModify;
	}
	
	
	/* 중요 메시지함 */
	public Page<MessageDto> impoMessages(int page, EmployeeDto recipient){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productCode").descending());
		
		Page<Message> impoMessages = messageRepository.findImpoMessages(pageable, recipient);
		Page<MessageDto> impoMessageBox = impoMessages.map(message -> modelMappler.map(message, MessageDto.class));
		
		return impoMessageBox;
	}
	
	
	/* 중요 메시지 조회 */
	public MessageDto selectImpoMessage(Long messageNo, EmployeeDto recipient){
		
		return  modelMappler.map(messageRepository.findImpoMessageById(messageNo, recipient), MessageDto.class);
	}
	
	
	/* 중요 메시지 받은 메시지 이동 */
	public RecipientManagementDto impoMessageToreceiveMessage(Long messageNo) {
		
		RecipientManagement targetMessage = recipientManagementRepository.findById(messageNo).orElseThrow();
		targetMessage.setReceiveMessageCategory("받은 메시지함");
		
		recipientManagementRepository.save(targetMessage);
		
		RecipientManagementDto statusModify = modelMappler.map(targetMessage, RecipientManagementDto.class);
		
		return statusModify;
	}
	
	
	/* 받은 메시지, 중요 메시지 휴지통 이동 */
	public RecipientManagementDto receiveMessageToBinMessage(Long messageNo) {
		
		RecipientManagement targetMessage = recipientManagementRepository.findById(messageNo).orElseThrow();
		targetMessage.setReceiveMessageDelete("Y");
		
		recipientManagementRepository.save(targetMessage);
		
		RecipientManagementDto statusModify = modelMappler.map(targetMessage, RecipientManagementDto.class);
		
		return statusModify;
	}
	
	
	/* 휴지통 받은 메시지 */
	public Page<MessageDto> binReceiveMessages(int page, EmployeeDto recipient){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productCode").descending());
		
		Page<Message> binReceiveMessages = messageRepository.findBinReceiveMessages(pageable, recipient);
		Page<MessageDto> binReceiveMessageBox = binReceiveMessages.map(message -> modelMappler.map(message, MessageDto.class));
		
		return binReceiveMessageBox;
	}
	
	
	/* 휴지통 받은 메시지 영구 삭제 */
	
	
	/* 휴지통 받은 메시지 복구 */
	public RecipientManagementDto binMessageToreceiveMessage(Long messageNo) {
		
		RecipientManagement targetMessage = recipientManagementRepository.findById(messageNo).orElseThrow();
		targetMessage.setReceiveMessageDelete("N");
		
		recipientManagementRepository.save(targetMessage);
		
		RecipientManagementDto statusModify = modelMappler.map(targetMessage, RecipientManagementDto.class);
		
		return statusModify;
	}
	
	
	/* 휴지통 보낸 메시지 */
	public Page<MessageDto> binSendMessages(int page, EmployeeDto recipient){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productCode").descending());
		
		Page<Message> binSendMessages = messageRepository.findBinSendMessages(pageable, recipient);
		Page<MessageDto> binSendMessageBox = binSendMessages.map(message -> modelMappler.map(message, MessageDto.class));
		
		return binSendMessageBox;
	}
	
	
	/* 휴지통 보낸 메시지 영구 삭제 */
	
	
	/* 휴지통 보낸 메시지 복구 */
	public SenderManagementDto binMessageToSendMessage(Long messageNo) {
		
		SenderManagement targetMessage = senderManagementRepository.findById(messageNo).orElseThrow();
		targetMessage.setSendMessageDelete("N");
		
		senderManagementRepository.save(targetMessage);
		
		SenderManagementDto statusModify = modelMappler.map(targetMessage, SenderManagementDto.class);
		
		return statusModify;
	}
	

	

}
