package com.greedy.newworker.calendar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_CALENDAR_CATEGORY")
public class CalendarCategory {
	
	@Id
	@Column(name = "CALENDAR_CATEGORY_NO")
	private Long calendarCategoryNo;
	
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	

}
