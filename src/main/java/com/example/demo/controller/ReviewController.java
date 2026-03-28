package com.example.demo.controller;

import com.example.demo.model.PackageReview;
import com.example.demo.model.TravelPackage;
import com.example.demo.model.User;
import com.example.demo.repository.TravelPackageRepository;
import com.example.demo.service.PackageReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {

    @Autowired
    private PackageReviewService reviewService;

    @Autowired
    private TravelPackageRepository packageRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/reviews/";

    @GetMapping("/reviews")
    public String showReviewsPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            // Redirect to login with return URL
            return "redirect:/login?redirect=/reviews";
        }
        
        // Get all packages for dropdown
        List<TravelPackage> packages = packageRepository.findAll();
        model.addAttribute("packages", packages);
        
        // Get user's existing reviews
        List<PackageReview> userReviews = reviewService.getReviewsByUser(user.getId());
        model.addAttribute("userReviews", userReviews);
        
        return "reviews";
    }

    @GetMapping("/reviews/view")
    public String viewAllReviews(@RequestParam(name = "packageId", required = false) Long packageId,
                                 @RequestParam(name = "location", required = false) String location,
                                 Model model) {
        List<PackageReview> reviews;
        
        if (packageId != null) {
            reviews = reviewService.getApprovedReviewsByPackage(packageId);
        } else {
            reviews = reviewService.getApprovedReviews();
        }
        
        // Filter by location if provided
        if (location != null && !location.isEmpty()) {
            reviews = reviews.stream()
                    .filter(r -> r.getLocationVisited() != null && 
                               r.getLocationVisited().toLowerCase().contains(location.toLowerCase()))
                    .collect(java.util.stream.Collectors.toList());
        }
        
        // Get all packages for filter dropdown
        List<TravelPackage> packages = packageRepository.findAll();
        model.addAttribute("packages", packages);
        model.addAttribute("reviews", reviews);
        model.addAttribute("selectedPackageId", packageId);
        model.addAttribute("selectedLocation", location);
        
        return "reviews-view";
    }

    @PostMapping("/reviews/submit")
    public String submitReview(
            @RequestParam("packageId") Long packageId,
            @RequestParam("reviewText") String reviewText,
            @RequestParam("rating") Integer rating,
            @RequestParam("locationVisited") String locationVisited,
            @RequestParam(value = "visitDate", required = false) String visitDateStr,
            @RequestParam(value = "photos", required = false) MultipartFile[] photos,
            @RequestParam(value = "videos", required = false) MultipartFile[] videos,
            HttpSession session,
            Model model) {
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login?redirect=/reviews";
        }

        try {
            Optional<TravelPackage> packageOpt = packageRepository.findById(packageId);
            if (packageOpt.isEmpty()) {
                model.addAttribute("error", "Package not found");
                return "redirect:/reviews";
            }

            PackageReview review = new PackageReview();
            review.setTravelPackage(packageOpt.get());
            review.setUser(user);
            review.setReviewText(reviewText);
            review.setRating(rating != null ? rating : 5);
            review.setLocationVisited(locationVisited);
            
            // Parse visit date if provided
            if (visitDateStr != null && !visitDateStr.isEmpty()) {
                try {
                    review.setVisitDate(java.sql.Date.valueOf(visitDateStr));
                } catch (Exception e) {
                    // Invalid date format, skip
                }
            }

            // Handle photo uploads
            List<String> photoUrls = new ArrayList<>();
            if (photos != null && photos.length > 0) {
                for (MultipartFile photo : photos) {
                    if (!photo.isEmpty()) {
                        String photoUrl = saveFile(photo, "photo");
                        if (photoUrl != null) {
                            photoUrls.add(photoUrl);
                        }
                    }
                }
            }
            if (!photoUrls.isEmpty()) {
                review.setPhotoUrls(String.join(",", photoUrls));
            }

            // Handle video uploads
            List<String> videoUrls = new ArrayList<>();
            if (videos != null && videos.length > 0) {
                for (MultipartFile video : videos) {
                    if (!video.isEmpty()) {
                        String videoUrl = saveFile(video, "video");
                        if (videoUrl != null) {
                            videoUrls.add(videoUrl);
                        }
                    }
                }
            }
            if (!videoUrls.isEmpty()) {
                review.setVideoUrls(String.join(",", videoUrls));
            }

            // Set review as approved automatically
            review.setIsApproved(true);
            reviewService.createReview(review);
            model.addAttribute("success", "Your review has been submitted successfully! It is now visible to all users.");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error submitting review: " + e.getMessage());
        }

        return "redirect:/reviews?success=true";
    }

    private String saveFile(MultipartFile file, String type) {
        try {
            // Create upload directory if it doesn't exist
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? 
                originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String filename = type + "_" + System.currentTimeMillis() + extension;

            // Save file
            Path path = Paths.get(UPLOAD_DIR + filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Return URL path (relative to static folder)
            return "/uploads/reviews/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

