package com.greedy.newworker.employee.service;

import java.io.IOException;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.EmployeeRepository;
import com.greedy.newworker.exception.UserNotFoundException;
import com.greedy.newworker.util.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;

	@Value("${image.image-dir}" + "/employeeimgs")
	private String IMAGE_DIR;
	@Value("${image.image-url}" + "/employeeimgs/")
	private String IMAGE_URL;
	
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


	
	/* 사원 등록 - 정혜연 */
	@Transactional
	public void insertEmployee(EmployeeDto employeeDto) {
		
		log.info("[EmployeeService] 직원 등록 시작 ====================");
		log.info("[EmployeeService] employeeDto : {}", employeeDto);
		String imageName = UUID.randomUUID().toString().replace("-", ""); //랜덤 유효 아이디 생성
		String replaceFileName = null;
		
		try {
			replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, employeeDto.getEmployeeImage());
			employeeDto.setEmployeeImageUrl(replaceFileName);
			
			log.info("[EmployeeService] replaceFileName : {}", replaceFileName);
			
			employeeRepository.save(modelMapper.map(employeeDto, Employee.class));
		
		} catch (IOException e) {
			e.printStackTrace();
			
			try {
				FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
		
		log.info("[EmployeeService] 직원 등록 완료 ====================");
		
		
	}
	
	
	
	
	
}
