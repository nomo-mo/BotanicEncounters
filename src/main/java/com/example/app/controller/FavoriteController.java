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
    public String showFavoriteList(Model model) {
        model.addAttribute("favorites", favoriteService.getUserFavorites(null));
        return "favorite_list";
    }

    // **お気に入り詳細を表示**
    @GetMapping("/{id}")
    public String showFavorite(@PathVariable Integer id, Model model) {
    	Botanic botanic = botanicService.getBotanicById(id); // IDで検索
        if (botanic != null) {
            model.addAttribute("botanicName", botanic.getBotanicName());
            model.addAttribute("imagePath", botanic.getImagePath());
            model.addAttribute("botanicalId", botanic.getId()); // IDもモデルに追加
        } else {
            model.addAttribute("botanicName", "不明な植物");
            model.addAttribute("imagePath", "/static/images/default.jpg"); // デフォルト画像
        }
        return "favorite_save";
    }

 // お気に入り登録
    @PostMapping("/{botanicName}/favorite")
    public String addFavorite(@PathVariable String botanicName, RedirectAttributes ra, Principal principal) {
        // Principal が null の場合、ログインしていないユーザーとして処理
        if (principal == null) {
            ra.addFlashAttribute("status", "ログインが必要です");
            return "redirect:/botanicalsList";  // ログインページにリダイレクト
        }

        String userName = principal.getName(); // ログイン中のユーザー名を取得
        User user = userService.findByName(userName); // ユーザー情報を取得
        Botanic botanic = botanicService.findByName(botanicName);

        if (botanic == null) {
            ra.addFlashAttribute("status", "植物が見つかりませんでした");
            return "redirect:/botanicals/favorite/list"; // お気に入りリストへ遷移
        }

        // **重複登録を防ぐ**
        if (favoriteService.isFavorite(user.getId(), botanic.getId())) {
            ra.addFlashAttribute("status", "既にお気に入りに登録済みです");
            return "redirect:/botanicals/favorite/list"; // お気に入りリストへ遷移
        }

        // お気に入り登録処理
        String imagePath = botanic.getImagePath() != null ? botanic.getImagePath() : "/static/images/default.jpg";
        favoriteService.addFavorite(user.getId(), botanic.getId(), imagePath);
        
        ra.addFlashAttribute("status", "お気に入りに登録しました");

        return "redirect:/favorite/list"; // お気に入りリストへリダイレクト
    }




    // お気に入り削除
    @PostMapping("/{botanicName}/unfavorite")
    public String removeFavorite(@PathVariable String botanicName, RedirectAttributes ra, Principal principal) {
        String userName = principal.getName(); // ログイン中のユーザー名を取得
        User user = userService.findByName(userName);
        Botanic botanic = botanicService.findByName(botanicName);

        favoriteService.removeFavorite(user.getId(), botanic.getId());
        ra.addFlashAttribute("status", "お気に入りを解除しました");
        return "redirect:/botanicals/favorite/list";
    }


}

