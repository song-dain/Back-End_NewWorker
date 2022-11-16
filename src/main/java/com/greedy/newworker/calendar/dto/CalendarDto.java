package com.greedy.newworker.calendar.dto;

import lombok.Data;

@Data
public class CalendarDto {

	private Long calendarNo;
	private CalendarCategoryDto calendarCategory;
	private EmployeeDto employee;
	private DepDto dep;
	private String scheduleTitle;
	private java.sql.Date startDate;
	private java.sql.Date endDate;
	private String scheduleLocation;
	private String scheduleContent;
	private String scheduleDelete;
	
}
