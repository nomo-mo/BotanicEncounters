package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class User {
	
    private Integer id;
    private String name;
    
    @NotBlank(message = "ログインIDを入力してください")
    private String loginId;
    
    @NotBlank(message = "パスワードを入力してください")
    private String loginPass;

}

