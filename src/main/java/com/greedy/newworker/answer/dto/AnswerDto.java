package com.greedy.newworker.answer.dto;

import lombok.Data;


import java.sql.Date;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.questionItem.dto.QuestionItemDto;

@Data
public class AnswerDto {
	
	private EmployeeDto employee;
	private QuestionItemDto ansNo;

}