package com.greedy.newworker.att.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.greedy.newworker.employee.dto.EmployeeDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttDto {
	
	private Long attNo;
	private AttTypeDto attType;
	private EmployeeDto employee;
	private java.util.Date attStart;
	private java.util.Date attEnd;
	private java.util.Date attDate;
	private int attWorkTime;
	
	public void setAttStart(LocalDateTime now) {

        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        
        this.attStart = date;
        		
	}
	
	public void setAttDate(LocalDateTime now) {

        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        
        this.attDate = date;
        		
	}
	
	public void setAttEnd(LocalDateTime now) {
		
		Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        
        this.attEnd = date;
	}

}
