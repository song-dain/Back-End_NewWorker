package com.greedy.newworker.apttachImg.entity;

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

import com.greedy.newworker.employee.entity.Department;
import com.greedy.newworker.employee.entity.Employee;
import com.greedy.newworker.employee.entity.Position;
import com.greedy.newworker.notice.entity.Notice;
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
public class ApttachImg {
	
	@Id
	@Column(name = "IMG_NO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
	private Long imgNo;
	
	@Column(name = "ORG_NAME")
	private String orgName;
	
	@Column(name = "SVR_NAME")
	private String svrName;
	
	@Column(name = "IMG_PATH")
	private String imgPath;
	
	@Column(name = "IMG_TYPE")
	private String imgType;
	
	@ManyToOne
	@JoinColumn(name = "NOT_NO")
	private Notice notNo;
	

}
