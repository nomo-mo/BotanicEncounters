package com.example.app.service;

import java.util.List;

import com.example.app.domain.User;

public interface UserService {
    // ユーザーを追加
    void addUser(User user);

    // IDでユーザーを取得
    User getUserById(int id);

    // すべてのユーザーを取得
    List<User> getAllUsers();

    // ユーザー名でユーザーを取得
    User getUserByName(String name);

    // ユーザーを削除
    void removeUser(int id);

    // ユーザー情報を更新
    void updateUser(User user);
}
