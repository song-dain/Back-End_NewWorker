package com.greedy.newworker.notice.entity;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TBL_NOTICE")
@SequenceGenerator(name = "NOT_SEQ_GENERATOR",
sequenceName = "SEQ_NOT_NO",
initialValue = 1, allocationSize = 1)
@DynamicInsert
public class Notice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOT_SEQ_GENERATOR")
	@Column(name = "NOT_NO")
	private Long notNo;

	@Column(name = "NOT_TITLE")
	private String notTitle;

	@Column(name = "NOT_CONTENT")
	private String notContent;
	
	@Column(name = "NOT_DATE")
	private Date notDate;
	
	@Column(name = "NOT_STATUS")
	private String notStatus;
	
	@Column(name = "NOT_COUNT")
	private Long notCount;
	
	@Column(name = "NOT_UPDATE")
	private Date notUpdate;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_NO")
	private Employee employee;
	
	@Column(name = "NOT_IMAGE_URL")
	private String noticeImageUrl;
	
	/* Notice 수정 용도의 메소드 정의 */

	public void update(String notTitle2, String notContent2, Date notDate2, Date notUpdate2, String notStatus2,
			Long notNo2, String noticeImageUrl2) {
		
		this.notTitle = notTitle2;
		this.notContent = notContent2;
		this.notDate = notDate2;
		this.notNo = notNo2;
		this.notUpdate = notUpdate2;
		this.noticeImageUrl = noticeImageUrl2;
		this.notStatus = notStatus2;
	}
}