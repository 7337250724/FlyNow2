package com.example.demo.controller;

import com.example.demo.model.Country;
import com.example.demo.model.User;
import com.example.demo.service.CountryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private CountryService countryService;

    // Admin endpoints
    @PostMapping("/admin/countries")
    public ResponseEntity<?> createCountry(@RequestBody Map<String, Object> request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Country country = new Country();
            country.setName((String) request.get("name"));
            country.setDescription((String) request.get("description"));
            country.setHeroImage((String) request.get("heroImage"));
            country.setRegion((String) request.get("region"));
            
            // Set seasons (comma-separated string)
            Object seasonsObj = request.get("seasons");
            if (seasonsObj != null) {
                if (seasonsObj instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<String> seasonsList = (List<String>) seasonsObj;
                    if (!seasonsList.isEmpty()) {
                        country.setSeasons(String.join(",", seasonsList));
                    } else {
                        country.setSeasons(null);
                    }
                } else if (seasonsObj instanceof String) {
                    String seasonsStr = (String) seasonsObj;
                    if (!seasonsStr.trim().isEmpty()) {
                        country.setSeasons(seasonsStr);
                    } else {
                        country.setSeasons(null);
                    }
                }
            } else {
                country.setSeasons(null);
            }

            @SuppressWarnings("unchecked")
            List<String> imageUrls = (List<String>) request.get("imageUrls");

            Country savedCountry = countryService.createCountry(country, imageUrls);
            return ResponseEntity.ok(savedCountry);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating country: " + e.getMessage());
        }
    }

    @PutMapping("/admin/countries/{id}")
    public ResponseEntity<?> updateCountry(@PathVariable Long id, @RequestBody Map<String, Object> request,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Country country = new Country();
            country.setName((String) request.get("name"));
            country.setDescription((String) request.get("description"));
            country.setHeroImage((String) request.get("heroImage"));
            country.setRegion((String) request.get("region"));
            
            // Set seasons (comma-separated string)
            Object seasonsObj = request.get("seasons");
            if (seasonsObj != null) {
                if (seasonsObj instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<String> seasonsList = (List<String>) seasonsObj;
                    if (!seasonsList.isEmpty()) {
                        country.setSeasons(String.join(",", seasonsList));
                    } else {
                        country.setSeasons(null);
                    }
                } else if (seasonsObj instanceof String) {
                    String seasonsStr = (String) seasonsObj;
                    if (!seasonsStr.trim().isEmpty()) {
                        country.setSeasons(seasonsStr);
                    } else {
                        country.setSeasons(null);
                    }
                }
            } else {
                country.setSeasons(null);
            }

            @SuppressWarnings("unchecked")
            List<String> imageUrls = (List<String>) request.get("imageUrls");

            Country updatedCountry = countryService.updateCountry(id, country, imageUrls);
            return ResponseEntity.ok(updatedCountry);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating country: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/countries/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            countryService.deleteCountry(id);
            return ResponseEntity.ok("Country deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting country: " + e.getMessage());
        }
    }

    @GetMapping("/admin/countries")
    public ResponseEntity<?> getAllCountriesAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            // Use lightweight method that doesn't load all relationships
            List<Country> countries = countryService.getAllCountriesForList();
            return ResponseEntity.ok(countries);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching countries: " + e.getMessage());
        }
    }

    @GetMapping("/admin/countries/{id}")
    public ResponseEntity<?> getCountryByIdAdmin(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Country country = countryService.getCountryById(id);
            return ResponseEntity.ok(country);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Country not found");
        }
    }

    // Public user endpoints
    @GetMapping("/countries")
    public ResponseEntity<?> getAllCountries() {
        try {
            List<Country> countries = countryService.getAllCountries();
            return ResponseEntity.ok(countries);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching countries: " + e.getMessage());
        }
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable Long id) {
        try {
            Country country = countryService.getCountryById(id);

            // Create response with country and its places
            Map<String, Object> response = new HashMap<>();
            response.put("id", country.getId());
            response.put("name", country.getName());
            response.put("description", country.getDescription());
            response.put("heroImage", country.getHeroImage());
            response.put("region", country.getRegion());
            response.put("seasons", country.getSeasons());
            response.put("places", country.getPlaces());
            response.put("images", country.getImages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Country not found");
        }
    }
}
