package com.greedy.newworker.rest.dto;

import java.sql.Date;


import com.greedy.newworker.employee.dto.EmployeeDto;

import lombok.Data;

@Data
public class RestDto {

	private Long restNo;
	private Date restDate;
	private Date restModdate;
	private String restOk;
	private Date restOkdate;
	private Long restDay;
	private Date restFdate;
	private Date restLdate;
	private String restReason;
	private String restYn;
	private RestCateDto restCateTypeNo;
	private EmployeeDto employeeNo;
	
	
	
	public Date update(Date sqlDate) {
		// TODO Auto-generated method stub
		return null;
	}
}
