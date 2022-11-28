package com.greedy.newworker.approval.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
	
	@Column(name = "EMPLOYEE_NO")
	private Long employeeNo;

	@Column(name = "APP_NO")
	private Long approvalNo;
	
	
}
