package com.greedy.newworker.apttach.dto;

import com.greedy.newworker.approval.dto.ApprovalDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApttachDto {
	
	private Long attachNo;
	private ApprovalDto appNo;
	private String attachRoot;
	private String attachName;
	private String attachOriginName;

}
