package com.greedy.newworker.employee.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
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
import org.springframework.web.multipart.MultipartFile;

import com.greedy.newworker.employee.dto.DepartmentDto;
import com.greedy.newworker.employee.dto.PositionDto;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
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
	
	@ManyToOne/*(cascade = CascadeType.PERSIST)*/
	@JoinColumn(name = "POSITION_NO")
	private Position position;
	
	@ManyToOne/*(cascade = CascadeType.PERSIST)*/
	@JoinColumn(name = "DEP_NO")
	private Department dep;
	
	@Column(name = "EMPLOYEE_REST_DAY")
	private Long employeeRestDay;
	
	@Column(name = "EMPLOYEE_IMAGE_URL")
	private String employeeImageUrl;
	
	@Column(name = "EMPLOYEE_HIRE_DATE")
	private Date employeeHireDate;
	
	@Column(name = "EMPLOYEE_ENT_DATE")
	private Date employeeEntDate;

	//수정
	public void update(String employeePwd, String employeeName, String employeeEmail, String employeePhone,
			String employeeAddress, String employeeStatus, String employeeRole, Position position, Department dep,
			Long employeeRestDay, String employeeImageUrl, Date employeeHireDate, Date employeeEntDate) {
					
			
			this.employeePwd = employeePwd;
			this.employeeName = employeeName;
			this.employeeEmail = employeeEmail;
			this.employeePhone = employeePhone;
			this.employeeAddress = employeeAddress;
			this.employeeStatus = employeeStatus;
			this.employeeRole = employeeRole;
			this.position = position;
			this.dep = dep;
			this.employeeRestDay = employeeRestDay;
			this.employeeImageUrl = employeeImageUrl;
			this.employeeHireDate = employeeHireDate;
			this.employeeEntDate = employeeEntDate;
		
	}

	
	
	
	
	/* 수정 */	
//	public void update(String employeePwd, String employeeName, String employeeEmail,
//			String employeePhone, String employeeAddress, String employeeStatus, String employeeRole, Long positionNo,
//			Long depNo, Long employeeRestDay, String employeeImageUrl, Date employeeHireDate,
//			Date employeeEntDate) {
//
//		
//		this.employeePwd = employeePwd;
//		this.employeeName = employeeName;
//		this.employeeEmail = employeeEmail;
//		this.employeePhone = employeePhone;
//		this.employeeAddress = employeeAddress;
//		this.employeeStatus = employeeStatus;
//		this.employeeRole = employeeRole;		
//		this.position.setPositionNo(positionNo); 
//		this.dep.setDepNo(depNo);
//		this.employeeRestDay = employeeRestDay;
//		this.employeeImageUrl = employeeImageUrl;
//		this.employeeHireDate = employeeHireDate;
//		this.employeeEntDate = employeeEntDate;
//}

	
	
	
	


	

	


	



	

	
	
}
