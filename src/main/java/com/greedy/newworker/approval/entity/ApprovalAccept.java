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
@Table(name = "TBL_APPROVAL_ACCEPT")
@SequenceGenerator(name = "APPROVAL_ACEEPT_SEQ_GENERATOR", sequenceName = "SEQ_APPROVAL_ACCEPT_NO", initialValue = 1, allocationSize = 1)
@DynamicInsert
public class ApprovalAccept {

	@Id
	@Column(name = "ACC_NO")
	private Long accNo;
	
	@ManyToOne
	@JoinColumn(name = "APP_NO")
	private Approval app;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_NO")
	private Employee employee;
	
	@Column(name = "ACC_STATUS")
	private String accStatus;
	
	@Column(name = "ACC_DATE")
	private Date accDate;
	
	
}
