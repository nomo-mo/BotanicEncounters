package com.example.app.service;

import java.util.List;

import com.example.app.domain.Botanic;
import com.example.app.domain.Home;
import com.example.app.domain.Location;

public interface HomeService {
	
	List<Botanic>getAllBotanicals();
	Home getBotanicById(Integer id);
	List<Location> getBotanicLocations();
	List<Botanic> getBotanicalsByCategory(String category);

}
