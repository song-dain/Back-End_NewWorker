package com.greedy.newworker.notice.dto;

import lombok.Data;


import java.sql.Date;

import com.greedy.newworker.employee.dto.EmployeeDto;

@Data
public class NoticeDto {
	
	private Long notNo;
	private String notTitle;
	private String notContent;
	private Date notDate;
	private String notStatus;
	private Long notCount;
	private Date notUpdate;
	private EmployeeDto employee;
}