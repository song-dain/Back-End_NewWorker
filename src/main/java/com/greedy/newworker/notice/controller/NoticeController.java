package com.greedy.newworker.notice.controller;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.common.paging.Pagenation;
import com.greedy.newworker.common.paging.PagingButtonInfo;
import com.greedy.newworker.common.paging.ResponseDtoWithPaging;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.notice.dto.NoticeDto;
import com.greedy.newworker.notice.entity.Notice;
import com.greedy.newworker.notice.service.NoticeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	private final NoticeService noticeService;
	private final MessageSourceAccessor messageSourceAccesor;
	
	
	public NoticeController(NoticeService noticeService, MessageSourceAccessor messageSourceAccesor) {
		this.noticeService = noticeService;
		this.messageSourceAccesor = messageSourceAccesor;
	}
	
	/* 1. 공지사항 목록 조회 (페이징) */
	@GetMapping("/noticeList")
	public ResponseEntity<ResponseDto> selectNoticeListWithPaging(@RequestParam(name="page", defaultValue="1") int page) {
	
		log.info("[NoticeController] selectReviewListWithPaging Start ===================================");
        log.info("[NoticeController] selectReviewListWithPaging : " + page);
      
        Page <NoticeDto> noticeDtoList = noticeService.selectNoticeListWithPaging(page);
        
        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(noticeDtoList);
        
        log.info("[ReviewController] reviewDtoList : " + noticeDtoList.getContent());
        log.info("[ReviewController] pageInfo : " + pageInfo);
        
        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(noticeDtoList.getContent());
		
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	
	}
	
	/* 2. 상품 상세 조회 - noticeNo로 공지 1개 조회(사원) */
	@GetMapping("/noticeDetail/{notNo}")
	public ResponseEntity<ResponseDto> seleNoticeDetail(@PathVariable Long notNo) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "조회 성공", noticeService.selectNotice(notNo)));
		
	}
	
	/* 3. 공지 등록 */
    @PostMapping("/notices/register")
    public ResponseEntity<ResponseDto> insertNotice(@ModelAttribute NoticeDto noticeDto,
    		@AuthenticationPrincipal EmployeeDto employee) {
    	
    	noticeDto.setEmployee(employee);
    	
    	return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "공지 등록 성공", noticeService.insertNotice(noticeDto)));
    	
    }
    
    /* 4. 공지 수정 */
	@PutMapping("/notices/register")
	public ResponseEntity<ResponseDto> updateNotice(@ModelAttribute NoticeDto noticeDto) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "공지 수정 성공", noticeService.updateNotice(noticeDto)));
		
	}
	
	/* 5.공지 삭제 */
	@DeleteMapping("/noticeDetail/delete/{notNo}")
	public ResponseEntity<ResponseDto> removeNotice(@ModelAttribute NoticeDto noticeDto,
			@PathVariable("notNo") Long notNo) {
				
		noticeService.deleteNotice(notNo);
				 
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "공지 삭제 완료", null));
	}
	
	
}

	
