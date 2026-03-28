package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private PostService postService;

    // Root path "/" is now handled by HomeController
    // This controller handles feed and post creation

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/feed")
    public String feed(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("posts", postService.getAllPosts());
        return "feed";
    }

    @GetMapping("/create-post")
    public String createPostPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("post", new Post());
        return "create-post";
    }

    @PostMapping("/create-post")
    public String createPost(@ModelAttribute Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        post.setUser(user);
        // Set title from content if title is empty
        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            if (post.getContent() != null && !post.getContent().isEmpty()) {
                String content = post.getContent();
                post.setTitle(content.length() > 50 ? content.substring(0, 50) + "..." : content);
            } else {
                post.setTitle("Untitled Post");
            }
        }
        postService.createPost(post);
        return "redirect:/feed";
    }
}
