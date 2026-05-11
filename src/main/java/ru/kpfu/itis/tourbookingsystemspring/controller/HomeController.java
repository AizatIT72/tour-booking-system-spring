package ru.kpfu.itis.tourbookingsystemspring.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.tourbookingsystemspring.security.CustomUserDetails;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
        model.addAttribute("user", currentUser.getUser());
        return "home";
    }
}