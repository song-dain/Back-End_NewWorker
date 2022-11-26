package com.greedy.newworker.calendar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.newworker.calendar.dto.CalendarDto;
import com.greedy.newworker.calendar.dto.Criteria;
import com.greedy.newworker.calendar.entity.Calendar;
import com.greedy.newworker.calendar.repository.CalendarRepository;
import com.greedy.newworker.calendar.repository.CalendarRepositoryCustom;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.repository.EmployeeRepository;
import com.greedy.newworker.rest.entity.Rest;
import com.greedy.newworker.rest.repository.RestRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CalendarService {

	private final CalendarRepository calendarRepository;
	private final CalendarRepositoryCustom calendarRepositoryCustom;
	private final EmployeeRepository employeeRepository;
	private final RestRepository restRepository;
	private final ModelMapper modelMapper;

	public CalendarService(CalendarRepository calendarRepository, CalendarRepositoryCustom calendarRepositoryCustom,
			EmployeeRepository employeeRepository, RestRepository restRepository, ModelMapper modelMapper) {
		this.calendarRepository = calendarRepository;
		this.calendarRepositoryCustom = calendarRepositoryCustom;
		this.employeeRepository = employeeRepository;
		this.restRepository = restRepository;
		this.modelMapper = modelMapper;
	}

	/* 일정 조회 */
	public Map<String, List<Object>> officeCalendar(EmployeeDto employee, Criteria criteria) {
		
		log.info("[cs]criteria : {}", criteria);
		
		Map calendarMap = new HashMap();
		
		List<Calendar> scheduleList = calendarRepositoryCustom.scheduleFilter(criteria, modelMapper.map(employee, Employee.class));
		calendarMap.put("scheduleList", scheduleList.stream().map(schedule -> modelMapper.map(schedule, CalendarDto.class)).toList());
		
		if(criteria.getDayOff().equals("dayOff")) {
			List<Rest> dayOffList = restRepository.findByEmployeeNoAndRestOk(modelMapper.map(employee, Employee.class), "Y");
			
			if(dayOffList.size() != 0) {
				calendarMap.put("dayOffList", dayOffList.stream().map(dayOff -> modelMapper.map(dayOff, Rest.class)).toList());
			}
		}
		
		log.info("[cs] calendarMap : {}", calendarMap);
		
		return calendarMap;
//		return null;

	}
	
	public CalendarDto scheduleDetail(Long scheduleNo, EmployeeDto employee) {
		
		Calendar findSchedule = calendarRepository.findByCalendarNoAndEmployee(scheduleNo, employee.getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));
		
		return modelMapper.map(findSchedule, CalendarDto.class);
	}

	/* 일정 추가 */
	public CalendarDto addSchedule(EmployeeDto employee, CalendarDto schedule) {

		Employee emp = employeeRepository.findById(employee.getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));

		EmployeeDto empDto = modelMapper.map(emp, EmployeeDto.class);

		schedule.setEmployee(empDto);
		schedule.setDep(empDto.getDep());

		calendarRepository.save(modelMapper.map(schedule, Calendar.class));

		return schedule;
	}

	/* 일정 수정 */
	public CalendarDto updateSchedule(EmployeeDto employee, Long calendarNo, CalendarDto schedule) {

		Calendar updateSchedule = calendarRepository.findByCalendarNoAndEmployee(calendarNo, employee.getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));

		updateSchedule.getCalendarCategory()
				.setCalendarCategoryNo(schedule.getCalendarCategory().getCalendarCategoryNo());
		updateSchedule.getCalendarCategory()
				.setCalendarCategoryName(schedule.getCalendarCategory().getCalendarCategoryName());
		updateSchedule.setScheduleTitle(schedule.getScheduleTitle());
		updateSchedule.setStartDate(schedule.getStartDate());
		updateSchedule.setEndDate(schedule.getEndDate());
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

}
