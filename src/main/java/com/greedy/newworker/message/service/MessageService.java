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
	
	
	/* ????????? ?????? ?????? */
	public List<EmployeeDto> findRecipient(Long depNo, EmployeeDto employee) {
		
		Department dep = departmentRepository.findById(depNo)
				.orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ???????????????."));
		
		List<Employee> empList = employeeRepository.findByDepWithoutEmployee(dep.getDepNo(), modelMappler.map(employee, Employee.class).getEmployeeNo());

		return empList.stream().map(emp -> modelMappler.map(emp, EmployeeDto.class)).toList();
	}
	
	
	/* ????????? ????????? ????????? */
	public MessageDto newMessage(MessageDto messageDto, EmployeeDto senderDto) {

		Employee recipient = employeeRepository.findById(messageDto.getRecipient().getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ???????????????."));
		
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

	
	/* ?????? ???????????? */
	public Page<MessageDto> receiveMessages(int page, EmployeeDto recipientDto) {

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());

		Page<Message> receiveMessages = messageRepository.findReceiveMessages(pageable, modelMappler.map(recipientDto, Employee.class));

		return receiveMessages.map(message -> modelMappler.map(message, MessageDto.class));
	}

	
	/* ?????? ????????? ?????? */
	public Page<MessageDto> searchReceiveMessage(int page, String keyword, EmployeeDto recipient){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());
		
		Page<Message> findMessages = messageRepository
				.findByReceiveMessageContentContains(pageable, keyword, modelMappler.map(recipient, Employee.class));
	
		return findMessages.map(message -> modelMappler.map(message, MessageDto.class));
	}
	
	
	/* ?????? ????????? ?????? */
	public MessageDto messageRead(Long messageNo) {
		
		Message readMessage = messageRepository.findById(messageNo).orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));
		
		readMessage.setMessageStatus("read");
		
		messageRepository.save(readMessage);
		
		return modelMappler.map(readMessage, MessageDto.class);
	}

	
	/* ?????? ???????????? */
	public Page<MessageDto> sendMessages(int page, EmployeeDto senderDto) {

		Pageable pageable = PageRequest.of(page - 1, 11, Sort.by("messageNo").descending());

		Page<Message> sendMessages = messageRepository.findSendMessages(pageable, modelMappler.map(senderDto, Employee.class));

		return sendMessages.map(message -> modelMappler.map(message, MessageDto.class));
	}

	
	/* ?????? ????????? ?????? */
	public Page<MessageDto> searchSendMessage(int page, String keyword, EmployeeDto sender){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());
		
		Page<Message> findMessages = messageRepository.findBySendMessageContentContains(pageable, keyword, modelMappler.map(sender, Employee.class));
	
		return findMessages.map(message -> modelMappler.map(message, MessageDto.class));
	}

	
	/* ?????? ????????? ?????? ?????? */
	public MessageDto sendCancel(Long messageNo, EmployeeDto senderDto) {
		
		Message cancelMessage = messageRepository.findSendMessageById(messageNo, modelMappler.map(senderDto, Employee.class))
				.orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));
		
		cancelMessage.setMessageStatus("cancel");
		
		messageRepository.save(cancelMessage);
		
		RecipientManagement targetMessage = recipientManagementRepository.findById(messageNo)
				.orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));
		targetMessage.setReceiveMessageDelete("CALCEL");
		
		return modelMappler.map(cancelMessage, MessageDto.class);
	}
	
	
	/* ?????? ???????????? ??? */
	public Page<MessageDto> impoMessages(int page, EmployeeDto recipientDto) {

		Employee recipient = modelMappler.map(recipientDto, Employee.class);

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());

		Page<Message> impoMessages = messageRepository.findImpoMessages(pageable, recipient);

		return impoMessages.map(message -> modelMappler.map(message, MessageDto.class));
	}

	
	/* ?????? ????????? ?????? ??? */
	public MessageDto selectImpoMessage(Long messageNo, EmployeeDto recipientDto) {

		Employee recipient = modelMappler.map(recipientDto, Employee.class);

		Message message = messageRepository.findImpoMessageById(messageNo, recipient)
				.orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));

		message.setMessageStatus("read");

		messageRepository.save(message);

		return modelMappler.map(message, MessageDto.class);
	}
	
	
	/* ?????? ????????? ?????? */
	public Page<MessageDto> searchImpoMessage(int page, String keyword, EmployeeDto recipient){
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());
		
		Page<Message> findMessages = messageRepository
				.findByImpoMessageContentContains(pageable, keyword, modelMappler.map(recipient, Employee.class));
	
		return findMessages.map(message -> modelMappler.map(message, MessageDto.class));
	}

	
	/* ????????? ?????? ????????? ??? */
	public Page<MessageDto> binReceiveMessages(int page, EmployeeDto recipientDto) {

		Employee recipient = modelMappler.map(recipientDto, Employee.class);

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());

		Page<Message> binReceiveMessages = messageRepository.findBinReceiveMessages(pageable, recipient);

		return binReceiveMessages.map(message -> modelMappler.map(message, MessageDto.class));
	}

	
	/* ????????? ?????? ????????? ??? */
	public Page<MessageDto> binSendMessages(int page, EmployeeDto senderDto) {

		Employee sender = modelMappler.map(senderDto, Employee.class);

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());

		Page<Message> binSendMessages = messageRepository.findBinSendMessages(pageable, sender);

		return binSendMessages.map(message -> modelMappler.map(message, MessageDto.class));
	}
	
	
	/* ?????? ?????????(?????? ????????????, ?????? ?????????, ????????? ?????? ?????????) ?????? */
	public RecipientManagementDto receiveMessageManagement(RecipientManagementDto messageRequest) {

		RecipientManagement targetMessage = recipientManagementRepository.findById(messageRequest.getMessage().getMessageNo())
				.orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));

		if (messageRequest.getReceiveMessageCategory() != null) {
			targetMessage.setReceiveMessageCategory(messageRequest.getReceiveMessageCategory());
		}

		if (messageRequest.getReceiveMessageDelete() != null) {
			targetMessage.setReceiveMessageDelete(messageRequest.getReceiveMessageDelete());
		}

		recipientManagementRepository.save(targetMessage);

		return modelMappler.map(targetMessage, RecipientManagementDto.class);
	}


	/* ?????? ?????????(?????? ????????????, ????????? ?????? ?????????) ?????? */
	public SenderManagementDto sendMessageManagement(SenderManagementDto messageRequest) {
		
		SenderManagement targetMessage = senderManagementRepository.findById(messageRequest.getMessage().getMessageNo())
				.orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));

		targetMessage.setSendMessageDelete(messageRequest.getSendMessageDelete());
		
		senderManagementRepository.save(targetMessage);
		
		
		return modelMappler.map(targetMessage, SenderManagementDto.class);
	}


	/* [main] ??? ?????? ????????? */
	public MessageDto unreadMessage(EmployeeDto emp) {
		
		Long unreadMessage = messageRepository.countUnreadMessage(modelMappler.map(emp, Employee.class));
		
		MessageDto count = new MessageDto();
		count.setUnreadMessage(unreadMessage);
		
		return count;
	}




	
}
