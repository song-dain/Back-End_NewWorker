package com.greedy.newworker.calendar.dto;

import com.greedy.newworker.employee.dto.DepartmentDto;
import com.greedy.newworker.employee.dto.EmployeeDto;

import lombok.Data;

@Data
public class CalendarDto {

	private Long calendarNo;
	private CalendarCategoryDto calendarCategory;
	private EmployeeDto employee;
	private DepartmentDto dep;
	private String scheduleTitle;
	private java.sql.Date startDate;
	private java.sql.Date endDate;
	private String scheduleLocation;
	private String scheduleContent;
	private String scheduleDelete;
	
}
