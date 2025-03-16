package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Botanic;
import com.example.app.domain.Home;

@Mapper
public interface HomeMapper {
	
	List<Botanic> selectAll();
	Home selectById(int id);
	List<Botanic> selectByCategory(@Param("category") String category);
	// ページ分割機能用
    List<Botanic> selectLimited(@Param("offset") int offset, @Param("limit") int limit);
    Long count();
	void insert(Botanic botanic);
	void update(Botanic botanic);
	void delete(int id);
	List<Botanic> searchByKeyword(String string);

}
