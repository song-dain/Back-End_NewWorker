package com.greedy.newworker.message.dto;

import java.time.LocalDate;

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
	private String sendDate;
	private EmployeeDto recipient;
	private EmployeeDto sender;
	private String messageStatus;
	
	/* 가공데이터 */
	private Long unreadMessage;
	private LocalDate today = LocalDate.now();
	

}
