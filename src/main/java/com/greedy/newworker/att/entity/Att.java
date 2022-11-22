package com.greedy.newworker.att.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.greedy.newworker.att.dto.AttTypeDto;
import com.greedy.newworker.employee.entity.Employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity	
@Table(name = "TBL_ATT")
@SequenceGenerator(name = "ATT_SEQ_GENERATOR", 
	sequenceName = "SEQ_ATT_NO", 
	initialValue = 1, allocationSize = 1)
public class Att {
	
	@Id
	@Column(name="ATT_NO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATT_SEQ_GENERATOR")
	private Long attNo;
	
	@ManyToOne
	@JoinColumn(name="ATT_TYPE_NO")
	private AttType attType;
	
	@ManyToOne
	@JoinColumn(name="EMPLOYEE_NO")
	private Employee employee;
	
	@Column(name="ATT_START")
	private java.util.Date attStart;
	
	@Column(name="ATT_END")
	private java.util.Date attEnd;
	
	@Column(name="ATT_DATE")
	private java.util.Date attDate;
	
	@Column(name="ATT_WORKTIME")
	private int attWorkTime;
	
	@Column(name="ATT_MONTH")
	private String attMonth;

	public void updateEnd(LocalDateTime now) {
		
		Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        
		this.attEnd = date;
		
	}


}
