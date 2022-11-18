package com.greedy.newworker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.greedy.newworker.employee.exception.LoginFailedException;
import com.greedy.newworker.exception.dto.ApiExceptionDto;

@RestControllerAdvice
public class ApiExceptionAdvice {
	
	@ExceptionHandler(DuplicatedUsernameException.class)
	public ResponseEntity<ApiExceptionDto> exceptionHandler(DuplicatedUsernameException e) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ApiExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage()));
	}

	
	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<ApiExceptionDto> exceptionHandler(LoginFailedException e) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ApiExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage()));
	}
	
	
}
