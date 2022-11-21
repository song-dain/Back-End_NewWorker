package com.greedy.newworker.apttachImg.dto;

import lombok.Data;


import java.sql.Date;

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.notice.dto.NoticeDto;
import com.greedy.newworker.questionItem.dto.QuestionItemDto;

@Data
public class ApttachImgDto {
	
	private Long imgNo;
	private String orgName;
	private String svrName;
	private String imgPath;
	private String imgType;
	private NoticeDto notNo;

}