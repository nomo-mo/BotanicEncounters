package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Favorite;

@Mapper
public interface FavoriteMapper {
    void insertFavorite(Favorite favorite);
    void deleteFavorite(@Param("userId") Integer userId, @Param("botanicId") Integer botanicId);
    boolean isFavorite(@Param("userId") Integer userId, @Param("botanicId") Integer botanicId);
    List<Favorite> getFavoritesByUserId(Integer userId);
}

