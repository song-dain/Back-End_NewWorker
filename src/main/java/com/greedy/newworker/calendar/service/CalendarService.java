package com.greedy.newworker.calendar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.newworker.calendar.dto.CalendarCategoryDto;
import com.greedy.newworker.calendar.dto.CalendarDto;
import com.greedy.newworker.calendar.dto.Criteria;
import com.greedy.newworker.calendar.entity.Calendar;
import com.greedy.newworker.calendar.entity.CalendarCategory;
import com.greedy.newworker.calendar.repository.CalendarCategoryRepository;
import com.greedy.newworker.calendar.repository.CalendarRepository;
import com.greedy.newworker.calendar.repository.CalendarRepositoryCustom;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.EmployeeRepository;
import com.greedy.newworker.rest.dto.RestDto;
import com.greedy.newworker.rest.entity.Rest;
import com.greedy.newworker.rest.repository.RestRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CalendarService {

	private final CalendarRepository calendarRepository;
	private final CalendarCategoryRepository categoryRepository;
	private final CalendarRepositoryCustom calendarRepositoryCustom;
	private final EmployeeRepository employeeRepository;
	private final RestRepository restRepository;
	private final ModelMapper modelMapper;

	public CalendarService(CalendarRepository calendarRepository, CalendarCategoryRepository categoryRepository, 
			CalendarRepositoryCustom calendarRepositoryCustom, 
			EmployeeRepository employeeRepository, RestRepository restRepository, ModelMapper modelMapper) {
		
		this.calendarRepository = calendarRepository;
		this.categoryRepository = categoryRepository;
		this.calendarRepositoryCustom = calendarRepositoryCustom;
		this.employeeRepository = employeeRepository;
		this.restRepository = restRepository;
		this.modelMapper = modelMapper;
	}

	
	/* 일정 조회 */
	public Map<String, List> officeCalendar(EmployeeDto employee, Criteria criteria) {
		
		log.info("[cs]criteria : {}", criteria);
		
		Map<String, List> calendarMap = new HashMap<>();
		
		List<Calendar> scheduleList = calendarRepositoryCustom.scheduleFilter(criteria, modelMapper.map(employee, Employee.class));
		calendarMap.put("scheduleList", scheduleList.stream().map(schedule -> modelMapper.map(schedule, CalendarDto.class)).toList());
		
		if(criteria.getDayOff().equals("연차")) {
			List<Rest> dayOffList = restRepository.findByEmployeeNoAndRestOk(modelMapper.map(employee, Employee.class), "Y");
			
			if(dayOffList.size() != 0) {
				calendarMap.put("dayOffList", dayOffList.stream().map(dayOff -> modelMapper.map(dayOff, Rest.class)).toList());
			}
		}
		
		log.info("[cs] calendarMap : {}", calendarMap.get("dayOffList"));
		
		return calendarMap;

	}
	
	
	/* 일정 상세 조회 */
	public CalendarDto scheduleDetail(Long scheduleNo, EmployeeDto employee) {
		
		Calendar findSchedule = calendarRepository.findByCalendarNoAndEmployee(scheduleNo, employee.getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));
		
		return modelMapper.map(findSchedule, CalendarDto.class);
	}

	
	/* 일정 추가 */
	public CalendarDto addSchedule(EmployeeDto employee, CalendarDto schedule) {
		
		CalendarCategory category = categoryRepository.findByCalendarCategoryName(schedule.getCalendarCategory().getCalendarCategoryName());

		Employee emp = employeeRepository.findById(employee.getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));

		EmployeeDto empDto = modelMapper.map(emp, EmployeeDto.class);
		
		
		schedule.setCalendarCategory(modelMapper.map(category, CalendarCategoryDto.class));
		schedule.setEmployee(empDto);
		schedule.setDep(empDto.getDep());

		calendarRepository.save(modelMapper.map(schedule, Calendar.class));

		return schedule;
	}

	
	/* 일정 수정 */
	public CalendarDto updateSchedule(EmployeeDto employee, Long calendarNo, CalendarDto schedule) {

		Calendar updateSchedule = calendarRepository.findByCalendarNoAndEmployee(calendarNo, employee.getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));
		
		CalendarCategory category = categoryRepository.findByCalendarCategoryName(schedule.getCalendarCategory().getCalendarCategoryName());

		log.info("[sc] schedule : {}", schedule);
		
		updateSchedule.setCalendarNo(calendarNo);
		updateSchedule.setCalendarCategory(category);
		updateSchedule.setScheduleTitle(schedule.getScheduleTitle());
		updateSchedule.setStartDate(schedule.getStartDate());
		updateSchedule.setEndDate(schedule.getEndDate());
		updateSchedule.setStartTime(schedule.getStartTime());
		updateSchedule.setScheduleLocation(schedule.getScheduleLocation());
		updateSchedule.setScheduleContent(schedule.getScheduleContent());

		calendarRepository.save(updateSchedule);

		return schedule;
	}

	
	/* 일정 삭제 */
	public CalendarDto deleteSchedule(EmployeeDto employee, Long calendarNo) {

		Calendar deleteSchedule = calendarRepository.findByCalendarNoAndEmployee(calendarNo, employee.getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));

		deleteSchedule.setScheduleDelete("Y");

		return modelMapper.map(deleteSchedule, CalendarDto.class);
	}
	
	
	/* [메인] 오늘 일정 조회 */
	public Object todaySchedule(EmployeeDto employee) {
		
		List<Calendar> scheduleList = calendarRepository.findByEmployeeNoAndStartDate(employee.getEmployeeNo());
		List<CalendarDto> todaySchedule = scheduleList.stream().map(schedule -> (modelMapper.map(schedule, CalendarDto.class))).toList();
		
		List<Rest> dayOffList = restRepository.findByEmployeeNoAndRestOkAndRestFdate(employee.getEmployeeNo());
		List<RestDto> todayDayOff = dayOffList.stream().map(dayoff -> (modelMapper.map(dayoff, RestDto.class))).toList();
		
		Map<String, List> calendarMap = new HashMap<>();
		
		calendarMap.put("todaySchedule", todaySchedule);
		calendarMap.put("todayDayOff", todayDayOff);
		
		return calendarMap;
	}

	
}
