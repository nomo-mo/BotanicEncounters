package com.example.app.service;

import java.util.List;

import com.example.app.domain.Botanic;
import com.example.app.domain.Location;

public interface HomeService {
	
	List<Botanic>getAllBotanicals();
	Botanic getBotanicById(Integer id);
	List<Location> getBotanicLocations();
	List<Botanic> getBotanicalsByCategory(String category);
	List<Botanic> searchBotanicalsByKeyword(String keyword);

}
