package com.greedy.newworker.survey.dto;

import lombok.Data;


import java.sql.Date;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.survey.dto.QuestionItemDto;
// 리스폰스
@Data
public class AnswerDto {
	
	private EmployeeDto employee;		//응답자
	private QuestionItemDto ansNo;		//항목 아이디

}