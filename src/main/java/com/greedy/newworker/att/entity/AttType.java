package com.greedy.newworker.att.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity	
@Table(name = "TBL_ATT_TYPE")
@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", 
	sequenceName = "SEQ_ATT_TYPE_NO", 
	initialValue = 1, allocationSize = 1)
public class AttType {
	
	@Id
	@Column(name="ATT_TYPE_NO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATT_TYPE_SEQ_GENERATOR")
	private Long attTypeNo;
	
	@Column(name="ATT_TYPE")
	private String attType;

}
