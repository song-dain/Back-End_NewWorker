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
@Table(name = "TBL_RECIPIENT_MANAGEMENT")
@DynamicInsert
@DynamicUpdate
public class RecipientManagement {
	
	@Id
	@Column(name = "MESSAGE_NO")
	private Long messageNo;
	
	@Column(name = "RECEIVE_MESSAGE_CATEGORY")
	private String receiveMessageCategory;
	
	@Column(name = "RECEIVE_MESSAGE_DELETE")
	private String receiveMessageDelete;

	public RecipientManagement(Long messageNo) {
		super();
		this.messageNo = messageNo;
	}

}
