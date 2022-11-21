package com.greedy.newworker.employee.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.common.paging.Pagenation;
import com.greedy.newworker.common.paging.PagingButtonInfo;
import com.greedy.newworker.common.paging.ResponseDtoWithPaging;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.service.ListService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/emp")
public class ListController {

	private final ListService listService;
	
	public ListController(ListService listService) {
		this.listService = listService;
	}
	
	/*직원 조회*/
	@GetMapping("/employeeList")
	public ResponseEntity<ResponseDto> selectEmployeeList(@RequestParam(name="page", defaultValue="1") int page) {
		
		log.info("[EmployeeController] selectEmployeeList Start ================================");
		log.info("[EmployeeController] page : {}", page);
		
		Page<EmployeeDto> employeeDtoList = listService.selectEmployeeList(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(employeeDtoList);
		 
		log.info("[EmployeeController] pageInfo : {}", pageInfo);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(employeeDtoList.getContent());
		
		log.info("[EmployeeController] selectEmployeeList End ================================");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
//	/* 직원상세조회(관리자) */
//	@GetMapping("/employeeList/detail-management/{employeeNo}")
//	public ResponseEntity<ResponseDto> selectEmployeeListDetail(@PathVariable Long employeeNo) {
//		
//		return ResponseEntity
//				.ok()
//				.body(new ResponseDto(HttpStatus.OK, "직원 상세 조회 성공", listService.selectEmployeeForAdmin(employeeNo)));
//	}
	
	
	
	
	
	
	
}
