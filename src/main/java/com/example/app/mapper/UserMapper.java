package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.User;

@Mapper
public interface UserMapper {
	
	 // すべてのユーザーを取得
    List<User> selectAll();
    
    // IDでユーザーを取得
    User selectById(Integer id);
    
    User selectByLoginId(String loginId);  
    
    void setDeleteById(Integer id); 
 
    // ユーザーを削除
    void insert(User user); 
    
    // ユーザー情報を更新
    void update(User user); 
    
    List<User> selectLimited(@Param("offset") int offset, @Param("num") int num); 

    long countActive();
}
