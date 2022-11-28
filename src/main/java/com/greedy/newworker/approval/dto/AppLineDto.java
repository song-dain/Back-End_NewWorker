package com.greedy.newworker.approval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppLineDto {

	private Long appLineNo;
	private Long appLineTurn;
	private Long employeeNo;
	private Long approval;
	
	
}
