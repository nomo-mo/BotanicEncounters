package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Favorite {
	
	private Integer id;
	private Integer userId;
	private String botanicName;
	private String imagePath; 
	private LocalDateTime createdAt;
	public void setBotanicId(Integer botanicId) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
