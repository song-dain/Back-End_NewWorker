//package com.greedy.newworker.rest.entity;
//
//import java.sql.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import com.greedy.newworker.employee.entity.Employee;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "TBL_REST")
//@SequenceGenerator(name = "REST_SEQ_GENERATOR", sequenceName = "SEQ_REST_NO", initialValue = 1, allocationSize = 1)
//@DynamicInsert
//@DynamicUpdate
//public class Rest {
//	
//	@Id
//	@Column(name = "REST_NO" )
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REST_SEQ_GENERATOR")
//	private Long RestNo;
//	
//	@Column(name = "REST_DATE")
//	private Date RestDate;
//	
//	@Column(name = "REST_MODDATE")
//	private Date RestModdate;
//	
//	@Column(name = "REST_OK")
//	private String RestOk;
//
//	@Column(name = "REST_OKDATE")
//	private Date RestOkdate;
//
//	@Column(name = "REST_DAY")
//	private Long RestDay;
//
//	@Column(name = "REST_FDATE")
//	private Date RestFdate;
//
//	@Column(name = "REST_LDATE")
//	private Date RestLdate;
//
//	
//	@Column(name = "REST_REASON")
//	private String RestReason;
//
//	@Column(name = "REST_YN")
//	private String RestYn;
//	
//	@ManyToOne
//	@JoinColumn(name = "RESTCATE_TYPENO")
//	private RestCate RestCateTypeNo;
//
//	@ManyToOne
//	@JoinColumn(name = "EMPLOYEE_NO")
//	private Employee EmployeeNo;
//
//	 
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
