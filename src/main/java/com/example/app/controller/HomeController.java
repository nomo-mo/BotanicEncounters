package com.example.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Plant;
import com.example.app.service.HomeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
	
	private final HomeService homeService;
		

    @GetMapping("/home")
    public String home() {
        return "index";                        // templates/index.html を返す
    }
    
    @GetMapping("/plantsList")
    public String showPlantsList(Model model) {
    	 List<Plant> plants = homeService.getAllPlants(); // 植物リストを取得
         System.out.println(plants);
    	 model.addAttribute("plants", plants);            // モデルに追加
         return "plantsList";                  
    }
    
    @GetMapping("/detail/{id}")
	public String showPlantDetail(@PathVariable Integer id,Model model) {
		model.addAttribute("plant", homeService.getPlantById(id));
		return "plantsdetail";
	}
    @GetMapping("/plants/category")
    public String showPlantsByCategory(@RequestParam("category") String category, Model model) {
        List<Plant> plants = homeService.getPlantsByCategory(category);
        model.addAttribute("plants", plants);
        model.addAttribute("selectedCategory", category); // 選択されたカテゴリーをHTMLに渡す
        return "plantsList";
    }
}