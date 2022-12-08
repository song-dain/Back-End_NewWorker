package com.greedy.newworker.employee.service;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.EmployeeRepository;
import com.greedy.newworker.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Value("${file.file-dir}" + "/employeeimgs")
	private String FILE_DIR;
	@Value("${file.file-url}" + "/employeeimgs/")
	private String FILE_URL;
	
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	public CustomUserDetailsService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
		log.info("[CustomUserDetailService] loadUserByUsername Start ======================== ");
		log.info("[CustomUserDetailService] memberId : {}", employeeId);
		
		return employeeRepository.findByEmployeeId(employeeId)
				.map(user -> addAuthorities(user))
				.orElseThrow(() -> new UserNotFoundException(employeeId + "를 찾을 수 없습니다."));
		
	}
	
	private EmployeeDto addAuthorities(Employee employee) {
		
		
		EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
		employeeDto.setEmployeeImageUrl(FILE_URL + employee.getEmployeeImageUrl());
		employeeDto.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(employeeDto.getEmployeeRole())));
		
		// /* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
		//productDtoList.forEach(product -> product.setProductImageUrl
		//(IMAGE_URL + product.getProductImageUrl()));
		
		return employeeDto;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
}
