package com.greedy.newworker.employee.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ModyinfoDto {

	private Date modDate;
	private EmployeeDto employeeNo;
	private DepartmentDto depNo;
	private PositionDto positionNo;
	private Long modNo;
	
}
