package com.example.app.filter;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginStatus {

	private Integer id;
	private String name;
	private String loginId;
	private LoginAuthority authority;

	public Integer getUserId() {
		return id;
	}
}
