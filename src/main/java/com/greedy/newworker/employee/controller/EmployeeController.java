package com.greedy.newworker.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.service.EmployeeService;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

	private final EmployeeService employeeService;
	private final PasswordEncoder passwordEncoder;
	
	public EmployeeController(EmployeeService employeeService, PasswordEncoder passwordEncoder) {
		this.employeeService = employeeService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping("/employee/{memberId}")
	public ResponseEntity<ResponseDto> selectMyMemberInfo(@PathVariable String memberId) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", employeeService.selectMyInfo(memberId)));
	}
	
	
	/* 사원 등록 - 정혜연 */
	@PostMapping("/employee/register")
	public ResponseEntity<ResponseDto> insertProduct(@ModelAttribute EmployeeDto employeeDto) {
		
		employeeDto.setEmployeePwd(passwordEncoder.encode(employeeDto.getEmployeePwd()));
		
		employeeService.insertEmployee(employeeDto);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 등록 성공", null));
		
	}
	
	/* 직원수정 */
	@PutMapping("/employee/{employeeNo}")
	public ResponseEntity<ResponseDto> updateEmployee(@ModelAttribute EmployeeDto employeeDto, @PathVariable Long employeeNo) {
		employeeDto.setEmployeeNo(employeeNo);
		
		employeeDto.setEmployeePwd(passwordEncoder.encode(employeeDto.getEmployeePwd()));
		
		employeeService.updateEmployee(employeeDto);
		
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 수정 성공", null));
	

}
	
	/* [캘린더] 본인 정보 */
	@GetMapping("/employee/empInfo")
	public ResponseEntity<ResponseDto> employeeInfo(@AuthenticationPrincipal EmployeeDto employee){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 정보 조회 성공", employee));
	}
	
	
	
}
