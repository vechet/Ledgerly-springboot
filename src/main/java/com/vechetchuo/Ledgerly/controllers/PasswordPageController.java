package com.vechetchuo.Ledgerly.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordPageController {

    @GetMapping("/reset-password")
    public String showResetPage(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password"; // refers to reset-password.html
    }
}

