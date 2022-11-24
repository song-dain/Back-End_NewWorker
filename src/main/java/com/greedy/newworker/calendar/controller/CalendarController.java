package com.greedy.newworker.calendar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.newworker.calendar.dto.CalendarDto;
import com.greedy.newworker.calendar.dto.Criteria;
import com.greedy.newworker.calendar.service.CalendarService;
import com.greedy.newworker.common.ResponseDto;
import com.greedy.newworker.employee.dto.EmployeeDto;

@RestController
@RequestMapping("/emp/calendar")
public class CalendarController {
	
	private final CalendarService calendarService;
	
	public CalendarController(CalendarService calendarService) {
		this.calendarService = calendarService;
	}
	
	/* 일정 조회 */
	@GetMapping("/schedule")
	public ResponseEntity<ResponseDto> officeCalendar(@AuthenticationPrincipal EmployeeDto employee, @RequestBody Criteria checkCategory){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 조회 완료", calendarService.officeCalendar(employee, checkCategory)));
	}
	
	/* 일정 추가 */
	@PostMapping("/schedule/add")
	public ResponseEntity<ResponseDto> addSchedule (@AuthenticationPrincipal EmployeeDto employee, @RequestBody CalendarDto schedule){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"일정 추가 완료", calendarService.addSchedule(employee, schedule)));
	}
	
	/* 일정 수정 */
	@PatchMapping("/schedule/update/{calendarNo}")
	public ResponseEntity<ResponseDto> updateSchedule (@AuthenticationPrincipal EmployeeDto employee, 
			@PathVariable  Long calendarNo, @RequestBody CalendarDto schedule){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"일정 수정 완료", calendarService.updateSchedule(employee, calendarNo, schedule)));
	}
	
	/* 일정 삭제 */
	@PatchMapping("/schedule/delete/{calendarNo}")
	public ResponseEntity<ResponseDto> deleteSchedule (@AuthenticationPrincipal EmployeeDto employee, @PathVariable Long calendarNo){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"일정 삭제 완료", calendarService.deleteSchedule(employee, calendarNo)));
	}
	
	

}
