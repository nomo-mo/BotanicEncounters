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
    @GetMapping("/{botanicName}")
    public String showFavorite(@PathVariable String botanicName, Model model) {
        Botanic botanic = botanicService.findByName(botanicName);
        if (botanic != null) {
            model.addAttribute("botanicName", botanic.getBotanicName());
            model.addAttribute("imagePath", botanic.getImagePath());
            model.addAttribute("botanicalId", botanic.getId()); // Add the botanicId to the model
        } else {
            model.addAttribute("botanicName", botanicName);
            model.addAttribute("imagePath", "/static/images/default.jpg"); // Default image if not found
        }
        return "favorite_save";
    }

 // お気に入り登録
    @PostMapping("/{botanicName}/favorite")
    public String addFavorite(@PathVariable String botanicName, RedirectAttributes ra, Principal principal) {
        String userName = principal.getName(); // ログイン中のユーザー名を取得
        User user = userService.findByName(userName); // ユーザー情報を取得
        Botanic botanic = botanicService.findByName(botanicName);
        String imagePath = (botanic != null) ? botanic.getImagePath() : "/static/images/default.jpg";

        favoriteService.addFavorite(user.getId(), botanic.getId(), imagePath);
        ra.addFlashAttribute("status", "お気に入りに登録しました");
        return "redirect:/botanicals/favorite/list";
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

