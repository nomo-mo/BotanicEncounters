package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.Favorite;
import com.example.app.mapper.FavoriteMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {
    
    private final FavoriteMapper favoriteMapper;
    private final BotanicService botanicService; 

    @Override
    public void addFavorite(Integer userId, Integer botanicId, String imagePath) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setBotanicId(botanicId);
        favorite.setImagePath(imagePath);
        
        // Botanic名の取得
        String botanicName = botanicService.getBotanicById(botanicId).getBotanicName();
        favorite.setBotanicName(botanicName);
        
        // フォルダー情報も必要に応じてセット
        favorite.setFolderNo(1);
        favorite.setFolderName("未分類");
        
        favoriteMapper.insertFavorite(favorite);
    }

    @Override
    public void removeFavorite(Integer userId, Integer botanicId) {
        favoriteMapper.deleteFavorite(userId, botanicId);
    }

    @Override
    public boolean isFavorite(Integer userId, Integer botanicId) {
        return favoriteMapper.selectFavorite(userId, botanicId) != null;
    }

    @Override
    public List<Favorite> getUserFavorites(Integer userId) {
        List<Favorite> favorites = favoriteMapper.selectUserFavorites(userId);
        for (Favorite f : favorites) {
            System.out.println("植物名: " + f.getBotanicName()); // ←ここでチェック
        }
        return favorites;
    }

}