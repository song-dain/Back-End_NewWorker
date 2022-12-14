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

@RestController
@RequestMapping("/emp/message")
public class MessageController {

	private final MessageService messageService;
	
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	
	/* 부서별 직원 조회 */
	@GetMapping("/send/findEmp/{depNo}")
	public ResponseEntity<ResponseDto> findRecipient(@PathVariable Long depNo, @AuthenticationPrincipal EmployeeDto employee){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "부서별 직원 조회 성공", messageService.findRecipient(depNo, employee)));
	}

	
	/* 메시지 전송 */
	@PostMapping("/send")
	public ResponseEntity<ResponseDto> newMessage(@RequestBody MessageDto newMessage, @AuthenticationPrincipal EmployeeDto sender) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "메시지 전송 성공",
				messageService.newMessage(newMessage, sender)));
	}

	
	/* 받은 메시지함 */
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
	
	/* 받은 메시지 읽기 */
	@PatchMapping("/read/{messageNo}")
	public ResponseEntity<ResponseDto> messageRead(@PathVariable("messageNo") Long messageNo){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "받은 메시지 읽음 수정 성공", messageService.messageRead(messageNo)));
	}
	
	
	/* 받은 메시지 검색 */
	@GetMapping("/receive/search")
	public ResponseEntity<ResponseDto> searchReceiveMessage(@RequestParam(name = "page", defaultValue = "1") int page, 
			@RequestParam(name="keyword") String keyword, @AuthenticationPrincipal EmployeeDto recipient){
		
		Page<MessageDto> searchReceiveMessage = messageService.searchReceiveMessage(page, keyword, recipient);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(searchReceiveMessage);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(searchReceiveMessage.getContent());
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "받은 메시지 검색 성공", responseDtoWithPaging));
	}

	
	/* 보낸 메시지함 */
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

	
	/* 보낸 메시지 검색 */
	@GetMapping("/send/search")
	public ResponseEntity<ResponseDto> searchSendMessage(@RequestParam(name = "page", defaultValue = "1") int page, 
			@RequestParam(name="keyword") String keyword, @AuthenticationPrincipal EmployeeDto sender){
		
		Page<MessageDto> searchSendMessage = messageService.searchSendMessage(page, keyword, sender);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(searchSendMessage);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(searchSendMessage.getContent());
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보낸 메시지 검색 성공", responseDtoWithPaging));
	}
	
	
	/* 보낸 메시지 전송 취소 */
	@PatchMapping("/cancel/{messageNo}")
	public ResponseEntity<ResponseDto> sendCancel(@PathVariable("messageNo") Long messageNo, @AuthenticationPrincipal EmployeeDto sender) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보낸 메시지 전송 취소 성공", messageService.sendCancel(messageNo, sender)));
	}

	
	/* 중요 메시지함 */
	@GetMapping("/impo")
	public ResponseEntity<ResponseDto> impoMessages(@RequestParam(name = "page", defaultValue = "1") int page,
			@AuthenticationPrincipal EmployeeDto recipient) {

		Page<MessageDto> impoMessageList = messageService.impoMessages(page, recipient);

		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(impoMessageList);

		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(impoMessageList.getContent());

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "중요 메시지함 조회 성공", responseDtoWithPaging));
	}

	
	/* 중요 메시지 검색 */
	@GetMapping("/impo/search")
	public ResponseEntity<ResponseDto> searchImpoMessage(@RequestParam(name = "page", defaultValue = "1") int page, 
			@RequestParam(name="keyword") String keyword, @AuthenticationPrincipal EmployeeDto recipient){
		
		Page<MessageDto> searchImpoMessage = messageService.searchImpoMessage(page, keyword, recipient);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(searchImpoMessage);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(searchImpoMessage.getContent());
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "중요 메시지 검색 성공", responseDtoWithPaging));
	}

	
	/* 휴지통 받은 메시지 조회 */
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

	
	/* 휴지통 보낸 메시지 조회 */
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
	@PatchMapping("/receive")
	public ResponseEntity<ResponseDto> receiveMessageManagement(@RequestBody RecipientManagementDto messageRequest) {

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "받은 메시지 이동 성공", messageService.receiveMessageManagement(messageRequest)));
	}
	
	/* 보낸 메시지(보낸 메시지함, 휴지통 관리) */
	@PatchMapping("/send/{messageNo}")
	public ResponseEntity<ResponseDto> sendMessageManagement(@RequestBody SenderManagementDto messageRequest) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보낸 메시지 이동 성공", messageService.sendMessageManagement(messageRequest)));
		
	}
	
	
	/* [main] 안 읽은 메시지 개수 */
	@GetMapping("/unread")
	public ResponseEntity<ResponseDto> unreadMessage(@AuthenticationPrincipal EmployeeDto emp){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "안 읽은 메시지 건수 조회 성공", messageService.unreadMessage(emp)));
	}
	
	
}
