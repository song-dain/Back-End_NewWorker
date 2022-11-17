package com.greedy.newworker.message.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "TBL_SENDER_MANAGEMENT")
@DynamicInsert
@DynamicUpdate
public class SenderManagement {
	
	@Id
	@Column(name = "MESSAGE_NO")
	private Long messageNo;
	
	@Column(name = "SEND_MESSAGE_DELETE")
	private String sendMessageDelete;

}
