package com.example.app.service;

import java.util.List;

import com.example.app.domain.User;

public interface UserService {
	
	// すべてのユーザーを取得
    List<User> getUserList();

    // IDでユーザーを取得
    User getUserById(Integer id);
    User getUserByLoginId(String logingId);
	void deleteUserById(Integer id);
	void addUser(User user);
	void editUser(User user);
	boolean isExsitingUser(String loginId);
	List<User> getUserListPerPage(int page, int numPerPage);
    int getTotalPages(int numPerPage);

	User findByName(String userName);
}
