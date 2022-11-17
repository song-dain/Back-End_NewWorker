package com.greedy.newworker.exception.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiExceptionDto {

	private int state;
	private String message;
	
	public ApiExceptionDto(HttpStatus status, String message) {
		this.state = status.value();
		this.message = message;
	}
	
	
}
