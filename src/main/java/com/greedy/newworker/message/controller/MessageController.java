package com.greedy.newworker.message.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.common.paging.Pagenation;
import com.greedy.newworker.common.paging.PagingButtonInfo;
import com.greedy.newworker.common.paging.ResponseDtoWithPaging;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.message.dto.MessageDto;
import com.greedy.newworker.message.dto.RecipientManagementDto;
import com.greedy.newworker.message.dto.SenderManagementDto;
import com.greedy.newworker.message.service.MessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/emp/message")
public class MessageController {

	private final MessageService messageService;

	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	/* 메시지 보내기 */
	@PostMapping("/send")
	public ResponseEntity<ResponseDto> newMessage(@RequestBody MessageDto newMessage,
			@AuthenticationPrincipal EmployeeDto sender, Long recipientNo) {

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "메시지 전송 성공",
				messageService.newMessage(newMessage, sender, recipientNo)));
	}

	/* 받은 메시지함 완!!!!!! */
	@GetMapping("/receive")
	public ResponseEntity<ResponseDto> receiveMessages(@RequestParam(name = "page", defaultValue = "1") int page,
			@AuthenticationPrincipal EmployeeDto recipient) {

		Page<MessageDto> receiveMessageList = messageService.receiveMessages(page, recipient);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(receiveMessageList);

		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(receiveMessageList.getContent());

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "받은 메시지함 조회 성공", responseDtoWithPaging));
	}

	/* 받은 메시지 상세 조회 완!!!!!! */
	@PatchMapping("/receive/{messageNo}/read")
	public ResponseEntity<ResponseDto> selectReceiveMessage(@PathVariable Long messageNo,
			@AuthenticationPrincipal EmployeeDto recipient) {

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "받은 메시지 조회 성공",
				messageService.selectReceiveMessage(messageNo, recipient)));
	}

	/* 보낸 메시지함 완!!!!!!!!!! */
	@GetMapping("/send")
	public ResponseEntity<ResponseDto> sendMessages(@RequestParam(name = "page", defaultValue = "1") int page,
			@AuthenticationPrincipal EmployeeDto sender) {

		Page<MessageDto> sendMessageList = messageService.sendMessages(page, sender);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(sendMessageList);

		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(sendMessageList.getContent());

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보낸 메시지함 조회 성공", responseDtoWithPaging));
	}

	/* 보낸 메시지 상세 조회 완!!!!!!!!!!! */
	@GetMapping("/send/{messageNo}/read")
	public ResponseEntity<ResponseDto> selectSendMessage(@PathVariable Long messageNo,
			@AuthenticationPrincipal EmployeeDto sender) {

		return ResponseEntity.ok().body(
				new ResponseDto(HttpStatus.OK, "보낸 메시지 조회 성공", messageService.selectSendMessage(messageNo, sender)));
	}


	/* 중요 메시지함 완!!!!!!!!!! */
	@GetMapping("/impo")
	public ResponseEntity<ResponseDto> impoMessages(@RequestParam(name = "page", defaultValue = "1") int page,
			@AuthenticationPrincipal EmployeeDto recipient) {

		Page<MessageDto> impoMessageList = messageService.receiveMessages(page, recipient);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(impoMessageList);

		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(impoMessageList.getContent());

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "중요 메시지함 조회 성공", responseDtoWithPaging));
	}

	/* 중요 메시지 상세 조회 완!!!!!!!! */
	@PatchMapping("/impo/{messageNo}/read")
	public ResponseEntity<ResponseDto> selectImpoMessage(@PathVariable Long messageNo,
			@AuthenticationPrincipal EmployeeDto recipient) {

		return ResponseEntity.ok().body(
				new ResponseDto(HttpStatus.OK, "중요 메시지 조회 성공", messageService.selectImpoMessage(messageNo, recipient)));
	}

	/* 휴지통 받은 메시지 조회 완!!!! */
	@GetMapping("/bin/receive")
	public ResponseEntity<ResponseDto> binReceiveMessages(@RequestParam(name = "page", defaultValue = "1") int page,
			@AuthenticationPrincipal EmployeeDto recipient) {

		Page<MessageDto> binReceiveMessageList = messageService.binReceiveMessages(page, recipient);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(binReceiveMessageList);

		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(binReceiveMessageList.getContent());

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "휴지통 받은 메시지함 조회 성공", responseDtoWithPaging));
	}

	/* 휴지통 보낸 메시지 조회 완!!!!! */
	@GetMapping("/bin/send")
	public ResponseEntity<ResponseDto> binSendMessages(@RequestParam(name = "page", defaultValue = "1") int page,
			@AuthenticationPrincipal EmployeeDto sender) {

		Page<MessageDto> binSendMessages = messageService.binSendMessages(page, sender);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(binSendMessages);

		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(binSendMessages.getContent());

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "휴지통 보낸 메시지함 조회 성공", responseDtoWithPaging));
	}
	

	/* 받은 메시지(빋은 메시지함, 중요 메시지, 휴지통 받은 메시지) 관리 */
	@PatchMapping("/receive/{messageNo}")
	public ResponseEntity<ResponseDto> receiveMessageManagement(@PathVariable Long messageNo,
			@RequestBody RecipientManagementDto messageRequest) {
		log.info("[MessageController] messageNo : {}", messageNo);
		log.info("[MessageController] messageRequest : {}", messageRequest);

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "받은 메시지 이동 성공",
				messageService.receiveMessageManagement(messageNo, messageRequest)));
	}
	
	
	/* 보낸 메시지(보낸 메시지함, 휴지통 관리) */
	@PatchMapping("/send/{messageNo}")
	public ResponseEntity<ResponseDto> sendMessageManagement(@PathVariable Long messageNo, 
			@RequestBody SenderManagementDto messageRequest) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보낸 메시지 이동 성공",
				messageService.sendMessageManagement(messageNo, messageRequest)));
		
	}
	
	

}
