package com.greedy.newworker.apttach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.greedy.newworker.approval.entity.Approval;

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
public class Apttach {

	@Id
	@Column(name = "ATTACH_NO" )
	private Long attachNo;
	
	@ManyToOne
	@JoinColumn(name = "APP_NO")
	private Approval appNo;
	
	@Column(name = "ATTACH_ROOT")
	private String attachRoot;
	
	@Column(name = "ATTACH_NAME")
	private String attachName;
	
	@Column(name = "ATTACH_ORIGIN_NAME")
	private String attachOriginName;

	
	
}
