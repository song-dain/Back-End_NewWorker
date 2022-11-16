package com.greedy.newworker.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {

	private String grantType; //나중에 다시 토큰을 요청해야 할때 어떤 타입으로 해야 하는지에 대한 정보
	private String memberName; //추가적으로, 계정에 대한 기억해놓고 싶은 데이터가 있을 경우 담을 용도의 필드
	private String accessToken; //3가지로 구분되던 긴 문자열
	private Long accessTokenExpiresIn; // 확인하기 쉽도록 언제까지 유효한지 체크하는 데이터

	
	
}

