package com.greedy.newworker.survey.controller;

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
import com.greedy.newworker.employee.dto.DepartmentDto;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.notice.dto.NoticeDto;
import com.greedy.newworker.notice.entity.Notice;
import com.greedy.newworker.notice.service.NoticeService;
import com.greedy.newworker.survey.dto.QuestionItemDto;
import com.greedy.newworker.survey.dto.SurveyDto;
import com.greedy.newworker.survey.service.SurveyService;

import lombok.extern.slf4j.Slf4j;
/* 설문조사 컨트롤러*/
@Slf4j
@Controller
@RequestMapping("/survey")
public class SurveyController {
	
	private final SurveyService surveyService;
	private final MessageSourceAccessor messageSourceAccesor;
	
	
	public SurveyController(SurveyService surveyService, MessageSourceAccessor messageSourceAccesor) {
		this.surveyService = surveyService;
		this.messageSourceAccesor = messageSourceAccesor;
	}
	
	/* 1. 설문게시판 목록 조회 (페이징) */
	@GetMapping("/surveyList")
	public ResponseEntity<ResponseDto> selectSurveyListWithPaging(@RequestParam(name="page", defaultValue="1") int page) {
	
		log.info("[SurveyController] selectSurveyListWithPaging Start ===================================");
        log.info("[SurveyController] selectSurveyListWithPaging : " + page);
      
        Page <SurveyDto> surveyDtoList = surveyService.selectSurveyListWithPaging(page);
        
        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(surveyDtoList);
        
        log.info("[SurveyController] surveyDtoList : " + surveyDtoList.getContent());
        log.info("[SurveyController] pageInfo : " + pageInfo);
        
        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(surveyDtoList.getContent());
		
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	
	}
	
	/* 2. 설문 상세 조회 - surNo로 공지 1개 조회(사원) */
	@GetMapping("/surveyDetail/{surNo}")
	public ResponseEntity<ResponseDto> seleNoticeDetail(@PathVariable Long surNo) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "조회 성공", surveyService.selectSurvey(surNo)));
		
	}
	
	/* 3. 설문 등록 */
    @PostMapping("/survey/register")
    public ResponseEntity<ResponseDto> insertSurvey(@ModelAttribute SurveyDto surveyDto,
    		@AuthenticationPrincipal EmployeeDto employee) {
    	
    	surveyDto.setEmployee(employee);
    	log.info("[SurveyDto] surveyDto : " + surveyDto);
    	
    	
    	return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "설문 등록 성공", surveyService.insertSurvey(surveyDto)));
    	
    }
    
    /* 3. 설문 제출 */
    @PostMapping("/survey/submit")
    public ResponseEntity<ResponseDto> insertSurvey1(@ModelAttribute SurveyDto surveyDto,
    		@AuthenticationPrincipal EmployeeDto employee) {
    	
    	surveyDto.setEmployee(employee);
    	log.info("[SurveyDto] surveyDto : " + surveyDto);
    	
    	
    	return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "설문 제출 성공", surveyService.insertSurvey1(surveyDto)));
    	
    }
    
    
    
    /* 4. 설문 수정 */
	@PutMapping("/survey/register")
	public ResponseEntity<ResponseDto> updateSurvey(@ModelAttribute SurveyDto surveyDto) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "공지 수정 성공", surveyService.updateSurvey(surveyDto)));
		
	}
	
	/* 5. 설문 삭제 */
	@DeleteMapping("/surveyDetail/delete/{surNo}")
	public ResponseEntity<ResponseDto> removeSurvey(@ModelAttribute SurveyDto surveyDto,
			@PathVariable("surNo") Long surNo) {
				
		surveyService.deleteSurvey(surNo);
				 
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "공지 삭제 완료", null));
	}
	
	
}

	
