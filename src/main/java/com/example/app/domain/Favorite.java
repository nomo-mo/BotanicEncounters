package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Favorite {
	
	private Integer id;
	private Integer userId;
	private Integer botanicId;
	private LocalDateTime createdAt;

}
