package com.greedy.newworker.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.employee.service.EmployeeService;

@RestController
@RequestMapping("/api/m1")
public class EmployeeController {

	private final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/employee/{memberId}")
	public ResponseEntity<ResponseDto> selectMyMemberInfo(@PathVariable String memberId) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", employeeService.selectMyInfo(memberId)));
	}
}
