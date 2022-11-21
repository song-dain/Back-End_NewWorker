package com.greedy.newworker.att.controller;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.att.dto.AttDto;
import com.greedy.newworker.att.service.AttService;
import com.greedy.newworker.common.ResponseDto;
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
	
	
	@PutMapping("/end")
	//@RequestMapping(value="/end", method={RequestMethod.PUT, RequestMethod.POST})
	public ResponseEntity<ResponseDto> insertEnd(@RequestBody AttDto attDto, @AuthenticationPrincipal EmployeeDto employee) {
		
		LocalDateTime now = LocalDateTime.now();
		attDto.setEmployee(employee);
		attDto.setAttEnd(now);
		
//		log.info("[ AttController ] 퇴근 등록 시간 : {}", now);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "퇴근 등록 성공", attService.insertAttEnd(attDto)));
	}
	
}
