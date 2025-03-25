package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.Favorite;
import com.example.app.mapper.FavoriteMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService{
	
	private final FavoriteMapper favoriteMapper;

	@Override
	public void addFavorite(Integer userId, Integer botanicId) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setBotanicId(botanicId);
        favoriteMapper.insertFavorite(favorite);
		
	}

	@Override
	public void removeFavorite(Integer userId, Integer botanicId) {
		favoriteMapper.deleteFavorite(userId,botanicId);
		
	}

	@Override
	public boolean isFavorite(Integer userId, Integer botanicId) {
		return favoriteMapper.isFavorite(userId, botanicId);
	}

	@Override
	public List<Favorite> getUserFavorites(Integer userId) {
		return favoriteMapper.getFavoritesByUserId(userId);
	}

}
