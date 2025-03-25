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

    @GetMapping("/{id}")
    public String showFavorite(@PathVariable Integer id, Model model) {
        model.addAttribute("botanicalId", id);
        return "favorite_detail"; // `favorite_detail.html` に遷移
    }

    @PostMapping("/{id}/favorite")
    public String addFavorite(@PathVariable Integer id, RedirectAttributes ra) {
        // ログインしていないユーザーでもお気に入りを登録できるようにする
        favoriteService.addFavorite(null, id);  // `null`を渡すか、ユーザーIDなしで処理
        ra.addFlashAttribute("status", "お気に入りに登録しました");
        return "redirect:/favorite_detail/" + id;
    }

    // **お気に入り削除**
    @PostMapping("/{id}/unfavorite")
    public String removeFavorite(@PathVariable Integer id, RedirectAttributes ra) {
        // ログインしていないユーザーでもお気に入り解除できるようにする
        favoriteService.removeFavorite(null, id);  // 同様に`null`を渡す
        ra.addFlashAttribute("status", "お気に入りを解除しました");
        return "redirect:/favorite_detail/" + id;
    }
}


