package com.example.app.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.example.app.domain.Admin;
import com.example.app.mapper.AdminMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{
	
	private final AdminMapper mapper;
	
	@Override
	public Admin getAuthenticatedAdmin(String loginId, String loginPass) {
		Admin authenticatedAdmin = null;
		
		Admin admin = mapper.selectByLoginId(loginId);
		if(admin != null && BCrypt.checkpw(loginPass, admin.getLoginPass())) {
			authenticatedAdmin = admin;
		}
		
		return authenticatedAdmin;
	}

}
