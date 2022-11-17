package com.greedy.newworker.att.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<ResponseDto> insertStart(Date attStart, @AuthenticationPrincipal EmployeeDto employee) throws ParseException {
		
		log.info("[ AttController ] 출근 등록 시간 : {}", attStart);
		AttDto attDto = new AttDto();
		
		attDto.setEmployee(employee);
		attDto.setAttStart(attStart);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "출근 등록 성공", attService.insertAttStart(attDto)));
		
	}

}
