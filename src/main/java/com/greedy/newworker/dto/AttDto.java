package com.greedy.newworker.dto;

import com.greedy.newworker.employee.dto.EmployeeDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttDto {
	
	private Long attNo;
	private AttTypeDto attType;
	private EmployeeDto employee;
	private java.util.Date attStart;
	private java.util.Date attEnd;
	private java.util.Date attDate;

}
