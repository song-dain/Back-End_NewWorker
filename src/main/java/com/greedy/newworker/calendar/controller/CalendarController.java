package com.greedy.newworker.calendar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.calendar.dto.CalendarDto;
import com.greedy.newworker.calendar.dto.Criteria;
import com.greedy.newworker.calendar.service.CalendarService;
import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.employee.dto.EmployeeDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/emp/calendar")
public class CalendarController {
	
	private final CalendarService calendarService;
	
	public CalendarController(CalendarService calendarService) {
		this.calendarService = calendarService;
	}
	
	/* 일정 조회 */
	@PostMapping("/schedule")
	public ResponseEntity<ResponseDto> selectOfficeCalendar(@AuthenticationPrincipal EmployeeDto employee, @RequestBody Criteria criteria){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 조회 완료", calendarService.officeCalendar(employee, criteria)));
	}
	
	/* 일정 상세 조회 */
	@GetMapping("/schedule/{scheduleNo}")
	public ResponseEntity<ResponseDto> scheduleDetail(@PathVariable Long scheduleNo, @AuthenticationPrincipal EmployeeDto employee){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 상세 조회 완료", calendarService.scheduleDetail(scheduleNo, employee)));
	}
	
	/* 일정 추가 */
	@PostMapping("/schedule/add")
	public ResponseEntity<ResponseDto> addSchedule (@AuthenticationPrincipal EmployeeDto employee, @RequestBody CalendarDto schedule){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"일정 추가 완료", calendarService.addSchedule(employee, schedule)));
	}
	
	/* 일정 수정 */
	@PutMapping("/schedule/update/{scheduleNo}")
	public ResponseEntity<ResponseDto> updateSchedule (@AuthenticationPrincipal EmployeeDto employee, 
			@PathVariable Long scheduleNo, @RequestBody CalendarDto schedule){
		
		log.info("[calendar] schedule : {}", schedule);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"일정 수정 완료", calendarService.updateSchedule(employee, scheduleNo, schedule)));
	}
	
	/* 일정 삭제 */
	@PatchMapping("/schedule/delete/{scheduleNo}")
	public ResponseEntity<ResponseDto> deleteSchedule (@AuthenticationPrincipal EmployeeDto employee, @PathVariable Long scheduleNo){
		
		log.info("[cc] scheduleNo : {}", scheduleNo);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"일정 삭제 완료", calendarService.deleteSchedule(employee, scheduleNo)));
	}
	

}
