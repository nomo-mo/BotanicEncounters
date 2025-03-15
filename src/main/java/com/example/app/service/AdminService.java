package com.example.app.service;

import com.example.app.domain.Admin;

public interface AdminService {
	
	Admin getAuthenticatedAdmin(String loginId, String loginPass);

}
