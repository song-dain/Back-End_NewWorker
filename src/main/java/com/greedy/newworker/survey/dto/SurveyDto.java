package com.greedy.newworker.survey.dto;

import lombok.Data;


import java.sql.Date;

import com.greedy.newworker.employee.dto.DepartmentDto;
import com.greedy.newworker.employee.dto.EmployeeDto;

@Data
public class SurveyDto {
	
	private Long surNo;
	private String surTitle;
	private String surContent;
	private String surStatus;
	private Date surDate;
	private Date surUpdate;
	private Date surStartDate;
	private Date surEndDate;
	private EmployeeDto employee;
	private String surImgPath;
	private DepartmentDto dep;
}