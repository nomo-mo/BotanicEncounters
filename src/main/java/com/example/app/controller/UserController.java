package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.User;
import com.example.app.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    // ユーザーログイン画面を表示
    @GetMapping("/user/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "user_login";  // user_login.html へ遷移
    }

    // ユーザーログイン処理
    @PostMapping("/user/login")
    public String loginCheck(@Valid User user, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return "user_login";
        }

        User authUser = userService.getAuthenticatedUser(user.getLoginId(), user.getLoginPass());
        if (authUser == null) {
            errors.rejectValue("loginId", "wrong_id_or_pass", "ログインIDまたはパスワードが間違っています");
            return "user_login";
        }

        session.setAttribute("userSession", authUser);    // 一般ユーザー 
        ra.addFlashAttribute("status", "ログインしました");
        System.out.println("ユーザーログイン成功！ /home へリダイレクト");
        return "redirect:/home"; // ホームページへリダイレクト
    }

    // ユーザーログアウト
    @GetMapping("/user/logout")
    public String userLogout(RedirectAttributes ra) {
        session.removeAttribute("userSession");  // 一般ユーザーのセッションのみ削除
        ra.addFlashAttribute("status", "ログアウトしました");
        return "redirect:/user/login";
    }
}

