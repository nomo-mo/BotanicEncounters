package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.service.FavoriteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/botanicals/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/{botanicName}")
    public String showFavorite(@PathVariable String botanicName, Model model) {
        model.addAttribute("botanicName", botanicName);
        return "favorite_save"; // `favorite_save.html` に遷移
    }

    @PostMapping("/{botanicName}/favorite")
    public String addFavorite(@PathVariable String botanicName, RedirectAttributes ra) {
        // 仮の画像パス（実際はデータベースから取得するなどの処理が必要）
        String imagePath = "/static/images/default.jpg";

        // ログインしていないユーザーでもお気に入りを登録できるようにする
        favoriteService.addFavorite(null, botanicName, imagePath);
        ra.addFlashAttribute("status", "お気に入りに登録しました");
        return "redirect:/botanicals/favorite/" + botanicName;
    }

    // **お気に入り削除**
    @PostMapping("/{botanicName}/unfavorite")
    public String removeFavorite(@PathVariable String botanicName, RedirectAttributes ra) {
        // ログインしていないユーザーでもお気に入り解除できるようにする
        favoriteService.removeFavorite(null, botanicName);
        ra.addFlashAttribute("status", "お気に入りを解除しました");
        return "redirect:/botanicals/favorite/" + botanicName;
    }
}



