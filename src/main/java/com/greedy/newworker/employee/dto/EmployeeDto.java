package com.greedy.newworker.employee.dto;

import java.sql.Date;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EmployeeDto implements UserDetails {

	private Long employeeNo;
	private String employeeId;
	private String employeePwd;
	private String employeeName;
	private String employeeEmail;
	private String employeePhone;
	private String employeeAddress;
	private String employeeStatus;
	private String employeeRole;
	private PositionDto position;
	private DepartmentDto dep;
	private Long employeeRestDay;
	
	private String code;
	
	private MultipartFile employeeImage;
	
	private String employeeImageUrl;
	private Date employeeHireDate;
	private Date employeeEntDate;
	
	
	
	/* Securiy 인증, 인가와 관련된 코드입니다. */
	private Collection<? extends GrantedAuthority> authorities;
	
	
	/* UserDetails의 권한을 체크하는 구간입니다. */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getPassword() {
		return employeePwd;
	}
	@Override
	public String getUsername() {
		return employeeId;
	}
	
	
	/* 로그인 하는 계정의 상태에 대한 체크 구간(만료, 잠김, 사용가능 여부 등) */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
