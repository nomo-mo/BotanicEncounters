package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.Botanic;
import com.example.app.domain.Location;
import com.example.app.mapper.BotanicMapper;
import com.example.app.mapper.LocationMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BotanicServiceImpl implements BotanicService {
	
	private final BotanicMapper botanicMapper;
	private final LocationMapper locationMapper;
	
	@Override
	public List<Botanic> getAllBotanic() {
		return botanicMapper.selectAll();
	}

	@Override
	public Botanic getBotanicById(Integer id) {
		if(id == null) {
			return null;
		}
		
		return botanicMapper.selectById(id);
	}

	@Override
	public void addBotanic(Botanic botanicals) {
		botanicMapper.insert(botanicals);	
	}

	@Override
	public void editBotanic(Botanic botanicals) {
		botanicMapper.update(botanicals);
	}

	@Override
	public void deleteBotanic(Integer id) {
		if(id == null) {
			return;
		}
		
		botanicMapper.delete(id);	
	}

	@Override
	public List<Botanic> getItemsByPage(int page, int numPerPage) {
		int offset = numPerPage * (page - 1);
        return botanicMapper.selectLimited(offset, numPerPage);
    }

	@Override
	public int getTotalPages(int numPerPage) {
		double totalNum = (double) botanicMapper.count();
        return (int) Math.ceil(totalNum / numPerPage);
    }

	@Override
	public List<Location> getBotanicLocations() {
		return locationMapper.selectAll();

}

	

	@Override
	public void addBotanicImage(Integer botanicId, MultipartFile file) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public Botanic findByName(String botanicName) {
		return botanicMapper.findByName(botanicName);
	}



	


}
