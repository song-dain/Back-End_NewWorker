package com.greedy.newworker.att.controller;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.att.dto.AttDto;
import com.greedy.newworker.att.service.AttService;
import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.common.paging.Pagenation;
import com.greedy.newworker.common.paging.PagingButtonInfo;
import com.greedy.newworker.common.paging.ResponseDtoWithPaging;
import com.greedy.newworker.employee.dto.EmployeeDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/att")
public class AttController {
	
	
	private final AttService attService;
	
	public AttController (AttService attService) {
		this.attService = attService;
	}
	
	/* 출근 등록 */
	@PostMapping("/start")											 
	public ResponseEntity<ResponseDto> insertStart(@AuthenticationPrincipal EmployeeDto employee) throws ParseException {
		
		
		AttDto attDto = new AttDto();
		LocalDateTime now = LocalDateTime.now();
		
		attDto.setEmployee(employee);
		attDto.setAttStart(now);
		
		log.info("[ AttController ] 출근 등록 시간 : {}", now);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "출근 등록 성공", attService.insertAttStart(attDto)));
		
	}
	
	/* 퇴근 등록 */
	@PutMapping("/end")
	public ResponseEntity<ResponseDto> insertEnd(@RequestBody AttDto attDto, @AuthenticationPrincipal EmployeeDto employee) {
		
		LocalDateTime now = LocalDateTime.now();
		attDto.setEmployee(employee);
		attDto.setAttEnd(now);
		attDto.setAttDate(now);
		log.info("[ AttController ] 퇴근 등록 시간 : {}", now);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "퇴근 등록 성공", attService.insertAttEnd(attDto)));
	}
	
	/* -> 날짜로 찾는거 못하겠어서 근태번호로 검색 */
	@GetMapping("/days")
	public ResponseEntity<ResponseDto> selectTodayAttEmployee(Long attNo, @AuthenticationPrincipal EmployeeDto employee) {
		
		log.info("[ AttController ] 직원용 - 근태 번호 : {}", attNo);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "일일 근태 조회", attService.selectAttDay(attNo , employee.getEmployeeNo())));
	}
	
	/* 전부 다 조회 가능한 관리자용 근태번호로 조회 */
	@GetMapping("/admin/days")
	public ResponseEntity<ResponseDto> selectTodayAttAdmin(Long attNo) {
		
		log.info("[ AttController ] 관리자용 - 근태 번호 : {}", attNo);
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "일일 근태 조회", attService.selectAttDayAdmin(attNo)));
	}
	
	/* 날짜(월)로 검색 - 직원용 */
	@GetMapping("/months")
	public ResponseEntity<ResponseDto> selectMonthAttListEmployee(
			@RequestParam(name="page", defaultValue="1") int page, 
			String attMonth, @AuthenticationPrincipal EmployeeDto employee) {
		
		log.info("[ AttController ] 직원용 - 입력받은 attMonth : {}", attMonth);
		
		Long employeeNo = employee.getEmployeeNo();
		log.info("[ AttController ] 직원용 - 입력받은 employeeNo : {}", employeeNo);
		
		Page<AttDto> attDtoList = attService.selectAttListByMonthAndEmployeeNo(page, attMonth, employeeNo);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(attDtoList);
		log.info("[ AttController ] pageInfo : {}", pageInfo);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(attDtoList.getContent());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "리스트 조회 완료", responseDtoWithPaging));
	}
	
	/* 날짜(월)로 검색 - 관리자용 */
	@GetMapping("/months/admin")
	public ResponseEntity<ResponseDto> selectMonthAttListAdmin(
			@RequestParam(name="page", defaultValue="1") int page, 
			String attMonth, Long employeeNo) {
		
		log.info("[ AttController ] 관리자용 - 입력받은 attMonth : {}", attMonth);
		log.info("[ AttController ] 관리자용 - 입력받은 employeeNo : {}", employeeNo);
		
		Page<AttDto> attDtoList = attService.selectAttListByMonthAndEmployeeNo(page, attMonth, employeeNo);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(attDtoList);
		log.info("[ AttController ] pageInfo : {}", pageInfo);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(attDtoList.getContent());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "리스트 조회 완료", responseDtoWithPaging));
	}
}
