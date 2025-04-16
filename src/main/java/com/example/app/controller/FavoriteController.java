package com.example.app.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Botanic;
import com.example.app.domain.User;
import com.example.app.filter.LoginAuthority;
import com.example.app.filter.LoginStatus;
import com.example.app.service.BotanicService;
import com.example.app.service.FavoriteService;
import com.example.app.service.UserService;

import jakarta.servlet.http.HttpSession;
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
	public String showFavoriteList(Model model, HttpSession session) {
		LoginStatus loginStatus = (LoginStatus) session.getAttribute("loginStatus");
		if (loginStatus == null) {
			System.out.println("ログイン状態が確認できません。ログインページへリダイレクト");
			return "redirect:/user/login"; // **ユーザーログインページへリダイレクト**
		}

		Integer userId = loginStatus.getId();
		model.addAttribute("favorites", favoriteService.getUserFavorites(userId));
		return "favorite_list";
	}

	@GetMapping("/botanicalsList")
	public String showBotanicalList(Model model, HttpSession session) {
		LoginStatus loginStatus = (LoginStatus) session.getAttribute("loginStatus");
		if (loginStatus != null) {
			model.addAttribute("user", loginStatus); // ← userという名前で渡す
		} else {
			model.addAttribute("user", null);
		}

		// その他の表示データなど
		return "botanicalsList";
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

	@PostMapping("/favorite/add")
	public String addFavorite(
			@RequestParam("botanicId") Integer botanicId,
			@RequestParam("imagePath") String imagePath,
			HttpSession session) {
		// セッションからログインユーザー情報を取得
		LoginStatus loginStatus = (LoginStatus) session.getAttribute("loginStatus");

		if (loginStatus == null || loginStatus.getAuthority() != LoginAuthority.USER) {
			return "redirect:/user_login"; // ログインしてない or USERでない
		}

		Integer userId = loginStatus.getUserId(); // ←ここ大事

		// 登録処理
		favoriteService.addFavorite(userId, botanicId, imagePath);

		return "redirect:/botanicList";
	}
}
