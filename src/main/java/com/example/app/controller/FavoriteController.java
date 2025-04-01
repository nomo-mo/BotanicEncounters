package com.example.app.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Botanic;
import com.example.app.domain.User;
import com.example.app.service.BotanicService;
import com.example.app.service.FavoriteService;
import com.example.app.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/botanicals/favorite")
public class FavoriteController {

	private final FavoriteService favoriteService;
	private final BotanicService botanicService; // 植物情報を取得するためのサービス
	private final UserService userService;

	// お気に入り一覧を表示
	@GetMapping("/list")
	public String showFavoriteList(Model model, Principal principal) {
		 if (principal == null) {
		        System.out.println("principalがnullです。ログインページへリダイレクト");
		        return "redirect:/user/login";  // **ユーザーログインページへリダイレクト**
		    }

		    String userName = principal.getName();
		    System.out.println("取得したユーザー名: " + userName);

		User user = userService.findByName(userName);
	    if (user == null) {
	        System.out.println("ユーザーが見つかりません。ログインページへリダイレクト");
	        return "redirect:/user/login";
	    }

	    model.addAttribute("favorites", favoriteService.getUserFavorites(user.getId()));
	    return "favorite_list";
	}



	// **お気に入り詳細を表示**
	@GetMapping("/{id}")
	public String showFavorite(@PathVariable Integer id, Model model) {
		Botanic botanic = botanicService.getBotanicById(id);
		if (botanic != null) {
			model.addAttribute("botanicName", botanic.getBotanicName());
			model.addAttribute("imagePath", botanic.getImagePath());
			model.addAttribute("botanicalId", botanic.getId());
		} else {
			model.addAttribute("botanicName", "不明な植物");
			model.addAttribute("imagePath", "/static/images/default.jpg");
		}
		return "favorite_save";
	}

	// お気に入り登録
	@PostMapping("/{botanicId}/favorite")
	public String addFavorite(@PathVariable Integer botanicId, RedirectAttributes ra, Principal principal) {
		if (principal == null) {
			ra.addFlashAttribute("status", "ログインが必要です");
			return "redirect:/user/login"; 
		}

		String loginId = principal.getName();
		User user = userService.findByName(loginId);
		Botanic botanic = botanicService.getBotanicById(botanicId);

		if (botanic == null) {
			ra.addFlashAttribute("status", "植物が見つかりません");
			return "redirect:/botanicals/favorite/list";
		}

		// **重複登録を防ぐ**
		if (favoriteService.isFavorite(user.getId(), botanic.getId())) {
			ra.addFlashAttribute("status", "既にお気に入りに登録済みです");
			return "redirect:/botanicals/favorite/list";
		}

		// お気に入り登録処理
		favoriteService.addFavorite(user.getId(), botanic.getId(), botanic.getImagePath());

		ra.addFlashAttribute("status", "お気に入りに登録しました");
		return "redirect:/botanicals/favorite/list";
	}

	// お気に入り削除
	@PostMapping("/{botanicId}/unfavorite")
	public String removeFavorite(@PathVariable Integer botanicId, RedirectAttributes ra, Principal principal) {
	    if (principal == null) {
	        ra.addFlashAttribute("status", "ログインが必要です");
	        return "redirect:/botanicals/favorite/list";
	    }

	    String userName = principal.getName();
	    User user = userService.findByName(userName);
	    Botanic botanic = botanicService.getBotanicById(botanicId);

	    if (botanic == null) {
	        ra.addFlashAttribute("status", "植物が見つかりませんでした");
	        return "redirect:/botanicals/favorite/list";
	    }

	    favoriteService.removeFavorite(user.getId(), botanic.getId());
	    ra.addFlashAttribute("status", "お気に入りを解除しました");
	    return "redirect:/botanicals/favorite/list";
	}


}
