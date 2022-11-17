package com.greedy.newworker.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greedy.newworker.exception.dto.ApiExceptionDto;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;
	
	public JwtAccessDeniedHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 필요한 권한이 없이 접근하려 할 때 403 오류
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ApiExceptionDto errorResponse = new ApiExceptionDto(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다.");
		// Java Object -> JSON String 변환 => objectMapper.writeValueAsString(object)
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}

}
