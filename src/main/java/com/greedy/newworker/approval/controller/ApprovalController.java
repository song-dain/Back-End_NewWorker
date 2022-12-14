package com.greedy.newworker.approval.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.approval.dto.AppLineDto;
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
	
	
	/* 부서별 결재자 조회 */
	@GetMapping("/findApprover/{depNo}")
	public ResponseEntity<ResponseDto> findApprover(@PathVariable Long depNo, @AuthenticationPrincipal EmployeeDto employee){
		
		log.info("[ApprovalController] fineApprover : {}", depNo);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "부서별 직원 조회 성공", approvalService.findApprover(depNo, employee)));
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
	
	
	
	
	/* 결재 문서 수신함 조회 */
	@GetMapping("/approver")
	public ResponseEntity<ResponseDto> receiveApproval (@RequestParam(name = "page", defaultValue = "1") int page,
			@AuthenticationPrincipal EmployeeDto approver) {
		
		Long employeeNo = approver.getEmployeeNo();
		

		
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
	
	
	/* 기안자 - 결재문서 '삭제'처리 */
	@PostMapping("/removeApproval")
	public ResponseEntity<ResponseDto> removeApproval(@RequestBody ApprovalDto removeApproval,
			@AuthenticationPrincipal EmployeeDto drafter) {
		removeApproval.setEmployee(drafter);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재 문서 삭제 성공",
		approvalService.removeApproval(removeApproval)));
		
	}
	
	
	/* 기안자 - 결재문서 상세 조회 */
	@GetMapping("/drafterDetail/{appNo}")
	public ResponseEntity<ResponseDto> selectDrafterDetail(@PathVariable Long appNo) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "기안자 결재 문서 상세 조회 성공", approvalService.selectDrafterDetail(appNo)));
	}
	
	
	/* 결재자 - 결재문서 상세 조회 */
	@GetMapping("/approverDetail/{appNo}")
	public ResponseEntity<ResponseDto> selectApproverDetail(@PathVariable Long appNo) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재자 결재 문서 상세 조회 성공", approvalService.selectApproverDetail(appNo)));
	}
	
	
	
	/* 기안자 - 결재상태 변경(회수) => '회수' 일 경우 기안자가 문서 삭제 가능해짐 */
	@PutMapping("/appStatus")
	public ResponseEntity<ResponseDto> changeAppStatus(@RequestBody ApprovalDto appStatusChange) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재상태 변경 성공", approvalService.changeAppStatus(appStatusChange)));
	}


	
	/* 결재자 - 승인상태 '승인'처리 */
	@PutMapping("/acceptStatus")
	public ResponseEntity<ResponseDto> changeAcceptStatus(@RequestBody AppLineDto accChange) {
		
		log.info("accChange : {}", accChange);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"승인상태 승인 변경 적용 성공", approvalService.changeAccStatus(accChange)));
		
	}
	
	
	/* 결재자 - 승인상태 '반려'처리 */
	@PutMapping("/notAcceptStatus")
	public ResponseEntity<ResponseDto> changeNotAcceptStatus(@RequestBody AppLineDto accChange) {
			
			log.info("accChange : {}", accChange);
			
			return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"승인상태 반려 변경 적용 성공", approvalService.changeNotAccStatus(accChange)));
		
	}
	
	


}
