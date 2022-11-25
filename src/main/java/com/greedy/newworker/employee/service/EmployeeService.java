package com.greedy.newworker.employee.service;

import java.io.IOException;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Department;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.entity.Position;
import com.greedy.newworker.employee.repository.EmployeeRepository;
import com.greedy.newworker.exception.UserNotFoundException;
import com.greedy.newworker.util.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	private final RegistCodeMailService registCodeMailService;

	@Value("${file.file-dir}" + "/employeeimgs")
	private String FILE_DIR;
	@Value("${file.file-url}" + "/employeeimgs/")
	private String FILE_URL;
	
	public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, RegistCodeMailService registCodeMailService) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
		this.registCodeMailService = registCodeMailService;
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
			replaceFileName = FileUploadUtils.saveFile(FILE_DIR, imageName, employeeDto.getEmployeeImage());
			employeeDto.setEmployeeImageUrl(replaceFileName);
			
			log.info("[EmployeeService] replaceFileName : {}", replaceFileName);
			
			employeeRepository.save(modelMapper.map(employeeDto, Employee.class));
		
		} catch (IOException e) {
			e.printStackTrace();
			
			try {
				FileUploadUtils.deleteFile(FILE_DIR, replaceFileName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
		
		log.info("[EmployeeService] 직원 등록 완료 ====================");
		
		
	}



/* 직원수정 */
	@Transactional
	public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
		
		log.info("[EmployeeService] updateEmployee Start ===================================");
		log.info("[EmployeeService] employeeDto : {}", employeeDto);

		String replaceFileName = null;

		try {

			Employee oriEmployee = employeeRepository.findById(employeeDto.getEmployeeNo()).orElseThrow(
					() -> new IllegalArgumentException("해당 직원이 없습니다. employeeNo=" + employeeDto.getEmployeeNo()));
			String oriImage = oriEmployee.getEmployeeImageUrl();

			/* 이미지를 변경하는 경우 */
			if (employeeDto.getEmployeeImage() != null) {
					
				/* 새로 입력 된 이미지 저장 */
				String imageName = UUID.randomUUID().toString().replace("-", "");
				replaceFileName = FileUploadUtils.saveFile(FILE_DIR, imageName, employeeDto.getEmployeeImage());
				employeeDto.setEmployeeImageUrl(replaceFileName);
				
				log.info("{} {}",FILE_DIR, oriImage);
				
				/* 기존에 저장 된 이미지 삭제*/
				FileUploadUtils.deleteFile(FILE_DIR, oriImage);

			} else { 
				/* 이미지를 변경하지 않는 경우 */
				employeeDto.setEmployeeImageUrl(oriImage);
			}
			
			/* 조회 했던 기존 엔티티의 내용을 수정 */
			oriEmployee.update( 					
					employeeDto.getEmployeePwd(), 
					employeeDto.getEmployeeName(), 
					employeeDto.getEmployeeEmail(),
					employeeDto.getEmployeePhone(),
					employeeDto.getEmployeeAddress(),
					employeeDto.getEmployeeStatus(),
					employeeDto.getEmployeeRole(),					
					modelMapper.map(employeeDto.getPosition(), Position.class),
					modelMapper.map(employeeDto.getDep(), Department.class),
//					employeeDto.getPosition().getPositionNo(), 
//					employeeDto.getDep().getDepNo(), 
					employeeDto.getEmployeeRestDay(),
					employeeDto.getEmployeeImageUrl(),					
					employeeDto.getEmployeeHireDate(),
					employeeDto.getEmployeeEntDate());
			
			employeeRepository.save(oriEmployee);
			
		} catch (IOException e) {
			e.printStackTrace();
			try {
				FileUploadUtils.deleteFile(FILE_DIR, replaceFileName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		log.info("[EmployeeService] updateEmployee End ===================================");
		
		return employeeDto;

				
	}

//	public Object selectMyInfo(String memberId) {
//
//		Employee employee = employeeRepository.findByEmployeeId(memberId)
//				.orElseThrow(() -> new UserNotFoundException(memberId + "를 찾을 수 없습니다."));
//		
//		
//		
//		return modelMapper.map(employee, EmployeeDto.class);
//	}


	
	
	


	
}
