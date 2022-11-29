package com.greedy.newworker.survey.dto;



import lombok.Data;
//Answer
@Data
public class QuestionItemDto {
	
	private Long ansNo;				//항목 아이디
	private String ansContent;		//항목 내용
	private SurveyDto surNo;		//설문 아이디 (PK)
}