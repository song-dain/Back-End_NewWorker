package com.greedy.newworker.calendar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.greedy.newworker.calendar.dto.CalendarCategoryDto;
import com.greedy.newworker.calendar.dto.DepDto;
import com.greedy.newworker.calendar.dto.EmployeeDto;
import com.greedy.newworker.message.entity.Employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_CALENDAR")
@SequenceGenerator(name = "CALENDAR_SEQ_GENERATOR", sequenceName = "SEQ_CALENDAR_NO", initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Calendar {
	
	@Id
	@Column(name = "CALENDAR_NO" )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALENDAR_SEQ_GENERATOR")
	private Long calendarNo;
	
	@Column(name = "CALENDAR_CATEGORY_NO")
	private CalendarCategoryDto calendarCategory;
	
	@Column(name = "EMPLOYEE_NO")
	private Employee employee;
	
	@Column(name = "DEP_NO")
	private Dep dep;
	
	@Column(name = "SCHEDULE_TITLE")
	private String scheduleTitle;
	
	@Column(name = "START_DATE")
	private java.sql.Date startDate;
	
	@Column(name = "END_DATE")
	private java.sql.Date endDate;
	
	@Column(name = "SCHEDULE_LOCATION")
	private String scheduleLocation;
	
	@Column(name = "SCHEDULE_CONTENT")
	private String scheduleContent;
	
	@Column(name = "SCHEDULE_DELETE")
	private String scheduleDelete;

}
