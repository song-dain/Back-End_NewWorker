package com.greedy.newworker.message.dto;

import lombok.Data;

@Data
public class MessageDto {
	
	private Long messageNo;
	private String messageContent;
	private java.sql.Date sendDate;
	private EmployeeDto recipient;
	private EmployeeDto sender;
	private String messageStatus;

}
