package com.greedy.newworker.notice.dto;

import lombok.Data;


import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	
	
	/* DB에는 없지만 추후 파일 업로드 로직 작성 시 활용할 필드 */
	@JsonIgnore
	private MultipartFile noticeImage;
	private String noticeImageUrl;
}