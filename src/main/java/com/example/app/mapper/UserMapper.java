package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.User;

@Mapper
public interface UserMapper {
	
    // ユーザーを追加
    void insertUser(User user);

    // IDでユーザーを取得
    User selectUserById(int id);

    // すべてのユーザーを取得
    List<User> selectAllUsers();

    // ユーザー名でユーザーを取得
    User selectUserByName(String name);

    // ユーザーを削除
    void deleteUser(int id);

    // ユーザー情報を更新
    void updateUser(User user);
}
