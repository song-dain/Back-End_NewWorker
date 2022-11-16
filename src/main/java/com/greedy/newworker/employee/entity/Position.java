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
@Table(name = "TBL_POSITION")
public class Position {
	
	@Id
	@Column(name = "POSITION_NO")
	private Long positionNo;
	
	@Column(name = "POSITION_NAME")
	private String positionName;
	
	@Column(name = "POSITION_REST_DAY")
	private Long positionRestDay;
	
}
