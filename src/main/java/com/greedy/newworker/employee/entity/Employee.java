package com.greedy.newworker.employee.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@Getter	
@Setter
@Entity
@Table(name = "TBL_EMPLOYEE")
@SequenceGenerator(name = "EMPLOYEE_SEQ_GENERATOR", sequenceName = "SEQ_EMPLOYEE_NO", initialValue = 1, allocationSize = 1)
@DynamicInsert
public class Employee {

	@Id
	@Column(name = "EMPLOYEE_NO")
	private Long employeeNo;
	
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name = "EMPLOYEE_PWD")
	private String employeePwd;
	
	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;
	
	@Column(name = "EMPLOYEE_EMAIL")
	private String employeeEmail;
	
	@Column(name = "EMPLOYEE_PHONE")
	private String employeePhone;
	
	@Column(name = "EMPLOYEE_ADDRESS")
	private String employeeAddress;
	
	@Column(name = "EMPLOYEE_STATUS")
	private String employeeStatus;
	
	@Column(name = "EMPLOYEE_ROLE")
	private String employeeRole;
	
	@ManyToOne
	@JoinColumn(name = "POSITION_NO")
	private Position position;
	
	@ManyToOne
	@JoinColumn(name = "DEP_NO")
	private Department dep;
	
	@Column(name = "EMPLOYEE_REST_DAY")
	private Long employeeRestDay;
	
	@Column(name = "EMPLOYEE_IMAGE")
	private String employeeImage;
	
	@Column(name = "EMPLOYEE_HIRE_DATE")
	private Date employeeHireDate;
	
	@Column(name = "EMPLOYEE_ENT_DATE")
	private Date employeeEntDate;
	
}
