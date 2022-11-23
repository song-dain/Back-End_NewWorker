package com.greedy.newworker.approval.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.greedy.newworker.employee.entity.Employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_APP_LINE")
@SequenceGenerator(name = "APP_LINE_SEQ_GENERATOR", sequenceName = "SEQ_APP_LINE_NO", initialValue = 1, allocationSize = 1)
@DynamicInsert
public class AppLine {

	@Id
	@Column(name = "APP_LINE_NO")
	private Long appLineNo;
	
	@Column(name = "APP_LINE_TURN")
	private Long appLineTurn;
	
	@ManyToOne
	@JoinColumn(name = "APP_NO")
	private Approval app;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_NO")
	private Employee employee;
	
	
}
