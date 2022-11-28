package com.greedy.newworker.approval.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.newworker.approval.dto.AppLineDto;
import com.greedy.newworker.approval.dto.ApprovalDto;
import com.greedy.newworker.approval.entity.AppLine;
import com.greedy.newworker.approval.entity.Approval;
import com.greedy.newworker.approval.repository.AppLineRepository;
import com.greedy.newworker.approval.repository.ApprovalRepository;
import com.greedy.newworker.apttach.entity.Apttach;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.EmployeeRepository;
import com.greedy.newworker.util.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApprovalService {
	
	private final ModelMapper modelMapper;
	private final ApprovalRepository approvalRepository;
	private final AppLineRepository appLineRepository;
	private final EmployeeRepository employeeRepository;
	
	@Value("${file.file-dir}" + "/approvalfiles")
	private String FILE_DIR;
	@Value("${file.file-url}" + "/approvalfiles/")
	private String FILE_URL;
	
	public ApprovalService(ModelMapper modelMapper, ApprovalRepository approvalRepository, EmployeeRepository employeeRepository, AppLineRepository appLineRepository) {
		this.modelMapper = modelMapper;
		this.approvalRepository = approvalRepository;
		this.employeeRepository = employeeRepository;
		this.appLineRepository = appLineRepository;
	}

	
	
	public Page<ApprovalDto> sendApproval(int page, Long employeeNo) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("appNo").descending());
		
		Page<Approval> sendApproval = approvalRepository.findSendApproval(pageable, employeeNo);
		Page<ApprovalDto> sendApprovalList = sendApproval.map(approval -> modelMapper.map(approval, ApprovalDto.class));
		
		log.info("sendApprovalList : {}", sendApprovalList);
		return sendApprovalList;
	}

	
	
	// 전자결재 등록 
	@Transactional
	public ApprovalDto appRegist(ApprovalDto approvalDto) {

		log.info("[ApprovalService] 결재 등록 시작 ==========================");
		log.info("[ApprovalService] approvalDto : {}", approvalDto);
		
		
		
		log.info("[ApprovalService] Debug 1");
		
		
		
		// 첨부파일
		String fileName = UUID.randomUUID().toString().replace("-", ""); // 랜덤 유효 아이디 생성
		List<String> replaceFilesName = null;

		try {
			replaceFilesName = FileUploadUtils.saveFiles(FILE_DIR, fileName, approvalDto.getApprovalFiles());
		// 배열로 들어가기 때문에 반복문으로 SIZE 만큼 삽입
		for(int i = 0; i < replaceFilesName.size(); i++) {
			
			Apttach apttache = new Apttach();
			apttache.setAttachName(replaceFilesName.get(i));
			
		}

			log.info("[ApprovalService] replaceFileName : {}", replaceFilesName);

			approvalRepository.save(modelMapper.map(approvalDto, Approval.class));
			
		} catch (IOException e) {

			e.printStackTrace();
			try {
				FileUploadUtils.deleteFiles(FILE_DIR, replaceFilesName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		log.info("[ApprovalService] 결재 등록 종료 ==========================");

		return approvalDto;
	}



	public Page<ApprovalDto> receiveApproval(int page, Long employeeNo) {

		return null;
	}
		

	//상신함 조회
	
	
}
