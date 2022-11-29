package com.greedy.newworker.survey.entity;

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

import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.survey.entity.Survey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TBL_QUESTION_ITEM")
@SequenceGenerator(name = "QUE_SEQ_GENERATOR",
sequenceName = "SEQ_ANS_NO",
initialValue = 1, allocationSize = 1)
@DynamicInsert
public class QuestionItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUE_SEQ_GENERATOR")
	@Column(name = "ANS_NO")
	private Long ansNo;

	@Column(name = "ANS_CONTENT")
	private String ansContent;
	
//	@ManyToOne
//	@JoinColumn(name = "SUR_NO")
	@Column(name = "SUR_NO")
	private Long surNo;
	
}