package com.greedy.newworker.approval.dto;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.greedy.newworker.employee.dto.EmployeeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDto {

	private Long appNo;
	
	private EmployeeDto employee;
	
	private String appDocNo;
	private String appTitle;
	private String appContent;
	private String appStatus;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date appCreatedDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date appEndDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date appUpdateDate;
//	
//	List<결제선>
//	
//	List<첨부파일>
//	
//	List<MultipartFile>
	
}
