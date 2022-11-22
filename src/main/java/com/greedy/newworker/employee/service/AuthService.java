package com.greedy.newworker.employee.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.dto.TokenDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.exception.LoginFailedException;
import com.greedy.newworker.employee.repository.EmployeeRepository;
import com.greedy.newworker.jwt.TokenProvider;
import com.greedy.newworker.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final TokenProvider tokenProvider;
	private final RegistCodeMailService registCodeMailService;
	private final FindPwdMailService findPwdMailService;
	private final RedisUtil redisUtil;
	
	
	public AuthService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, 
						ModelMapper modelMapper, TokenProvider tokenProvider, RegistCodeMailService registCodeMailService, 
						FindPwdMailService findPwdMailService, RedisUtil redisUtil) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder; 
		this.modelMapper = modelMapper;
		this.tokenProvider = tokenProvider;
		this.registCodeMailService = registCodeMailService;
		this.findPwdMailService = findPwdMailService;
		this.redisUtil = redisUtil;
		
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
	
	
	/* 비밀번호 찾기(1) - 이메일 인증 */	
	public Object findPwd(EmployeeDto employeeDto) throws Exception {
		
		Employee employee = employeeRepository.findByEmployeeIdAndEmployeeNameAndEmployeeEmail(employeeDto.getEmployeeId(), employeeDto.getEmployeeName(), employeeDto.getEmployeeEmail());
		
		log.info("findPwd employee : {}", employee);
		
		String code = registCodeMailService.sendSimpleMessage(employee.getEmployeeEmail());
		
		log.info("findPwd code : {}", code);
		return code;
	}



	/* 비밀번호 찾기(2) - 임시 비밀번호 */	
	@Transactional
	public Object pwdInquiry(EmployeeDto employeeDto) throws Exception {
		

		Employee employee = employeeRepository.findByEmployeeIdAndEmployeeNameAndEmployeeEmail(employeeDto.getEmployeeId(), employeeDto.getEmployeeName(), employeeDto.getEmployeeEmail());
		
		log.info("아이디 : {}", employeeDto.getEmployeeId());
		log.info("이름 : {}", employeeDto.getEmployeeName());
		log.info("이메일 : {}", employeeDto.getEmployeeEmail());
		
		
		String email = redisUtil.getData(employeeDto.getCode());
		
		log.info("Redis 이메일 확인 : {}", email);
		if(employeeDto.getEmployeeEmail().equals(email)) {

			String tempPwd = passwordEncoder.encode(findPwdMailService.sendSimpleMessage(employee.getEmployeeEmail()));
			log.info("tempPwd : {}", tempPwd);
			log.info("tempPwd 발송용 이메일 : {}", employee.getEmployeeEmail());
			employee.setEmployeePwd(tempPwd);

			employeeRepository.save(employee);
			
			return tempPwd;
			
		}

		
		return null;
	}
	

	
	
	
}
