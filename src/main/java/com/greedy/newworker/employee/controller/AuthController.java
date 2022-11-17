package com.greedy.newworker.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	
	
	/* 로그인 */
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody EmployeeDto employeeDto) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "로그인에 성공했습니다.", authService.login(employeeDto)));
		
	}
	

	
	
}
