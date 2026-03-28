package com.example.demo.controller;

import com.example.demo.model.PackageReview;
import com.example.demo.service.PackageReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PackageReviewService reviewService;

    @GetMapping(value = {"/", "/index", "/index.html"})
    public String home(Model model) {
        try {
            // Fetch approved reviews for homepage display
            List<PackageReview> reviews = reviewService.getApprovedReviews();
            // Limit to latest 6 reviews for homepage
            if (reviews != null && reviews.size() > 6) {
                reviews = reviews.subList(0, 6);
            }
            model.addAttribute("reviews", reviews != null ? reviews : Collections.emptyList());
        } catch (Exception e) {
            // If there's an error fetching reviews, just show empty list
            model.addAttribute("reviews", Collections.emptyList());
        }
        return "index1"; // templates/index1.html - Homepage template
    }
}
