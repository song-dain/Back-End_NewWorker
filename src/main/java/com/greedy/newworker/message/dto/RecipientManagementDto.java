package com.greedy.newworker.message.dto;

import lombok.Data;

@Data
public class RecipientManagementDto {
	
	private MessageDto message;
	private String receiveMessageCategory;
	private String receiveMessageDelete;

}
