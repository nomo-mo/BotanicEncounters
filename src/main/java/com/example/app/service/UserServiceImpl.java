package com.example.app.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.User;
import com.example.app.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

	@Override
	public List<User> getUserList() {
		return userMapper.selectAll();
	}

	@Override
	public User getUserById(Integer id) {
		return userMapper.selectById(id);
	}

	@Override
	public User getUserByLoginId(String logingId) {
		return userMapper.selectByLoginId(logingId);
	}

	@Override
	public void deleteUserById(Integer id) {
		userMapper.setDeleteById(id);
		
	}

	@Override
	public void addUser(User user) {
		String password = user.getLoginPass();
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setLoginPass(hashedPassword);
		userMapper.insert(user);
	}

	@Override
	public void editUser(User user) {
		String password = user.getLoginPass();
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setLoginPass(hashedPassword);
		userMapper.update(user);		
	}

	@Override
	public boolean isExsitingUser(String loginId) {
		User user = userMapper.selectByLoginId(loginId);
		if(user != null) {
			return true;
		}

		return false;
	}

	@Override
	public List<User> getUserListPerPage(int page, int numPerPage) {
		int offset = numPerPage * (page - 1);
		return userMapper.selectLimited(offset, numPerPage);
	}

	@Override
	public int getTotalPages(int numPerPage) {
		long count = userMapper.countActive();
		return (int) Math.ceil((double) count / numPerPage);
	}

	@Override
	public User findByName(String userName) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}


    
    
}

