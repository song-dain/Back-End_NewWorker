package com.greedy.newworker.approval.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicUpdate
public class AppLine {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_LINE_SEQ_GENERATOR")
	@Column(name = "APP_LINE_NO")
	private Long appLineNo;
	
	@Column(name = "APP_LINE_TURN")
	private Long appLineTurn;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_NO")
	private Employee employee;

	@Column(name = "APP_NO")
	private Long approvalNo;
	
	
	/* 결재 승인과 관련된 부분 */
	
	@Column(name = "ACCEPT_STATUS")
	private String acceptStatus;
	
	@Column(name = "ACCEPT_ACTIVATE")
	private String acceptActivate;
	
	@Column(name = "ACCEPT_DATE")
	private Date acceptDate;
	
	
}
