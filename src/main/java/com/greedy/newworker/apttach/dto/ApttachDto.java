package com.greedy.newworker.apttach.dto;

import com.greedy.newworker.approval.dto.ApprovalDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApttachDto {
	
	private Long attachNo;
	private String attachName;
	private Long approvalNo;
	
	public ApttachDto(String attachName) {
		super();
		this.attachName = attachName;
	}
	
	
}
