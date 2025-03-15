package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Admin;
import com.example.app.domain.Plant;
import com.example.app.service.PlantService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/plants")
public class PlantController {
	
	 private final int NUM_PER_PAGE = 10;
		
		private final PlantService service;
		private final HttpSession session;
		
		@GetMapping
		public String showPlantList(
				@RequestParam(name = "page", defaultValue = "1") Integer page,
				Model model) {
			 Admin authAdmin = (Admin) session.getAttribute("authAdmin");
			    if (authAdmin == null) {
			        System.out.println("セッション情報なし。ログインページへリダイレクト");
			        return "redirect:/"; // セッションが切れていた場合はログインページへ
			    }
			    
			    System.out.println("ログイン済みユーザー：" + authAdmin.getLoginId());
			model.addAttribute("plantList", service.getItemsByPage(page, NUM_PER_PAGE));
			model.addAttribute("page", page);
			session.setAttribute("page", page);
			model.addAttribute("totalPages", service.getTotalPages(NUM_PER_PAGE));
			return "list";
		}
		
		@GetMapping("/detail/{id}")
		public String showPlantDetail(@PathVariable Integer id,
				Model model) {
			model.addAttribute("plant", service.getPlantById(id));
			return "detail";
		}
		
		@GetMapping("/add")
		public String showAddForm(Model model) {
			model.addAttribute("plant", new Plant());
			model.addAttribute("locationList", service.getPlantLocations());
			return "save";
		}
		
		@PostMapping("/add")
		public String addPlant(@Valid Plant plant,
				Errors errors,
				Model model,
				RedirectAttributes ra) {
			if(errors.hasErrors()) {
				model.addAttribute("locationList", service.getPlantLocations());
				return "save";
			}
			
			service.addPlant(plant);
			ra.addFlashAttribute("status", "観葉植物を追加しました");
			return "redirect:/plants";
		}
		
		@GetMapping("/edit/{id}")
		public String showEditForm(@PathVariable Integer id,
				Model model) {
			model.addAttribute("plant", service.getPlantById(id));
			model.addAttribute("locationList", service.getPlantLocations());
			return "edit";
		}
		
		@PostMapping("/edit/{id}")
		public String editPlant(@PathVariable Integer id,
				@Valid Plant plant,
				Errors errors,
				Model model,
				RedirectAttributes ra) {
			if(errors.hasErrors()) {
				model.addAttribute("locationList", service.getPlantLocations());
				return "edit";
			}
			
			service.editPlant(plant);
			ra.addFlashAttribute("status", "観葉植物情報を更新しました");
			return "redirect:/plants/detail/" + id;
		}
		
		@GetMapping("/delete/{id}")
		public String deletePlant(@PathVariable Integer id,
				RedirectAttributes ra) {
			service.deletePlant(id);		
			ra.addFlashAttribute("status", "観葉植物を削除しました");
			return "redirect:/plants";
		}
		
		


	}	
		
		