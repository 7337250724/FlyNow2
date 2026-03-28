package com.example.demo.controller;

import com.example.demo.dto.PackageSummaryDTO;
import com.example.demo.model.TravelPackage;
import com.example.demo.model.User;
import com.example.demo.service.TravelPackageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/packages")
public class AdminPackageController {

    @Autowired
    private TravelPackageService packageService;

    // Get all packages (lightweight for list view)
    @GetMapping
    public ResponseEntity<?> getAllPackages(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        // Use lightweight DTO method that doesn't load all relationships
        List<PackageSummaryDTO> packages = packageService.getAllPackagesForList();
        return ResponseEntity.ok(packages);
    }

    // Get package by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPackageById(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Optional<TravelPackage> packageOpt = packageService.getPackageById(id);
        if (packageOpt.isPresent()) {
            return ResponseEntity.ok(packageOpt.get());
        } else {
            return ResponseEntity.status(404).body("Package not found");
        }
    }

    // Create package
    @PostMapping
    public ResponseEntity<?> createPackage(@RequestBody Map<String, Object> request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            TravelPackage travelPackage = new TravelPackage();
            travelPackage.setName((String) request.get("name"));
            travelPackage.setLocation((String) request.get("location"));
            travelPackage.setPackageType((String) request.get("packageType")); // "DOMESTIC" or "INTERNATIONAL"
            travelPackage.setHeroImageUrl((String) request.get("heroImageUrl"));
            
            if (request.get("durationDays") != null) {
                travelPackage.setDurationDays(((Number) request.get("durationDays")).intValue());
            }
            
            if (request.get("price") != null) {
                travelPackage.setPrice(((Number) request.get("price")).doubleValue());
            }
            
            // Set standardPrice and luxuryPrice
            if (request.get("standardPrice") != null) {
                travelPackage.setStandardPrice(((Number) request.get("standardPrice")).doubleValue());
            } else if (request.get("price") != null) {
                travelPackage.setStandardPrice(((Number) request.get("price")).doubleValue());
            }
            
            if (request.get("luxuryPrice") != null) {
                travelPackage.setLuxuryPrice(((Number) request.get("luxuryPrice")).doubleValue());
            } else if (request.get("price") != null) {
                travelPackage.setLuxuryPrice(((Number) request.get("price")).doubleValue() * 1.5);
            }
            
            travelPackage.setPriceCurrency((String) request.get("priceCurrency"));
            travelPackage.setShortDescription((String) request.get("shortDescription"));
            travelPackage.setAboutDescription((String) request.get("aboutDescription"));
            
            // Travel type fields
            travelPackage.setTravelTypes((String) request.get("travelTypes"));
            if (request.get("isFamilyFriendly") != null) {
                travelPackage.setIsFamilyFriendly((Boolean) request.get("isFamilyFriendly"));
            }
            if (request.get("isCoupleFriendly") != null) {
                travelPackage.setIsCoupleFriendly((Boolean) request.get("isCoupleFriendly"));
            }
            if (request.get("isCorporate") != null) {
                travelPackage.setIsCorporate((Boolean) request.get("isCorporate"));
            }
            if (request.get("isAdventure") != null) {
                travelPackage.setIsAdventure((Boolean) request.get("isAdventure"));
            }
            if (request.get("isSpiritual") != null) {
                travelPackage.setIsSpiritual((Boolean) request.get("isSpiritual"));
            }
            
            // Seasons field
            travelPackage.setSeasons((String) request.get("seasons"));
            
            // Visa requirement only for international packages
            if ("INTERNATIONAL".equals(travelPackage.getPackageType())) {
                travelPackage.setVisaRequirement((String) request.get("visaRequirement"));
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> itineraryDays = (List<Map<String, Object>>) request.get("itineraryDays");
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> features = (List<Map<String, Object>>) request.get("features");
            
            @SuppressWarnings("unchecked")
            List<String> galleryImageUrls = (List<String>) request.get("galleryImageUrls");

            TravelPackage savedPackage = packageService.createPackage(travelPackage, itineraryDays, features, galleryImageUrls);
            return ResponseEntity.ok(savedPackage);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating package: " + e.getMessage());
        }
    }

    // Update package
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePackage(@PathVariable Long id, @RequestBody Map<String, Object> request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            TravelPackage travelPackage = new TravelPackage();
            travelPackage.setName((String) request.get("name"));
            travelPackage.setLocation((String) request.get("location"));
            travelPackage.setPackageType((String) request.get("packageType"));
            travelPackage.setHeroImageUrl((String) request.get("heroImageUrl"));
            
            if (request.get("durationDays") != null) {
                travelPackage.setDurationDays(((Number) request.get("durationDays")).intValue());
            }
            
            if (request.get("price") != null) {
                travelPackage.setPrice(((Number) request.get("price")).doubleValue());
            }
            
            // Set standardPrice and luxuryPrice
            if (request.get("standardPrice") != null) {
                travelPackage.setStandardPrice(((Number) request.get("standardPrice")).doubleValue());
            } else if (request.get("price") != null) {
                travelPackage.setStandardPrice(((Number) request.get("price")).doubleValue());
            }
            
            if (request.get("luxuryPrice") != null) {
                travelPackage.setLuxuryPrice(((Number) request.get("luxuryPrice")).doubleValue());
            } else if (request.get("price") != null) {
                travelPackage.setLuxuryPrice(((Number) request.get("price")).doubleValue() * 1.5);
            }
            
            travelPackage.setPriceCurrency((String) request.get("priceCurrency"));
            travelPackage.setShortDescription((String) request.get("shortDescription"));
            travelPackage.setAboutDescription((String) request.get("aboutDescription"));
            
            // Travel type fields
            travelPackage.setTravelTypes((String) request.get("travelTypes"));
            if (request.get("isFamilyFriendly") != null) {
                travelPackage.setIsFamilyFriendly((Boolean) request.get("isFamilyFriendly"));
            }
            if (request.get("isCoupleFriendly") != null) {
                travelPackage.setIsCoupleFriendly((Boolean) request.get("isCoupleFriendly"));
            }
            if (request.get("isCorporate") != null) {
                travelPackage.setIsCorporate((Boolean) request.get("isCorporate"));
            }
            if (request.get("isAdventure") != null) {
                travelPackage.setIsAdventure((Boolean) request.get("isAdventure"));
            }
            if (request.get("isSpiritual") != null) {
                travelPackage.setIsSpiritual((Boolean) request.get("isSpiritual"));
            }
            
            // Seasons field
            travelPackage.setSeasons((String) request.get("seasons"));
            
            if ("INTERNATIONAL".equals(travelPackage.getPackageType())) {
                travelPackage.setVisaRequirement((String) request.get("visaRequirement"));
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> itineraryDays = (List<Map<String, Object>>) request.get("itineraryDays");
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> features = (List<Map<String, Object>>) request.get("features");
            
            @SuppressWarnings("unchecked")
            List<String> galleryImageUrls = (List<String>) request.get("galleryImageUrls");

            TravelPackage updatedPackage = packageService.updatePackage(id, travelPackage, itineraryDays, features, galleryImageUrls);
            return ResponseEntity.ok(updatedPackage);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating package: " + e.getMessage());
        }
    }

    // Delete package
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            packageService.deletePackage(id);
            return ResponseEntity.ok("Package deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting package: " + e.getMessage());
        }
    }
}

