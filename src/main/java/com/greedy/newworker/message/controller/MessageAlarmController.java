package com.greedy.newworker.message.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageAlarmController {
	
	private final SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/{empNo}")
	public void message(@DestinationVariable("empNo")Long employeeNo) {
		messagingTemplate.convertAndSend("/sub/" + employeeNo, "alarm socket connection completed");
	}

}
