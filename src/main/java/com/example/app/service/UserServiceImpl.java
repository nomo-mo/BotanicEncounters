package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.User;
import com.example.app.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    @Transactional
    public void addUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.selectUserByName(name);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        userMapper.deleteUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}

