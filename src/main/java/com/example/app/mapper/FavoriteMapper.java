package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Favorite;

@Mapper
public interface FavoriteMapper {
    
    // お気に入り登録
	void insertFavorite(Favorite favorite);

    // お気に入り削除
	void deleteFavorite(Integer userId, Integer botanicId);
	
    // **修正点: メソッド名をXMLのidと合わせる**
	List<Favorite> selectUserFavorites(Integer userId);
    
    // お気に入りチェック
    Favorite selectFavorite(Integer userId, Integer botanicId);


}

