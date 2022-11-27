package com.greedy.newworker.message.service;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.greedy.newworker.message.dto.MessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {
	
	private final SimpMessageSendingOperations messagingTemplate;
	
	public void alarmByMessage(MessageDto sendMessage) {
		
		log.info("[alarm service] sendMessage : {}", sendMessage);
		
		messagingTemplate.convertAndSend("/sub/" + sendMessage.getRecipient().getEmployeeNo(), sendMessage);
	}
	

}
