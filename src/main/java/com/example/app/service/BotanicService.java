package com.example.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.Botanic;
import com.example.app.domain.Location;

public interface BotanicService {
	
	List<Botanic> getAllBotanic();
	Botanic getBotanicById(Integer id);
	
	// ページ分割機能用
    List<Botanic> getItemsByPage(int page, int numPerPage);
    int getTotalPages(int numPerPage);
    
	void addBotanic(Botanic botanic);
	void editBotanic(Botanic botanic);
	void deleteBotanic(Integer id);
	void addBotanicImage(Integer botanicId, MultipartFile file);

    List<Location> getBotanicLocations();
	Botanic findByName(String botanicName);

}
