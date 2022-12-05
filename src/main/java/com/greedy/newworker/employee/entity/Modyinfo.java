package com.greedy.newworker.employee.entity;

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

import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.dto.PositionDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_MODYINFO")
@SequenceGenerator(name = "MODYINFO_SEQ_GENERATOR", sequenceName = "SEQ_MOD_NO", initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Modyinfo {

	@Id
	@Column(name = "MOD_NO" )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODYINFO_SEQ_GENERATOR")
	private Long modNo;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_NO")
	private Employee employeeNo;
	
	@ManyToOne
	@JoinColumn(name = "DEP_NO")
	private Department depNo;
	
	@ManyToOne
	@JoinColumn(name = "POSITION_NO")
	private Position positionNo;
	
	@Column(name = "MOD_DATE")
	private Date modDate;

	
	
	
	
	
	
}
