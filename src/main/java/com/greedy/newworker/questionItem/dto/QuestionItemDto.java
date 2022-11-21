package com.greedy.newworker.questionItem.dto;

import com.greedy.newworker.survey.dto.SurveyDto;

import lombok.Data;

@Data
public class QuestionItemDto {
	
	private Long ansNo;
	private String ansContent;
	private SurveyDto surNo;
}