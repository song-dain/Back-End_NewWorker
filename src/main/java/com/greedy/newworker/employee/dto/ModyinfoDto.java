package com.greedy.newworker.employee.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ModyinfoDto {

	private Date modDate;
	private EmployeeDto employeeNo;
	private PositionDto depNo;
	private Long modNo;
	
}
