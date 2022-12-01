package com.greedy.newworker.approval.dto;

import java.sql.Date;

import com.greedy.newworker.employee.dto.EmployeeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppLineDto {

	private Long appLineNo;
	private Long appLineTurn;
	private EmployeeDto employee;
	private Long approvalNo;
	
	/* 결재 승인 과 관련된 부분 */
	private String acceptStatus;
	private String acceptActivate;
	private Date acceptDate;
	
	
}
