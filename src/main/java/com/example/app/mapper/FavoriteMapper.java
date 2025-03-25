package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Favorite;

@Mapper
public interface FavoriteMapper {
    
    // お気に入り登録
    void insertFavorite(Favorite favorite);

    // お気に入り削除
    void deleteFavorite(@Param("userId") Integer userId, @Param("botanicName") String botanicName);

    // すでにお気に入りかどうかをチェック
    boolean isFavorite(@Param("userId") Integer userId, @Param("botanicName") String botanicName);

    // 指定ユーザーのお気に入り一覧を取得
    List<Favorite> getFavoritesByUserId(Integer userId);
}

