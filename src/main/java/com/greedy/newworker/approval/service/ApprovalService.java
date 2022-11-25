package com.greedy.newworker.approval.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.newworker.approval.dto.AppLineDto;
import com.greedy.newworker.approval.dto.ApprovalDto;
import com.greedy.newworker.approval.entity.AppLine;
import com.greedy.newworker.approval.entity.Approval;
import com.greedy.newworker.approval.repository.ApprovalRepository;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApprovalService {
	
	private final ModelMapper modelMapper;
	private final ApprovalRepository approvalRepository;
	private final EmployeeRepository employeeRepository;
	
	private ApprovalService(ModelMapper modelMapper, ApprovalRepository approvalRepository, EmployeeRepository employeeRepository) {
		this.modelMapper = modelMapper;
		this.approvalRepository = approvalRepository;
		this.employeeRepository = employeeRepository;
	}

	public Page<ApprovalDto> sendApproval(int page, Long employeeNo) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("appNo").descending());
		
		Page<Approval> sendApproval = approvalRepository.findSendApproval(pageable, employeeNo);
		Page<ApprovalDto> sendApprovalList = sendApproval.map(approval -> modelMapper.map(approval, ApprovalDto.class));
		
		log.info("sendApprovalList : {}", sendApprovalList);
		return sendApprovalList;
	}

	public ApprovalDto appRegist(ApprovalDto approvalDto, EmployeeDto drafterDto) {
		
		AppLineDto appLineDto = new AppLineDto();
		
		// 결재문서 
		Approval approval = new Approval();
		approval.setAppDocNo(approvalDto.getAppDocNo());
		approval.setAppTitle(approvalDto.getAppTitle());
		approval.setAppContent(approvalDto.getAppContent());
		approval.setAppEndDate(approvalDto.getAppEndDate());
		approval.setEmployee(modelMapper.map(drafterDto, Employee.class));
		approvalRepository.save(approval);
		
		// 결재자 탐색
		Employee approver = employeeRepository.findById(appLineDto.getEmployee().getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다."));
		
		// 결재선
		AppLine appLine = new AppLine();
		appLine.setAppLineTurn(appLineDto.getAppLineTurn());
		appLine.setEmployee(modelMapper.map(approver, Employee.class));
		appLine.setApp(modelMapper.map(approval, Approval.class));
		
		
		
		
		return null;
	}
	
	
///	@PostMapping("/regist")
//	public ApprovalDto appRegist(ApprovalDto approvalDto, EmployeeDto employeeDto) {
//	
//	Employee approver = approvalService.findById(approvalDto.getEmployee().getEmployeeNo())
//			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다."));
//	
//	
//}

}
