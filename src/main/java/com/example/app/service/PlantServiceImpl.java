package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.Location;
import com.example.app.domain.Plant;
import com.example.app.mapper.LocationMapper;
import com.example.app.mapper.PlantMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlantServiceImpl implements PlantService {
	
	private final PlantMapper plantMapper;
	private final LocationMapper locationMapper;
	
	@Override
	public List<Plant> getAllPlant() {
		return plantMapper.selectAll();
	}

	@Override
	public Plant getPlantById(Integer id) {
		if(id == null) {
			return null;
		}
		
		return plantMapper.selectById(id);
	}

	@Override
	public void addPlant(Plant plants) {
		plantMapper.insert(plants);
		
	}

	@Override
	public void editPlant(Plant plants) {
		plantMapper.update(plants);
	}

	@Override
	public void deletePlant(Integer id) {
		if(id == null) {
			return;
		}
		
		plantMapper.delete(id);
	}

	@Override
	public List<Plant> getItemsByPage(int page, int numPerPage) {
		int offset = numPerPage * (page - 1);
        return plantMapper.selectLimited(offset, numPerPage);
    }

	@Override
	public int getTotalPages(int numPerPage) {
		double totalNum = (double) plantMapper.count();
        return (int) Math.ceil(totalNum / numPerPage);
    }

	@Override
	public List<Location> getPlantLocations() {
		return locationMapper.selectAll();

}

	@Override
	public void addPlantImage(Integer plantId, MultipartFile file) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
