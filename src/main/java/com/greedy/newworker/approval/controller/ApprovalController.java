package com.greedy.newworker.approval.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.approval.dto.ApprovalDto;
import com.greedy.newworker.approval.service.ApprovalService;
import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.common.paging.Pagenation;
import com.greedy.newworker.common.paging.PagingButtonInfo;
import com.greedy.newworker.common.paging.ResponseDtoWithPaging;
import com.greedy.newworker.employee.dto.EmployeeDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/app/approval")
public class ApprovalController {
	
	private final ApprovalService approvalService;
	
	public ApprovalController(ApprovalService approvalService) {
		this.approvalService = approvalService;
	}
	
	
	/* 결재 문서 상신함 조회 */
	@GetMapping("/draft")
	public ResponseEntity<ResponseDto> sendApproval(@RequestParam(name = "page", defaultValue = "1") int page,
			@AuthenticationPrincipal EmployeeDto drafter) {
		
		Long employeeNo = drafter.getEmployeeNo();
		Page<ApprovalDto> sendApprovalList = approvalService.sendApproval(page, employeeNo);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(sendApprovalList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(sendApprovalList.getContent());
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"결재 상신함 조회 성공", responseDtoWithPaging));
	}
	
	
	
	
	/* 결재 문서 수신함 */
	@GetMapping("/approver")
	public ResponseEntity<ResponseDto> receiveApproval (@RequestParam(name = "page", defaultValue = "1") int page,
			@AuthenticationPrincipal EmployeeDto approver) {
		
		Long employeeNo = approver.getEmployeeNo();
		
//		Long employeeNo = approver.getEmployeeNo();
//		Page<ApprovalDto> receiveApprovalList = approvalService.receiveApproval(page, employeeNo);
		
		Page<ApprovalDto> receiveApprovalList = approvalService.receiveApproval(page, employeeNo);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(receiveApprovalList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(receiveApprovalList.getContent());
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"결재 수신함 조회 성공", responseDtoWithPaging));
	}
	
	
	
	/* 결재 문서 생성 */
	@PostMapping("/regist")
	public ResponseEntity<ResponseDto> appRegist(@ModelAttribute ApprovalDto appRegist,
			@AuthenticationPrincipal EmployeeDto drafter) {
		
		appRegist.setEmployee(drafter);
		log.info("[APPREGIST] appRegist : {}", appRegist);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재 상신 성공",
				approvalService.appRegist(appRegist)));
	}
	

	
	
	
	

}
