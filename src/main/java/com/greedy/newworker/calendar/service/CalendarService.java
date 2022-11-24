package com.greedy.newworker.calendar.service;

import java.util.List;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CalendarService {

	private final CalendarRepository calendarRepository;
	private final CalendarRepositoryCustom calendarRepositoryCustom;
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;

	public CalendarService(CalendarRepository calendarRepository, CalendarRepositoryCustom calendarRepositoryCustom,
			EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.calendarRepository = calendarRepository;
		this.calendarRepositoryCustom = calendarRepositoryCustom;
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}

	/* 일정 조회 */
	public List<CalendarDto> officeCalendar(EmployeeDto employee, Criteria criteria) {
		
		List<Calendar> scheduleList = calendarRepositoryCustom.scheduleFilter(criteria, modelMapper.map(employee, Employee.class));
		
		log.info("[cs] scheduleList : {}", scheduleList);
		
		return scheduleList.stream().map(schedule -> modelMapper.map(schedule, CalendarDto.class)).toList();

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
