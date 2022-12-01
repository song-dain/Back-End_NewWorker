package com.greedy.newworker.approval.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	
	
	
	/* 결재 문서 수신함 */
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
	
	
	/* 기안자 결재문서 상세 조회 */
	@GetMapping("/drafterDetail/{appNo}")
	public ResponseEntity<ResponseDto> selectDrafterDetail(@PathVariable Long appNo) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "기안자 결재 문서 상세 조회 성공", approvalService.selectDrafterDetail(appNo)));
	}
	
	
	/* 결재자 결재문서 상세 조회 */
	@GetMapping("/approverDetail/{appNo}")
	public ResponseEntity<ResponseDto> selectApproverDetail(@PathVariable Long appNo) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재자 결재 문서 상세 조회 성공", approvalService.selectApproverDetail(appNo)));
	}
	
	
	
	/* 기안자 결재상태 변경(회수) => '회수' 일 경우 기안자가 문서 수정 및 삭제 가능해짐 */
	@PatchMapping("/appStatus")
	public ResponseEntity<ResponseDto> changeAppStatus(@RequestParam("appStatus") String appStatus) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재상태 변경 성공", approvalService.changeAppStatus(appStatus)));
	}
	
	
	/* 결재자 승인상태 변경(승인&반려) => 승인일 경우 결재상태가 '진행중' 으로, 반려일 경우 결재상태가 '반려'로. 승인일 경우 다음 결제자에게 결재활성화여부 Y 부여*/
	/* 결제활성화여부Y 갯수만큼 승인갯수가 결재문서에 존재할 경우 결재상태 '완료'로  */
	/* 기안자 승인상태 '승인'처리 */
	@PutMapping("/acceptStatus")
	public ResponseEntity<ResponseDto> changeAcceptStatus(@ModelAttribute AppLineDto accChange) {
		
		// 승인 로직
		// appLineNo와, appNo 를 받아온다.
		// 1. appLineNo 기준으로 조회 -> 상태 변경(accStatus '승인')
		// 2. appLineTurn + 1 하고, approvalNo 로 다시 한 번 appLineNo 조회 (다음 사람을 찾는다.)
		// 3. 있을 경우, appLineTurn+1 인 acceptActivate를 업데이트('Y')
		// 4. approvalNo 기준으로, 문서 조회. appStatus를 진행중 또는 완료로 변경.
		
		// ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ
		// 으아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ
		// 으아아아아아악~~~~~~~~~~~~~~~~~~~~~~~흐아아아아앙~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ후아으아ㅣㅏㅣㅏㅣ응
		// 으아앙 ㅜ아아앙아앙으흐흫허어어어허어어엉ㅇ 흐흐ㅏ아하아아 ㅠㅠㅠ
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"승인상태 변경 적용 성공", approvalService.changeAccStatus(accChange)));
		
	}
	
	
	
	

}
