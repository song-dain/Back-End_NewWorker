package com.greedy.newworker.message.dto;

import com.greedy.newworker.employee.dto.EmployeeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
	
	private Long messageNo;
	private String messageContent;
	private java.sql.Date sendDate;
	private EmployeeDto recipient;
	private EmployeeDto sender;
	private String messageStatus;
	private Long unreadMessage;

}
