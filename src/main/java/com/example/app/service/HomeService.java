package com.example.app.service;

import java.util.List;

import com.example.app.domain.Home;
import com.example.app.domain.Location;
import com.example.app.domain.Plant;

public interface HomeService {
	
	List<Plant>getAllPlants();
	Home getPlantById(Integer id);
	List<Location> getPlantLocations();
	List<Plant> getPlantsByCategory(String category);

}
