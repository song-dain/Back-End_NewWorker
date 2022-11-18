package com.greedy.newworker.notice.controller;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greedy.newworker.common.paging.Pagenation;
import com.greedy.newworker.common.paging.PagingButtonInfo;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.notice.dto.NoticeDto;
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

	
	@GetMapping("noticeList")
	public String goNoticeList(@RequestParam(defaultValue="1") int page, Model model) {
		
		log.info("[NoticeController] ========================================= ");
		log.info("[NoticeController] param page : {}", page);
		
		Page<NoticeDto> noticeList = noticeService.selectNoticeList(page);
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(noticeList);
		
		log.info("[NoticeController] noticeList : {}", noticeList.getContent());
		log.info("[NoticeController] paging : {}", paging);
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("paging", paging);
		
		log.info("[NoticeController] ========================================= ");
		
		return "notice/noticeList";
	}
	
	
	@GetMapping("/noticeRegist")
	public String goNoticeRegist() {
		return "notice/noticeRegist";
	}
	
	@PostMapping("/noticeRegist")
	public String noticeRegist(NoticeDto notice, @AuthenticationPrincipal EmployeeDto employee, RedirectAttributes rttr) {
		
		log.info("[NoticeController] ========================================= ");
		log.info("[NoticeController] noticeRegist request : {}", notice);
		
		log.info("[EmployeeController] EmployeeRegist request : {}", employee);
		
		notice.setEmployee(employee);	
		noticeService.noticeRegist(notice);
		
		rttr.addFlashAttribute("message", messageSourceAccesor.getMessage("notice.regist"));
		
		log.info("[NoticeController] ========================================= ");
		
		return "redirect:/notice/noticeList";
	}
	
	
	@GetMapping("/noticeDetail")
	public String goNoticeDetail(@AuthenticationPrincipal EmployeeDto employee, Model model, @RequestParam Long notNo) {
		
		// 1. 요청 게시글 번호를 parameter로 전달 받는다
		// 2. 요청 게시글 번호 기준으로 NoticeDTO 하나의 객체를 조회해온다.
		// 3. 로그인 유저의 role에 따라 다른 응답 화면으로 응답한다.
		
		log.info("[NoticeController] ========================================= ");
		
		NoticeDto notice = noticeService.seleNoticeDetail(notNo);
		
		log.info("[NoticeController] notice : {}", notice);
		
		model.addAttribute("notice", notice);
		
		log.info("[NoticeController] ========================================= ");
		log.info("[EmployeeController] EmployeeRegist request : {}", employee);
		
		if(employee != null && employee.getEmployeeRole().equals("ROLE_ADMIN")) {
			
			return "notice/noticeModify";
		}
		
		return "notice/noticeDetail";
	}
	
	
	@GetMapping("/noticeModify")
	public String goNoticeModify() {
		
		return "notice/noticeModify";
	}
	
	@PostMapping("/noticeModify")
	public String noticeModify(@ModelAttribute NoticeDto noticeModify, RedirectAttributes rttr) {
		
		log.info("[EmployeeController] modifyEmployee request Employee : {}", noticeModify);
		
		noticeService.modifyEmployee(noticeModify);
		
		rttr.addFlashAttribute("message", messageSourceAccesor.getMessage("notice.modify"));
		
		log.info("[EmployeeController] modifyEmployee ==============================");
		
		return "redirect:/notice/noticeList";
	}
	
	
	@PostMapping("/noticeDelete")
	public String noticeDelete(Long notNo, RedirectAttributes rttr) {
		
		log.info("[NoticeController] noticeDelete ==============================");
		log.info("[NoticeController] notice : " + notNo);
		
		noticeService.deleteNot(notNo);
		
		rttr.addFlashAttribute("message", messageSourceAccesor.getMessage("notice.delete"));
		
		log.info("[NoticeController] noticeDelete ==============================");
		
		return "redirect:/notice/noticeList";
	}
	
	// 디테일 css

}
