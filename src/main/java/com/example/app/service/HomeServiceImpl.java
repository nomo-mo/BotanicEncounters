package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.Botanic;
import com.example.app.domain.Home;
import com.example.app.domain.Location;
import com.example.app.mapper.HomeMapper;
import com.example.app.mapper.LocationMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService{
	
	private final HomeMapper homeMapper;
	private final LocationMapper locationMapper;


	@Override
	public List<Botanic> getAllBotanicals() {
		return homeMapper.selectAll();
		
	}

	@Override
	public Home getBotanicById(Integer id) {
		if(id == null) {
			return null;
		}
		
		return homeMapper.selectById(id);
	}
	@Override
	public List<Location> getBotanicLocations() {
		return locationMapper.selectAll();
	}

	@Override
	public List<Botanic> getBotanicalsByCategory(String category) {
		 return homeMapper.selectByCategory(category);
	}

}
