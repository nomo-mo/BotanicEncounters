package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Login;
import com.example.app.domain.User;
import com.example.app.filter.LoginAuthority;
import com.example.app.filter.LoginStatus;
import com.example.app.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserLoginController {

	private final UserService userService;
	private final HttpSession session;

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute(new Login());
		return "login-user";
	}

	@PostMapping("/login")
	public String login(
			@Valid Login login,
			Errors errors) throws Exception {
		if(errors.hasErrors()) {
			return "login-user";
		}

		// 正しいIDとパスワードの組み合わせか確認
		User user = userService.getUserByLoginId(login.getLoginId());
		if(user == null || !login.isCorrectPassword(user.getLoginPass())) {
			errors.rejectValue("loginId", "error.incorrect_id_or_password");
			return "login-user";
		}

		// セッションに認証情報を格納
		LoginStatus loginStatus = LoginStatus.builder()
				.id(user.getId())
				.name(user.getName())
				.loginId(user.getLoginId())
				.authority(LoginAuthority.USER)
				.build();
		session.setAttribute("loginStatus", loginStatus);
		System.out.println("ログイン成功: " + loginStatus.getName()); // デバッグ用
		
		return "redirect:/botanicals/favorite/list";
	}

	@GetMapping("/logout")
	public String logout(
			RedirectAttributes redirectAttributes) {
		session.removeAttribute("loginStatus");
		redirectAttributes.addFlashAttribute("message", "ログアウトしました。");
		return "redirect:/login";
	}

}