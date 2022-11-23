package com.greedy.newworker.approval.entity;

import java.sql.Date;

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
@Table(name = "TBL_APPROVAL")
@SequenceGenerator(name = "APPROVAL_SEQ_GENERATOR", sequenceName = "SEQ_APPROVAL_NO", initialValue = 1, allocationSize = 1)
@DynamicInsert
public class Approval {

	@Id
	@Column(name = "APP_NO")
	private Long appNo;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_NO")
	private Employee employee;
	
	@Column(name = "APP_DOC_NO")
	private String appDocNo;
	
	@Column(name = "APP_TITLE")
	private String appTitle;
	
	@Column(name = "APP_CONTENT")
	private String appContent;
	
	@Column(name = "APP_STATUS")
	private String appStatus;

	@Column(name = "APP_CREATED_DATE")
	private Date appCreatedDate;

	@Column(name = "APP_END_DATE")
	private Date appEndDate;
	
	@Column(name = "APP_UPDATE_DATE")
	private Date appUpdateDate;
	
	
	
}
