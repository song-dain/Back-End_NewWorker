package com.greedy.newworker.message.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
public class SenderManagement implements Serializable {
	
	@Id
	@Column(name = "MESSAGE_NO")
	private Long messageNo;
	
	@Column(name = "SEND_MESSAGE_DELETE")
	private String sendMessageDelete;

}
