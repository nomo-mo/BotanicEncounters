package com.example.app.service;

import java.util.List;

import com.example.app.domain.Favorite;

public interface FavoriteService {
    
    // お気に入り登録
    void addFavorite(Integer userId, Integer integer, String imagePath);
    
    // お気に入り削除
    void removeFavorite(Integer userId, Integer integer);
    
    // すでにお気に入りかどうかをチェック
    boolean isFavorite(Integer userId, String botanicName);
    
    // 指定ユーザーのお気に入り一覧を取得
    List<Favorite> getUserFavorites(Integer userId);
}

