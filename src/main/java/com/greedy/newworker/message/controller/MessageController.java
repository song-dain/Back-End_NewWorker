package com.greedy.newworker.message.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.common.paging.Pagenation;
import com.greedy.newworker.common.paging.PagingButtonInfo;
import com.greedy.newworker.common.paging.ResponseDtoWithPaging;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.message.dto.MessageDto;
import com.greedy.newworker.message.service.MessageService;

@RestController
@RequestMapping("/emp/message")
public class MessageController {
	
	private final MessageService messageService;
	
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	
	/* 받은 메시지함 완!!!!!! */
	@GetMapping("/receive")
	public ResponseEntity<ResponseDto> receiveMessages(@RequestParam(name="page", defaultValue="1")int page, @AuthenticationPrincipal EmployeeDto recipient){
		
		Page<MessageDto> receiveMessageList = messageService.receiveMessages(page, recipient);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(receiveMessageList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(receiveMessageList.getContent());
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "받은 메시지함 조회 성공", responseDtoWithPaging));
	}
	
	
	/* 받은 메시지 상세 조회 완!!!!!! */
	@PatchMapping("/receive/{messageNo}/read")
	public ResponseEntity<ResponseDto> selectReceiveMessage(@PathVariable Long messageNo, @AuthenticationPrincipal EmployeeDto recipient){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "받은 메시지 조회 성공", messageService.selectReceiveMessage(messageNo, recipient)));
	}
	
	
	/* 받은 메시지 중요 메시지함 이동 보류 (휴지통 이동이랑 같이 해야 돼서)--------------------------------------- */
	@PatchMapping("/receive/{messageNo}")
	public ResponseEntity<ResponseDto> receiveMessageToImpoMessage(@PathVariable Long messageNo){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "받은 메시지 중요 메시지함으로 이동 성공", messageService.receiveMessageToImpoMessage(messageNo)));
	}
	
	
	/* 보낸 메시지함 완!!!!!!!!!! */
	@GetMapping("/send")
	public ResponseEntity<ResponseDto> sendMessages(@RequestParam(name="page", defaultValue="1")int page, @AuthenticationPrincipal EmployeeDto sender){
		
		Page<MessageDto> sendMessageList = messageService.sendMessages(page, sender);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(sendMessageList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(sendMessageList.getContent());
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보낸 메시지함 조회 성공", responseDtoWithPaging));
	}

	
	/* 보낸 메시지 상세 조회 완!!!!!!!!!!! */
	@GetMapping("/send/{messageNo}/read")
	public ResponseEntity<ResponseDto> selectSendMessage(@PathVariable Long messageNo, @AuthenticationPrincipal EmployeeDto sender){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보낸 메시지 조회 성공", messageService.selectSendMessage(messageNo, sender)));
	}

	
	/* 보낸 메시지 휴지통 이동 완!!!!!!!!!!!!! */
	@PatchMapping("/send/{messageNo}")
	public ResponseEntity<ResponseDto> sendMessageToBinMessage(@PathVariable Long messageNo){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보낸 메시지 휴지통 이동 성공", messageService.sendMessageToBinMessage(messageNo)));
	}
	
	
	/* 중요 메시지함 완!!!!!!!!!! */
	@GetMapping("/impo")
	public ResponseEntity<ResponseDto> impoMessages(@RequestParam(name="page", defaultValue="1")int page, @AuthenticationPrincipal EmployeeDto recipient){
		
		Page<MessageDto> impoMessageList = messageService.receiveMessages(page, recipient);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(impoMessageList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(impoMessageList.getContent());
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "중요 메시지함 조회 성공", responseDtoWithPaging));
	}
	
	
	/* 중요 메시지 상세 조회 완!!!!!!!! */
	@PatchMapping("/impo/{messageNo}/read")
	public ResponseEntity<ResponseDto> selectImpoMessage(@PathVariable Long messageNo, @AuthenticationPrincipal EmployeeDto recipient){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "중요 메시지 조회 성공", messageService.selectImpoMessage(messageNo, recipient)));
	}
	
	

}
