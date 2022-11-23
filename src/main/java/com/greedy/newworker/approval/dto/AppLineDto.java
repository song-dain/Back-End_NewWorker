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
public class AppLineDto {

	private Long appLineNo;
	private Long appLineTurn;
	private ApprovalDto app;
	private EmployeeDto employee;
	
	
}
