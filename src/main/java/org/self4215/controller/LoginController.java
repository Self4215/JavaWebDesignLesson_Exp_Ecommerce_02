package org.self4215.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    // 访问 /login 时返回登录页
    @GetMapping("/login")
    public String login() {
        return "login"; // 对应 templates/login.html
    }
}