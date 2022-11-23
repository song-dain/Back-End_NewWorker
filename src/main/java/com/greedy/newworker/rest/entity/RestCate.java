package com.greedy.newworker.rest.entity;
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
@Table(name = "TBL_RESTCATE")
@SequenceGenerator(name = "RESTCATE_SEQ_GENERATOR", sequenceName = "SEQ_RESTCATE_TYPENO", initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class RestCate {

	@Id
	@Column(name = "RESTCATE_TYPENO" )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESTCATE_SEQ_GENERATOR")
	private Long restCateTypeNo;

	@Column(name = "RESTCATE_TYPE")
	private String restCateType;
	
	@Column(name = "RESTCATE_DED")
	private Long restCateDed;	
	
	
	
	
	
}
