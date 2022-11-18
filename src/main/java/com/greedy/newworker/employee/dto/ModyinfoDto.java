package com.greedy.newworker.employee.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ModyinfoDto {

	private Date ModDate;
	private EmployeeDto EmployeeNo;
	private PositionDto DepNo;
	private Long ModNo;
	
}
