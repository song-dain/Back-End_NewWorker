package com.greedy.newworker.rest.entity;

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
@Table(name = "TBL_REST")
@SequenceGenerator(name = "REST_SEQ_GENERATOR", sequenceName = "SEQ_REST_NO", initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Rest {
	
	@Id
	@Column(name = "REST_NO" )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REST_SEQ_GENERATOR")
	private Long restNo;
	
	@Column(name = "REST_DATE")
	private Date restDate;
	
	@Column(name = "REST_MODDATE")
	private Date restModdate;
	
	@Column(name = "REST_OK")
	private String restOk;

	@Column(name = "REST_OKDATE")
	private Date restOkdate;

	@Column(name = "REST_DAY")
	private Long restDay;

	@Column(name = "REST_FDATE")
	private Date restFdate;

	@Column(name = "REST_LDATE")
	private Date restLdate;

	
	@Column(name = "REST_REASON")
	private String restReason;

	@Column(name = "REST_YN")
	private String restYn;
	
	@ManyToOne
	@JoinColumn(name = "RESTCATE_TYPENO")
	private RestCate restCateTypeNo;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_NO")
	private Employee employeeNo;

	public void update(Date restDate, Date restModdate, Long restDay, String restReason, RestCate restCateTypeNo) {
		this.restDate = restDate;
		this.restModdate = restModdate;
		this.restDay = restDay;
		this.restReason = restReason;
		this.restCateTypeNo = restCateTypeNo;
	}

	public void updateOk(String restOk, Date restOkdate) {
		this.restOk = restOk;
		this.restOkdate = restOkdate;		
	}
	
	

	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
