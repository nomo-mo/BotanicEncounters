package com.example.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Botanic;
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
    
    @GetMapping("/botanicList")
    public String showBotanicalsList(Model model) {
    	 List<Botanic> botanicals = homeService.getAllBotanicals(); // 植物リストを取得
         System.out.println(botanicals);
    	 model.addAttribute("botanicals", botanicals);            // モデルに追加
         return "botanicalsList";                  
    }
   
    @GetMapping("/botanic/detail/{id}")
	public String showBotanicDetail(@PathVariable Integer id,Model model) {
		model.addAttribute("botanic", homeService.getBotanicById(id));
		return "botanicalsDetail";
	}
    @GetMapping("/botanic/category")
    public String showBotanicalsByCategory(@RequestParam("category") String category, Model model) {
        List<Botanic> botanicals = homeService.getBotanicalsByCategory(category);
        model.addAttribute("botanicals", botanicals);
        model.addAttribute("selectedCategory", category); // 選択されたカテゴリーをHTMLに渡す
        return "botanicalsList";
    }
    @GetMapping("/botanic/search")
    public String searchBotanicals(@RequestParam("keyword") String keyword, Model model) {
        List<Botanic> botanicals = homeService.searchBotanicalsByKeyword(keyword);
        model.addAttribute("botanicals", botanicals);
        model.addAttribute("searchKeyword", keyword);
        return "botanicalsList";
    }
}