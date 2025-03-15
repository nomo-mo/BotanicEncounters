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
import com.example.app.domain.Botanic;
import com.example.app.service.BotanicService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/botanicals")
public class BotanicController {
	
	 private final int NUM_PER_PAGE = 10;
		
		private final BotanicService service;
		private final HttpSession session;
		
		@GetMapping
		public String showBotanicalsList(
				@RequestParam(name = "page", defaultValue = "1") Integer page,
				Model model) {
			 Admin authAdmin = (Admin) session.getAttribute("authAdmin");
			    if (authAdmin == null) {
			        System.out.println("セッション情報なし。ログインページへリダイレクト");
			        return "redirect:/"; // セッションが切れていた場合はログインページへ
			    }
			    
			    System.out.println("ログイン済みユーザー：" + authAdmin.getLoginId());
			model.addAttribute("botanicList", service.getItemsByPage(page, NUM_PER_PAGE));
			model.addAttribute("page", page);
			session.setAttribute("page", page);
			model.addAttribute("totalPages", service.getTotalPages(NUM_PER_PAGE));
			return "list";
		}
		
		@GetMapping("/detail/{id}")
		public String showBotanicDetail(@PathVariable Integer id,
				Model model) {
			model.addAttribute("botanic", service.getBotanicById(id));
			return "detail";
		}
		
		@GetMapping("/add")
		public String showAddForm(Model model) {
			model.addAttribute("botanic", new Botanic());
			model.addAttribute("locationList", service.getBotanicLocations());
			return "save";
		}
		
		@PostMapping("/add")
		public String addBotanic(@Valid Botanic botanic,
				Errors errors,
				Model model,
				RedirectAttributes ra) {
			if(errors.hasErrors()) {
				model.addAttribute("locationList", service.getBotanicLocations());
				return "save";
			}
			
			service.addBotanic(botanic);
			ra.addFlashAttribute("status", "植物を追加しました");
			return "redirect:/botanicals";
		}
		
		@GetMapping("/edit/{id}")
		public String showEditForm(@PathVariable Integer id,
				Model model) {
			model.addAttribute("botanic", service.getBotanicById(id));
			model.addAttribute("locationList", service.getBotanicLocations());
			return "edit";
		}
		
		@PostMapping("/edit/{id}")
		public String editBotanic(@PathVariable Integer id,
				@Valid Botanic botanic,
				Errors errors,
				Model model,
				RedirectAttributes ra) {
			if(errors.hasErrors()) {
				model.addAttribute("locationList", service.getBotanicLocations());
				return "edit";
			}
			
			service.editBotanic(botanic);
			ra.addFlashAttribute("status", "植物情報を更新しました");
			return "redirect:/botanicals/detail/" + id;
		}
		
		@GetMapping("/delete/{id}")
		public String deleteBotanic(@PathVariable Integer id,
				RedirectAttributes ra) {
			service.deleteBotanic(id);		
			ra.addFlashAttribute("status", "植物を削除しました");
			return "redirect:/botanicals";
		}
		
	}	
		
		