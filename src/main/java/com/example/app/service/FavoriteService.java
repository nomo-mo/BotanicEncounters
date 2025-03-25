package com.example.app.service;

import java.util.List;

import com.example.app.domain.Favorite;

public interface FavoriteService {
	
	void addFavorite(Integer userId,Integer botanicId);
    void removeFavorite(Integer userId, Integer botanicId);
    boolean isFavorite(Integer userId, Integer botanicId);
    List<Favorite> getUserFavorites(Integer userId);

}
