package com.greedy.newworker.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@Getter
@Setter
@Entity
@Table(name = "TBL_DEPARTMENT")
public class Department {

	@Id
	@Column(name = "DEP_NO")
	private Long depNo;
	

	@Column(name = "DEP_NAME")
	private String depName;
	
}
