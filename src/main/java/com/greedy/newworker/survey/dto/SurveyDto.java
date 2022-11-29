package com.greedy.newworker.survey.dto;

import lombok.Data;


import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greedy.newworker.approval.dto.AppLineDto;
import com.greedy.newworker.employee.dto.DepartmentDto;
import com.greedy.newworker.employee.dto.EmployeeDto;

@Data
public class SurveyDto {
	
	private Long surNo;
	private String surTitle;
	private String surContent;
	private String surStatus;
	private Date surDate;
	private Date surUpDate;
	private Date surStartDate;
	private Date surEndDate;
	private EmployeeDto employee;
	private String surImgPath;
	private DepartmentDto dep;
	
	private List<QuestionItemDto> questionItem;
	
	/* DB에는 없지만 추후 파일 업로드 로직 작성 시 활용할 필드 */
	@JsonIgnore
	private MultipartFile surveyImage;
	private String surveyImageUrl;
	
	
}