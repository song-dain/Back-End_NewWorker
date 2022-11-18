package com.greedy.newworker.answer.entity;

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
import com.greedy.newworker.questionItem.entity.QuestionItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
//@NoArgsConstructor
//@ToString
@Entity
@Table(name = "TBL_ANSWER")
//@SequenceGenerator(name = "NOT_SEQ_GENERATOR",
//sequenceName = "SEQ_NOT_NO",
//initialValue = 1, allocationSize = 1)
@DynamicInsert
public class Answer {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOT_SEQ_GENERATOR")
	@ManyToOne
	@JoinColumn(name = "ANS_NO")
	private QuestionItem question;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_NO")
	private Employee employee;
	
}