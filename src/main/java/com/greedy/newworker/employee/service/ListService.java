package com.greedy.newworker.employee.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.ListRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ListService {
	
	private final ListRepository listRepository;
	private final ModelMapper modelMapper;
	
	public ListService(ListRepository listRepository, 
			ModelMapper modelMapper) {
		this.listRepository = listRepository;
		this.modelMapper = modelMapper;
		
	}	
	
	
/* 회원조회 */
	
	public Page<EmployeeDto> selectEmployeeList(int page) {
		log.info("[ListService] selectList Start =====================" );
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("employeeNo").descending());
		
		Page<Employee> employeeList = listRepository.findAll(pageable);
		Page<EmployeeDto> employeeDtoList = employeeList.map(list -> modelMapper.map(list, EmployeeDto.class));
		
		log.info("[ListService] employeeDtoList : {}", employeeDtoList.getContent());
		
		log.info("[ListService] selectEmployeeList End =====================" );
		
		return employeeDtoList;
	}


//	public Object selectEmployeeForAdmin(Long employeeNo) {
//		
//		log.info("[ListService] selectEmployeeForAdmin Start ===================================");
//        log.info("[ListService] employeeNo : " + employeeNo);
//        
//        Employee employee = listRepository.findByEmployeeNo(employeeNo)
//        		.orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. employeeNo=" + employeeNo));
//        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
//        
//        log.info("[ListService] employeeDto : " + employeeDto);
//        
//        log.info("[ListService] selectEmployeeForAdmin End ===================================");
//        
//        return employeeDto;
//	}

}
