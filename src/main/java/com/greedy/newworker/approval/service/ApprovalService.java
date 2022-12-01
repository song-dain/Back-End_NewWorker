package com.greedy.newworker.approval.service;

import java.io.IOException;
import java.util.ArrayList;
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
import com.greedy.newworker.approval.entity.Approval;
import com.greedy.newworker.approval.repository.AppLineRepository;
import com.greedy.newworker.approval.repository.ApprovalRepository;
import com.greedy.newworker.approval.repository.ApttachRepository;
import com.greedy.newworker.approval.repository.PositionRepository;
import com.greedy.newworker.apttach.dto.ApttachDto;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Department;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.DepartmentRepository;
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
	private final ApttachRepository apttachRepository;
	private final DepartmentRepository departmentRepository;
	private final ModelMapper modelMappler;
	
	@Value("${file.file-dir}" + "/approvalfiles")
	private String FILE_DIR;
	@Value("${file.file-url}" + "/approvalfiles/")
	private String FILE_URL;
	
	public ApprovalService(ModelMapper modelMapper, ApprovalRepository approvalRepository, 
			EmployeeRepository employeeRepository, AppLineRepository appLineRepository,
			ApttachRepository apttachRepository, DepartmentRepository departmentRepository,
			PositionRepository positionRepository, ModelMapper modelMappler) {
		this.modelMapper = modelMapper;
		this.approvalRepository = approvalRepository;
		this.employeeRepository = employeeRepository;
		this.appLineRepository = appLineRepository;
		this.apttachRepository = apttachRepository;
		this.departmentRepository = departmentRepository;
		this.modelMappler = modelMappler;
	}

	// 부서별 결재자 조회
	public List<EmployeeDto> findApprover(Long depNo, EmployeeDto employee) {
		
		Department dep = departmentRepository.findById(depNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부서입니다."));
		
		
		List<Employee> empList = employeeRepository.findByDepWithoutEmployee(dep.getDepNo(), modelMappler.map(employee, Employee.class).getEmployeeNo());
		
		return empList.stream().map(emp -> modelMapper.map(emp, EmployeeDto.class)).toList();
	}
	
	
	// 결재 상신함 조회
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
		
		
		// 결재문서 등록 시, 첫 번째 결재자의 결재활성화여부가 Y로 변경하도록 설정
		approvalDto.getAppLines().get(0).setAcceptActivate("Y");
	
		
		
		// 첨부파일
		String fileName = UUID.randomUUID().toString().replace("-", ""); // 랜덤 유효 아이디 생성
		List<String> replaceFilesName = null;
		List<ApttachDto> apttache = new ArrayList<ApttachDto>();
	
		ApttachDto apttachDto = null;
		
		try {
			replaceFilesName = FileUploadUtils.saveFiles(FILE_DIR, fileName, approvalDto.getApprovalFiles());
		// 배열로 들어가기 때문에 반복문으로 SIZE 만큼 삽입
		for(int i = 0; i < replaceFilesName.size(); i++) {
			
			apttachDto = new ApttachDto(replaceFilesName.get(i));
			apttache.add(apttachDto);
			
			
			log.info("[ApprovalService] apttachDto : {}", apttachDto);
		}
			//첨부파일 엔터티에 replaceFilesName 등록하여 관리
			approvalDto.setAttaches(apttache);
			
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


	// 결재 수신함 조회
	public Page<ApprovalDto> receiveApproval(int page, Long employeeNo) {
		
		Pageable pageable = PageRequest.of(page - 1,  10, Sort.by("appNo").descending());
		
		// find 해야 하는 것들 = 내 사원 번호, 결재선의 사원번호(나와 일치해야 함)
		Page<Approval> receiveApproval = approvalRepository.findReceiveApproval(pageable, employeeNo);
		Page<ApprovalDto> receiveApprovalList = receiveApproval.map(approval -> modelMapper.map(approval, ApprovalDto.class));
		
		log.info("receiveApprovalList : {}", receiveApprovalList);
		
		return receiveApprovalList;
	}
	
	
	/* 결재 문서 상세 조회 */
//	public ApprovalDto selectApprovalDetail(Long appNo) {
//
//		log.info("[selectApprovalDetail] 결재 상세 조회 시작 =====================");
//		log.info("[selectApprovalDetail] appNo : {}", appNo);
//		
//		Approval approval = approvalRepository.findByAppNo(appNo)
//				.orElseThrow(() -> new IllegalArgumentException("해당 결재 페이지가 존재하지 않습니다. appNo=" + appNo));
//		ApprovalDto approvalDto = modelMapper.map(approval, ApprovalDto.class);
//
//		return approvalDto;
//	}

	/* 기안자 결재 문서 상세 조회 */
	public ApprovalDto selectDrafterDetail(Long appNo) {

		log.info("[selectApprovalDetail] 결재 상세 조회 시작 =====================");
		log.info("[selectApprovalDetail] appNo : {}", appNo);
		
		Approval approval = approvalRepository.findByAppNo(appNo)
				.orElseThrow(() -> new IllegalArgumentException("해당 결재 페이지가 존재하지 않습니다. appNo=" + appNo));
		ApprovalDto approvalDto = modelMapper.map(approval, ApprovalDto.class);

		return approvalDto;
	}
	
	/* 결재자 결재 문서 상세 조회 */
	public ApprovalDto selectApproverDetail(Long appNo) {

		log.info("[selectApprovalDetail] 결재 상세 조회 시작 =====================");
		log.info("[selectApprovalDetail] appNo : {}", appNo);
		
		Approval approval = approvalRepository.findByAppNo(appNo)
				.orElseThrow(() -> new IllegalArgumentException("해당 결재 페이지가 존재하지 않습니다. appNo=" + appNo));
		ApprovalDto approvalDto = modelMapper.map(approval, ApprovalDto.class);

		return approvalDto;
	}

	
	/* 결재 상태 변경 (회수) */
	public Object changeAppStatus(String appStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	/* 승인 상태 변경 (승인) */
	public ApprovalDto changeAccStatus(AppLineDto accChange) {
		
		
		
		
//		
//		int accSize = originAccStatus.getAppLines().size(); // 결재순번의 갯수
//		int accString = originAccStatus.getAppLines().lastIndexOf("승인"); // 마지막 승인의 순서값
//		// 어떻게 그때그때 바뀔 AppLine 안의 accStatus의 특정 인덱스 순서를 찾지?
//		
//		if(accSize == accString) {
//			originAccStatus.setAppStatus("완료");
//		}
//
//		// 어떻게 해당순번의 상태값을 변경하지? ㅜ
//		
//
//		originAccStatus.setAppStatus("진행중");

		
		
		return null;
	}


	


	
	
	
	

		


	
}
