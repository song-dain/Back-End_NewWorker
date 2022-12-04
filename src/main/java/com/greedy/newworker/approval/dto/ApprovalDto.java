package com.greedy.newworker.approval.dto;

import java.sql.Date;
import java.util.List;


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
	
	
	private Date appCreatedDate;
	
	private Date appEndDate;
	
	private Date appUpdateDate;
	
	private List<AppLineDto> appLines;
	
	
	
	
}
