package com.example.app.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminAuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        // **URLチェック**: `/admin/` 配下のページのみ認証チェックを行う
        String requestURI = req.getRequestURI();
        if (requestURI.startsWith("/admin/")) {
            if (session.getAttribute("authAdmin") == null) {
                res.sendRedirect("/adminLOGIN");  // **管理者ログインページにリダイレクト**
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
