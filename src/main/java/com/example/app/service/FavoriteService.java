package com.example.app.service;

import java.util.List;

import com.example.app.domain.Favorite;

public interface FavoriteService {
    
    // お気に入り登録（画像パスも保存）
    void addFavorite(Integer userId, Integer botanicId, String imagePath);
    
    // お気に入り削除
    void removeFavorite(Integer userId, Integer botanicId);
    
    // すでにお気に入りかどうかをチェック
    boolean isFavorite(Integer userId, Integer botanicId);
    
    // 指定ユーザーのお気に入り一覧を取得（画像パスも含む）
    List<Favorite> getUserFavorites(Integer userId);

}