package com.example.app.domain;

import lombok.Data;

@Data
public class User {
	
    private Integer id;
    private String name;
    private String loginId;
    private String loginPass;

}

