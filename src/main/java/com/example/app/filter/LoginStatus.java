package com.example.app.filter;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginStatus {

	private Integer id;
	private String name;
	private String loginId;
	public Object getAuthority() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	

}
