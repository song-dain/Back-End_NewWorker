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
import com.greedy.newworker.employee.service.EmployeeService;
import com.greedy.newworker.employee.service.RegistCodeMailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private final RegistCodeMailService registCodeMailService;
	private final EmployeeService employeeService;
	
	
	public AuthController(AuthService authService, RegistCodeMailService registCodeMailService,
							EmployeeService employeeService) {
		this.authService = authService;
		this.registCodeMailService = registCodeMailService;
		this.employeeService = employeeService;
	}
	
	
	
	/* 로그인 */
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody EmployeeDto employeeDto) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "로그인에 성공했습니다.", authService.login(employeeDto)));
		
	}
	
	
	
	/* 아이디 찾기 */
	@PostMapping("/idInquiry")
	public ResponseEntity<ResponseDto> idInquiry(@RequestBody EmployeeDto employeeDto) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "아이디 조회에 성공했습니다.", authService.idInquiry(employeeDto)));
	}
	
	
	/* 비밀번호 찾기/변경 - 이메일 인증 */
	@PostMapping("/mailConfirm")
	public ResponseEntity<ResponseDto> mailConfirm(@RequestBody EmployeeDto employeeDto) throws Exception{
		
		log.info("/mailConfirm start ================================");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "인증코드 발송 성공", authService.findPwd(employeeDto)));
		
	}
	
	
	/* 비밀번호 찾기 - 임시 비밀번호 발송 */
	@PostMapping("/pwdInquiry")
	public ResponseEntity<ResponseDto> pwdInquiry(@RequestBody EmployeeDto employeeDto) throws Exception {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "임시 비밀번호 발송 성공", authService.pwdInquiry(employeeDto)));
	}
	
	
	/* 비밀번호 변경 */
	@PostMapping("/pwdUpdate")
	public ResponseEntity<ResponseDto> pwdUpdate(@RequestBody EmployeeDto employeeDto) throws Exception {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "비밀번호 변경 성공", authService.pwdUpdate(employeeDto)));
	}

	
	
}
