package com.greedy.newworker.message.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Department;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.DepartmentRepository;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MessageService {

	private final MessageRepository messageRepository;
	private final RecipientManagementRepository recipientManagementRepository;
	private final SenderManagementRepository senderManagementRepository;
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final ModelMapper modelMappler;

	public MessageService(MessageRepository messageRepository,
			RecipientManagementRepository recipientManagementRepository,
			SenderManagementRepository senderManagementRepository, 
			EmployeeRepository employeeRepository,
			DepartmentRepository departmentRepository,
			ModelMapper modelMappler) {

		this.messageRepository = messageRepository;
		this.recipientManagementRepository = recipientManagementRepository;
		this.senderManagementRepository = senderManagementRepository;
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.modelMappler = modelMappler;
	}
	
	
	/* 부서별 직원 조회 */
	public List<EmployeeDto> findRecipient(Long depNo, EmployeeDto employee) {
		
		Department dep = departmentRepository.findById(depNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부서입니다."));
		
		List<Employee> empList = employeeRepository.findByDepWithoutEmployee(dep.getDepNo(), modelMappler.map(employee, Employee.class).getEmployeeNo());

		return empList.stream().map(emp -> modelMappler.map(emp, EmployeeDto.class)).toList();
	}

	
	/* 새로운 메시지 보내기 */
	public MessageDto newMessage(MessageDto messageDto, EmployeeDto senderDto) {

		/* 수신자 정보 */
		Employee recipient = employeeRepository.findById(messageDto.getRecipient().getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다."));
		
		Message newMessage = new Message();
		newMessage.setRecipient(recipient);
		newMessage.setSender(modelMappler.map(senderDto, Employee.class));
		newMessage.setMessageContent(messageDto.getMessageContent());
		messageRepository.save(newMessage);
		
		RecipientManagement newRmanagement = new RecipientManagement(newMessage.getMessageNo());
		recipientManagementRepository.save(newRmanagement);
		
		SenderManagement newSmanagement = new SenderManagement(newMessage.getMessageNo());
		senderManagementRepository.save(newSmanagement);

		return modelMappler.map(newMessage, MessageDto.class);
	}

	
	/* 받은 메시지함 */
	public Page<MessageDto> receiveMessages(int page, EmployeeDto recipientDto) {

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());

		Page<Message> receiveMessages = messageRepository.findReceiveMessages(pageable, modelMappler.map(recipientDto, Employee.class));
		Page<MessageDto> receiveMessageBox = receiveMessages
				.map(message -> modelMappler.map(message, MessageDto.class));

		return receiveMessageBox;
	}

	/* 받은 메시지 조회 */
	public MessageDto selectReceiveMessage(Long messageNo, EmployeeDto recipientDto) {

		Message message = messageRepository.findReceiveMessageById(messageNo, modelMappler.map(recipientDto, Employee.class))
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메시지입니다."));

		message.setMessageStatus("read");

		return modelMappler.map(message, MessageDto.class);
	}

	
	/* 받은 메시지 검색 */
	public Page<MessageDto> searchReceiveMessage(int page, String keyword, EmployeeDto recipient){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());
		
		Page<Message> findMessages = messageRepository.findByReceiveMessageContentContains(pageable, keyword, modelMappler.map(recipient, Employee.class));
		Page<MessageDto> findMessageList = findMessages
				.map(message -> modelMappler.map(message, MessageDto.class));
	
		return findMessageList;
	}


	/* 보낸 메시지함 */
	public Page<MessageDto> sendMessages(int page, EmployeeDto senderDto) {

		Pageable pageable = PageRequest.of(page - 1, 11, Sort.by("messageNo").descending());

		Page<Message> sendMessages = messageRepository.findSendMessages(pageable, modelMappler.map(senderDto, Employee.class));
		Page<MessageDto> sendMessageBox = sendMessages.map(message -> modelMappler.map(message, MessageDto.class));

		return sendMessageBox;
	}

	
	/* 보낸 메시지 조회 */
	public MessageDto selectSendMessage(Long messageNo, EmployeeDto senderDto) {

		Message message = messageRepository.findSendMessageById(messageNo, modelMappler.map(senderDto, Employee.class))
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메시지입니다."));

		return modelMappler.map(message, MessageDto.class);
	}
	
	
	/* 보낸 메시지 검색 */
	public Page<MessageDto> searchSendMessage(int page, String keyword, EmployeeDto sender){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());
		
		Page<Message> findMessages = messageRepository.findBySendMessageContentContains(pageable, keyword, modelMappler.map(sender, Employee.class));
		Page<MessageDto> findMessageList = findMessages
				.map(message -> modelMappler.map(message, MessageDto.class));
	
		return findMessageList;
	}

	/* 보낸 메시지 전송 취소 */
	public MessageDto sendCancel(Long messageNo, EmployeeDto senderDto) {
		
		log.info("[ms] messageNo : {}", messageNo );
		
		Message cancelMessage = messageRepository.findSendMessageById(messageNo, modelMappler.map(senderDto, Employee.class))
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메시지입니다."));
		
		cancelMessage.setMessageStatus("cancel");
		
		messageRepository.save(cancelMessage);
		
		RecipientManagement targetMessage = recipientManagementRepository.findById(messageNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메시지입니다."));
		targetMessage.setReceiveMessageDelete("CALCEL");
		
		return modelMappler.map(cancelMessage, MessageDto.class);
	}
	
	
	/* 중요 메시지함 완 */
	public Page<MessageDto> impoMessages(int page, EmployeeDto recipientDto) {

		Employee recipient = modelMappler.map(recipientDto, Employee.class);

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());

		Page<Message> impoMessages = messageRepository.findImpoMessages(pageable, recipient);
		Page<MessageDto> impoMessageBox = impoMessages.map(message -> modelMappler.map(message, MessageDto.class));

		return impoMessageBox;
	}

	/* 중요 메시지 조회 완 */
	public MessageDto selectImpoMessage(Long messageNo, EmployeeDto recipientDto) {

		Employee recipient = modelMappler.map(recipientDto, Employee.class);

		Message message = messageRepository.findImpoMessageById(messageNo, recipient)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메시지입니다."));

		message.setMessageStatus("read");

		messageRepository.save(message);

		return modelMappler.map(message, MessageDto.class);
	}
	
	
	/* 중요 메시지 검색 */
	public Page<MessageDto> searchImpoMessage(int page, String keyword, EmployeeDto recipient){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());
		
		Page<Message> findMessages = messageRepository.findByImpoMessageContentContains(pageable, keyword, modelMappler.map(recipient, Employee.class));
		Page<MessageDto> findMessageList = findMessages
				.map(message -> modelMappler.map(message, MessageDto.class));
	
		return findMessageList;
	}

	
	/* 휴지통 받은 메시지 완 */
	public Page<MessageDto> binReceiveMessages(int page, EmployeeDto recipientDto) {

		Employee recipient = modelMappler.map(recipientDto, Employee.class);

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());

		Page<Message> binReceiveMessages = messageRepository.findBinReceiveMessages(pageable, recipient);
		Page<MessageDto> binReceiveMessageBox = binReceiveMessages
				.map(message -> modelMappler.map(message, MessageDto.class));

		return binReceiveMessageBox;
	}

	
	/* 휴지통 보낸 메시지 완 */
	public Page<MessageDto> binSendMessages(int page, EmployeeDto senderDto) {

		Employee sender = modelMappler.map(senderDto, Employee.class);

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());

		Page<Message> binSendMessages = messageRepository.findBinSendMessages(pageable, sender);
		Page<MessageDto> binSendMessageBox = binSendMessages
				.map(message -> modelMappler.map(message, MessageDto.class));

		return binSendMessageBox;
	}
	
	
	/* 받은 메시지(받은 메시지함, 중요 메시지, 휴지통 받은 메시지) 관리 */
	public RecipientManagementDto receiveMessageManagement(RecipientManagementDto messageRequest) {

		log.info("[MessageService] messageRequest : {}", messageRequest);

		RecipientManagement targetMessage = recipientManagementRepository.findById(messageRequest.getMessage().getMessageNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메시지입니다."));

		if (messageRequest.getReceiveMessageCategory() != null) {
			targetMessage.setReceiveMessageCategory(messageRequest.getReceiveMessageCategory());
		}

		if (messageRequest.getReceiveMessageDelete() != null) {
			targetMessage.setReceiveMessageDelete(messageRequest.getReceiveMessageDelete());
		}

		recipientManagementRepository.save(targetMessage);

		return modelMappler.map(targetMessage, RecipientManagementDto.class);
	}


	/* 보낸 메시지(받은 메시지함, 휴지통 보낸 메시지) 관리 */
	public SenderManagementDto sendMessageManagement(SenderManagementDto messageRequest) {

		log.info("[MessageService] messageRequest : {}", messageRequest);
		
		SenderManagement targetMessage = senderManagementRepository.findById(messageRequest.getMessage().getMessageNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메시지입니다."));

		targetMessage.setSendMessageDelete(messageRequest.getSendMessageDelete());
		
		senderManagementRepository.save(targetMessage);
		
		
		return modelMappler.map(targetMessage, SenderManagementDto.class);
	}


	/* [main] 안 읽은 메시지 */
	public MessageDto unreadMessage(EmployeeDto emp) {
		
		Long unreadMessage = messageRepository.countUnreadMessage(modelMappler.map(emp, Employee.class));
		
		MessageDto count = new MessageDto();
		count.setUnreadMessage(unreadMessage);
		
		return count;
	}


	
	
}
