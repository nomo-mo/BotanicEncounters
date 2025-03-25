package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Botanic;

@Mapper
public interface BotanicMapper {
	
	List<Botanic> selectAll();
	Botanic selectById(int id);
	
	// ページ分割機能用
    List<Botanic> selectLimited(@Param("offset") int offset, @Param("limit") int limit);
    Long count();
	
	void insert(Botanic botanic);
	void update(Botanic botanic);
	void delete(int id);
	Botanic findByName(String botanicName);
	
}
