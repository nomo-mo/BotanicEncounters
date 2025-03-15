package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Plant;

@Mapper
public interface PlantMapper {
	
	List<Plant> selectAll();
	Plant selectById(int id);
	
	// ページ分割機能用
    List<Plant> selectLimited(@Param("offset") int offset, @Param("limit") int limit);
    Long count();
	
	void insert(Plant plant);
	void update(Plant plant);
	void delete(int id);
	
}
