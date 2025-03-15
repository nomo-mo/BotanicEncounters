package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Location;

@Mapper
public interface LocationMapper {
	
	List<Location> selectAll();
	
}
