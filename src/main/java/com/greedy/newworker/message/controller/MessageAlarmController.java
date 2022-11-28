package com.greedy.newworker.message.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageAlarmController {
	
	private final SimpMessageSendingOperations messagingTemplate;
	
	/* 웹소켓 연결 */
	@MessageMapping("/{empNo}")
	public void sendMessage(@DestinationVariable("empNo")Long employeeNo) {
		
		log.info("[alarm] 웹소켓 연결");
		
		messagingTemplate.convertAndSend("/sub/" + employeeNo, "alarm socket connection completed");
	}

}
