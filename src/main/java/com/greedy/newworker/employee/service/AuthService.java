package com.greedy.newworker.employee.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.dto.TokenDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.exception.LoginFailedException;
import com.greedy.newworker.employee.repository.EmployeeRepository;
import com.greedy.newworker.jwt.TokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final TokenProvider tokenProvider;
	
	
	public AuthService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, 
			ModelMapper modelMapper, TokenProvider tokenProvider) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder; 
		this.modelMapper = modelMapper;
		this.tokenProvider = tokenProvider;
	}

	
	
	
	/* 로그인 */
	public TokenDto login(EmployeeDto employeeDto) {
		log.info("[AuthService] 로그인 시작 =======================");
		log.info("[AuthService] employeeDto : {}", employeeDto);
		
		// 1. 아이디 조회
		Employee employee = employeeRepository.findByEmployeeId(employeeDto.getEmployeeId())
								.orElseThrow(() -> new LoginFailedException("잘못 된 아이디 또는 비밀번호입니다."));
		
		
		// 2. 비밀번호 매칭
		// matches 메소드로 매칭을 검증한다.
		if(!passwordEncoder.matches(employeeDto.getEmployeePwd(), employee.getEmployeePwd())) {
			log.info("[AuthService] 비밀번호 예외처리 확인 ==================");
			throw new LoginFailedException("잘못 된 아이디 또는 비밀번호입니다.");
		}
		
		
		// 3. 토큰 발급
		TokenDto tokenDto = tokenProvider.generateTokenDto(modelMapper.map(employee, EmployeeDto.class));
		log.info("[AuthService] tokenDto : {}", tokenDto);
		
		log.info("[AuthService] 로그인 종료 ====================");
		
		return tokenDto;
		
	}



	
	/* 아이디 찾기 */
	public Object idInquiry(EmployeeDto employeeDto) {
		log.info("[AuthService] 아이디 찾기 시작 =======================");
		log.info("[AuthService] employeeDto : {}", employeeDto);
		
		// 1. 이메일 조회
		Employee employee = employeeRepository.findByEmployeeNameAndEmployeeEmail(employeeDto.getEmployeeName(), employeeDto.getEmployeeEmail())
				.orElseThrow(() -> new LoginFailedException("잘못 된 이름 또는 이메일입니다."));
		
		if(employee == null) {
			return null;
		}
		
		return employee.getEmployeeId();
	}

	
	
}
