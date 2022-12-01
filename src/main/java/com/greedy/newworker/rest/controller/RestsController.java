package com.greedy.newworker.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.common.paging.Pagenation;
import com.greedy.newworker.common.paging.PagingButtonInfo;
import com.greedy.newworker.common.paging.ResponseDtoWithPaging;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.rest.dto.RestDto;
import com.greedy.newworker.rest.service.RestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rest")
public class RestsController {
	
	private final RestService restService;
	
	public RestsController(RestService restService) {
		this.restService = restService;
	}
	
	
	/* 조회 */
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> selectRestListWithPaging(@RequestParam(name="page", defaultValue="1") int page) {
		
        Page <RestDto> restDtoList = restService.selectRestList(page);
        
        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(restDtoList);
        
               
        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(restDtoList.getContent());
		
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));	
	}

	/* 등록 */
	@PostMapping("/regist")
	public ResponseEntity<ResponseDto> insertRest(@ModelAttribute RestDto restDto,
			@AuthenticationPrincipal EmployeeDto employee) {
		
		restDto.setEmployeeNo(employee);
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "휴가 등록 성공", restService.insertRest(restDto, employee)));
	}
	
	/* 수정 */
	@PutMapping("/regist")
	public ResponseEntity<ResponseDto> updateRest(@ModelAttribute RestDto restDto,
			@AuthenticationPrincipal EmployeeDto employee) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "휴가 수정 성공", restService.updateRest(restDto)));
	}
	
	/* 상세조회 */
	@GetMapping("/list/detail/{restNo}")
	public ResponseEntity<ResponseDto> selectRestDetail(@PathVariable Long restNo) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", restService.selectRestDetail(restNo)));	
	}
	
	/* 연차인가 페이지 */
	@GetMapping("/list/admin")
	public ResponseEntity<ResponseDto> selectRestListAdmin(@RequestParam(name="page", defaultValue="1") int page) {
	
		Page<RestDto> restDtoListAdmin = restService.selectRestListAdmin(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(restDtoListAdmin);
		 
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(restDtoListAdmin.getContent());
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/* 연차인가 상세페이지 */
	@GetMapping("/list/admin/detail/{restNo}")
	public ResponseEntity<ResponseDto> selectRestDetailAdmin(@PathVariable Long restNo) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", restService.selectRestDetailAdmin(restNo)));	
	}
	
	
	
	/* 연차 승인, 반려 */	
	@PutMapping("/list/admin/ok")
	public ResponseEntity<ResponseDto> updateRestOk(@ModelAttribute RestDto restDto) {		
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "완료되었습니다.", restService.updateRestOk(restDto)));
	}
	
}
