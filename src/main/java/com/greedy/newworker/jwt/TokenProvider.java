package com.greedy.newworker.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.greedy.newworker.exception.TokenException;
import com.greedy.newworker.employee.dto.EmployeeDto;
import com.greedy.newworker.employee.dto.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {
	
	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_TYPE = "bearer";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 3000 * 60 * 300;	// 다들 원하셔서 넉넉히 잡아드렸습니다 (1000 * 60 * 30) 
	private final Key key;
	
	private final UserDetailsService userDetailsService;
	
	public TokenProvider(@Value("${jwt.secret}") String secretKey, UserDetailsService userDetailsService) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.userDetailsService = userDetailsService;
	}
	

	public TokenDto generateTokenDto(EmployeeDto employee) {
		
		log.info("[TokenProvider] generateTokenDto 시작 =========================");
		
		// 권한 가져오기
        List<String> roles =  Collections.singletonList(employee.getEmployeeRole());
		
		// Claims라고 불리우는 JWT body(payload)에 정보 담기
		Claims claims = Jwts
				.claims()
				.setSubject(employee.getEmployeeId());
		
		
		// 멤버 권한을 claim에 담기
		claims.put(AUTHORITIES_KEY, roles);
		
		
		
		long now = (new Date()).getTime();
		
		Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);	// 현재 시간 + 30분 => 토큰 만료 시간
		
		// Access Token 생성
		String accessToken = Jwts.builder()
				.setClaims(claims)
				.setExpiration(accessTokenExpiresIn)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
		
		return new TokenDto(BEARER_TYPE, employee.getEmployeeName(), accessToken, accessTokenExpiresIn.getTime());
	}


	public boolean validateToken(String jwt) {
		
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
			return true;
		} catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("[TokenProvider] 잘못 된 JWT 서명입니다.");
			throw new TokenException("잘못 된 JWT 서명입니다.");
		} catch(ExpiredJwtException e) {
			log.info("[TokenProvider] 만료 된 JWT 토큰입니다.");
			throw new TokenException("만료 된 JWT 토큰입니다.");
		} catch(UnsupportedJwtException e) {
			log.info("[TokenProvider] 지원되지 않는 JWT 토큰입니다.");
			throw new TokenException("지원되지 않는 JWT 토큰입니다.");
		} catch(IllegalArgumentException e) {
			log.info("[TokenProvider] JWT 토큰이 잘못 되었습니다.");
			throw new TokenException("JWT 토큰이 잘못 되었습니다.");
		}
	}

	public Authentication getAuthentication(String jwt) {
		
		Claims claims = parseClaims(jwt);
		//UserDetail 객체를 만들어서 Authentication 리턴
		UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
		
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private Claims parseClaims(String jwt) {
		
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
	}

}
