//package com.greedy.newworker.rest.controller;
//
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.greedy.newworker.common.ResponseDto;
//import com.greedy.newworker.common.paging.Pagenation;
//import com.greedy.newworker.common.paging.PagingButtonInfo;
//import com.greedy.newworker.common.paging.ResponseDtoWithPaging;
//import com.greedy.newworker.rest.dto.RestDto;
//import com.greedy.newworker.rest.service.RestService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestController
//@RequestMapping("/rest")
//public class RestsController {
//	
//	private final RestService restService;
//	
//	public RestsController(RestService restService) {
//		this.restService = restService;
//	}
//	
//	/* 조회 */
//	@GetMapping("/list")
//	public ResponseEntity<ResponseDto> selectRestListWithPaging(@PathVariable Long restNo, 
//			@RequestParam(name="page", defaultValue="1") int page) {
//		
//        Page <RestDto> restDtoList = restService.selectRestListWithPaging(page, restNo);
//        
//        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(restDtoList);
//        
//               
//        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
//        responseDtoWithPaging.setPageInfo(pageInfo);
//        responseDtoWithPaging.setData(restDtoList.getContent());
//		
//        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));	
//	}
//
//	/* 등록 */
//	@PostMapping("/regist")
//	public ResponseEntity<ResponseDto> insertRest(@RequestBody RestDto restDto) {
//		
//		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "휴가 등록 성공", restService.insertRest(restDto)));
//	}
//}
