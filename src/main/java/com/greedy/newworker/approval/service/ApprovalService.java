package com.greedy.newworker.approval.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.newworker.approval.dto.ApprovalDto;
import com.greedy.newworker.approval.entity.Approval;
import com.greedy.newworker.approval.repository.ApprovalRepository;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.message.dto.MessageDto;

@Service
public class ApprovalService {
	
	private final ModelMapper modelMapper;
	private final ApprovalRepository approvalRepository;
	
	private ApprovalService(ModelMapper modelMapper, ApprovalRepository approvalRepository) {
		this.modelMapper = modelMapper;
		this.approvalRepository = approvalRepository;
	}

	public Page<ApprovalDto> sendApproval(int page, Long employeeNo) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("appNo").descending());
		
		Page<Approval> sendApproval = approvalRepository.findSendApproval(pageable, employeeNo);
		Page<ApprovalDto> sendApprovalList = sendApproval.map(approval -> modelMapper.map(approval, ApprovalDto.class));
		return sendApprovalList;
	}
	
	
//	
//	/* 보낸 메시지함 완!!!!!!! */
//	public Page<MessageDto> sendMessages(int page, EmployeeDto senderDto) {
//
//		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("messageNo").descending());
//
//		Page<Message> sendMessages = messageRepository.findSendMessages(pageable, modelMappler.map(senderDto, Employee.class));
//		Page<MessageDto> sendMessageBox = sendMessages.map(message -> modelMappler.map(message, MessageDto.class));
//
//		return sendMessageBox;
//	}

	

}
