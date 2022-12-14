package com.greedy.newworker.apttach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_APTTACH")
@SequenceGenerator(name = "ATTACH_SEQ_GENERATOR", sequenceName = "SEQ_ATTACH_NO", initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Apttach {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTACH_SEQ_GENERATOR")
	@Column(name = "ATTACH_NO")
	private Long attachNo;

	@Column(name = "ATTACH_NAME")
	private String attachName;
	
	@Column(name = "APP_NO")
	private Long approvalNo;

	
	
}
