package com.example.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.Location;
import com.example.app.domain.Plant;

public interface PlantService {
	
	List<Plant> getAllPlant();
	Plant getPlantById(Integer id);
	
	// ページ分割機能用
    List<Plant> getItemsByPage(int page, int numPerPage);
    int getTotalPages(int numPerPage);
    
	void addPlant(Plant plant);
	void editPlant(Plant plant);
	void deletePlant(Integer id);
	void addPlantImage(Integer plantId, MultipartFile file);

    List<Location> getPlantLocations();

}
