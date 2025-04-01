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
		public String showBotanicList(                                         // page パラメータを受け取り、植物リストをページネーション対応で取得
				@RequestParam(name = "page", defaultValue = "1") Integer page,
				Model model) {
			 Admin authAdmin = (Admin) session.getAttribute("authAdmin");
			    if (authAdmin == null) {
			        System.out.println("セッション情報なし。ログインページへリダイレクト");
			        return "redirect:/"; // 管理者のログイン状態を確認。ログインしていなければトップページへリダイレクト。
			    }
			    
			    System.out.println("ログイン済みユーザー：" + authAdmin.getLoginId());
			model.addAttribute("botanicList", service.getItemsByPage(page, NUM_PER_PAGE));  // 植物リストを取得し、ビューに渡す
			model.addAttribute("page", page);
			session.setAttribute("page", page);
			model.addAttribute("totalPages", service.getTotalPages(NUM_PER_PAGE));
			return "list";
		}
		
		@GetMapping("/detail/{id}")    // id に対応する Botanic オブジェクトを取得し、detail ビューへ渡す
		public String showBotanicDetail(@PathVariable Integer id,
				Model model) {
			model.addAttribute("botanic", service.getBotanicById(id));
			return "detail";
		}
		
		@GetMapping("/add")            // 空の Botanic オブジェクトと、ロケーションリストを save ビューに渡す
		public String showAddForm(Model model) {
			model.addAttribute("botanic", new Botanic());
			model.addAttribute("locationList", service.getBotanicLocations());
			return "save";
		}
		
		@PostMapping("/add")           // バリデーション (@Valid) を実施し、エラーがあれば save ビューへ戻る
		public String addBotanic(@Valid Botanic botanic,
				Errors errors,       
				Model model,
				RedirectAttributes ra) {
			if(errors.hasErrors()) {        
				model.addAttribute("locationList", service.getBotanicLocations());
				return "save";
			}
			
			 // 追加: 重複チェック
		    if (service.isDuplicate(botanic.getBotanicName(), botanic.getBotanicCpl())) {
		        model.addAttribute("locationList", service.getBotanicLocations());
		        model.addAttribute("duplicateError", "同じ名前やCPLが既に存在します。");
		        return "save";
		    }
			
			// エラーがなければ service.addBotanic(botanic) で植物を追加し、リダイレクト時に「追加しました」のメッセージを表示
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
		
		@PostMapping("/edit/{id}")   // 指定 id の Botanic を取得し、edit ビューに渡す
		public String editBotanic(@PathVariable Integer id,
				@Valid Botanic botanic,
				Errors errors,
				Model model,
				RedirectAttributes ra) {
			if(errors.hasErrors()) {
				model.addAttribute("locationList", service.getBotanicLocations());
				return "edit";       // 正常ならデータを更新し、詳細ページにリダイレクト
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
		