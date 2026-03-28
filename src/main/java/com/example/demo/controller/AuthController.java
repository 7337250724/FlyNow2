package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "redirect", required = false) String redirect, Model model) {
        if (redirect != null) {
            model.addAttribute("redirect", redirect);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, 
            @RequestParam(name = "redirect", required = false) String redirect,
            HttpSession session, Model model) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            if ("admin".equals(user.getUsername())) {
                return "redirect:/admin.html?loginSuccess=true";
            }
            // If redirect parameter is provided, redirect there with success message
            if (redirect != null && !redirect.isEmpty()) {
                return "redirect:" + redirect + "?loginSuccess=true";
            }
            // Show success popup on login page, then redirect to packages
            return "redirect:/login?loginSuccess=true";
        }
        model.addAttribute("error", "Invalid username or password");
        if (redirect != null) {
            model.addAttribute("redirect", redirect);
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, HttpSession session) {
        try {
            User registeredUser = userService.registerUser(user);
            session.setAttribute("user", registeredUser);
            return "redirect:/packages";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/register?error=failed";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/api/check-admin")
    @org.springframework.web.bind.annotation.ResponseBody
    public org.springframework.http.ResponseEntity<String> checkAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && "admin".equals(user.getUsername())) {
            return org.springframework.http.ResponseEntity.ok("Authorized");
        }
        return org.springframework.http.ResponseEntity.status(401).body("Unauthorized");
    }
}
