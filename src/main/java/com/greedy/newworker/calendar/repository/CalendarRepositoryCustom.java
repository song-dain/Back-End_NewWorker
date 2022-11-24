package com.greedy.newworker.calendar.repository;

import java.util.List;

import com.greedy.newworker.calendar.dto.Criteria;
import com.greedy.newworker.calendar.entity.Calendar;
import com.greedy.newworker.employee.entity.Employee;

public interface CalendarRepositoryCustom {
	
	List<Calendar> scheduleFilter(Criteria criteria, Employee employee);

}
