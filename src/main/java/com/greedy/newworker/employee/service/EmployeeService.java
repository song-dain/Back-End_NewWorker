package com.greedy.newworker.employee.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.EmployeeRepository;
import com.greedy.newworker.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}
	
	
	
/* 회원 정보 조회*/
	public Object selectMyInfo(String memberId) {

		Employee employee = employeeRepository.findByEmployeeId(memberId)
				.orElseThrow(() -> new UserNotFoundException(memberId + "를 찾을 수 없습니다."));
		
		
		
		return modelMapper.map(employee, EmployeeDto.class);
	}
}
