package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Admin;
import com.example.app.service.AdminService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminController {
	
	private final AdminService service;                      //管理者認証処理
	private final HttpSession session;                       //ログインした管理者情報をセッションに保存するための HttpSession インスタンス
	
	@GetMapping                                          // ルート URL (/) への GET リクエストを処理
	public String showLoginForm(Model model) {
		model.addAttribute("admin", new Admin());            // 空の Admin オブジェクトを作成し、Thymeleaf のフォームバインディングで使用する
		return "login";                                     // src/main/resources/templates/login.html を表示
	}
	
	@PostMapping("/")                                                                        // フォーム送信（POST）を処理
	public String loginCheck(@Valid Admin admin,Errors errors,RedirectAttributes ra) {  // フォームの入力値を Admin オブジェクトにバインドし、バリデーションを実行
		if(errors.hasErrors()) {  
			return "login";                                                             // バリデーションエラーがあれば、再び login.html を表示し、エラー内容を反映
		}
		
		Admin authAdmin = service.getAuthenticatedAdmin(admin.getLoginId(), admin.getLoginPass());
		if(authAdmin == null) {                                  // service.getAuthenticatedAdmin(loginId, loginPass) を呼び出し、
			errors.rejectValue("loginId", "wrong_id_or_pass");   // データベース内の管理者情報と一致するか確認
			return "login";                                      // エラーメッセージを設定し、login.html に戻る
		}
		
		session.setAttribute("authAdmin", authAdmin);		     // HttpSession に認証済み管理者情報を保存し、ログイン状態を維持		
		ra.addFlashAttribute("status", "ログインしました");
		System.out.println("ログイン成功！/plants へリダイレクト"); // デバッグログ
		return "redirect:/plants";                               // /plants へリダイレクト（ログイン後のページ）
	}
	
	@GetMapping("/admin/logout")
	public String logout(RedirectAttributes ra) {
		session.invalidate();
		ra.addFlashAttribute("status", "ログアウトしました");
		return "redirect:/";                                      // トップページへリダイレクト
	}
	
}



