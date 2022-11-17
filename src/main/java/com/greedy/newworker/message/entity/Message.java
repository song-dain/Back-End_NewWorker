package com.greedy.newworker.message.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.greedy.newworker.employee.entity.Employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_MESSAGE")
@SequenceGenerator(name = "MESSAGE_SEQ_GENERATOR", sequenceName = "SEQ_MESSAGE_NO", initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Message {
	
	@Id
	@Column(name = "MESSAGE_NO" )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ_GENERATOR")
	private Long messageNo;
	
	@OneToOne
	@JoinColumn(name = "MESSAGE_NO")
	private RecipientManagement recipientManagement;
	
	@OneToOne
	@JoinColumn(name = "MESSAGE_NO")
	private SenderManagement senderManagement;
	
	@Column(name = "MESSAGE_CONTENT")
	private String messageContent;
	
	@Column(name = "SEND_DATE")
	private java.sql.Date sendDate;
	
	@ManyToOne
	@JoinColumn(name = "RECIPIENT")
	private Employee recipient;
	
	@ManyToOne
	@JoinColumn(name = "SENDER")
	private Employee sender;
	private String messageStatus;

}
