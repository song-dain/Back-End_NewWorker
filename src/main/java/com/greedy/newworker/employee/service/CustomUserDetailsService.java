package com.greedy.newworker.employee.service;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
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
		employeeDto.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(employeeDto.getEmployeeRole())));
		
		return employeeDto;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
}
