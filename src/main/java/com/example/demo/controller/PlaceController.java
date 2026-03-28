package com.example.demo.controller;

import com.example.demo.model.Place;
import com.example.demo.model.Country;
import com.example.demo.model.User;
import com.example.demo.service.PlaceService;
import com.example.demo.service.CountryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private CountryService countryService;

    // Admin endpoints
    @PostMapping("/admin/countries/{countryId}/places")
    public ResponseEntity<?> createPlace(@PathVariable Long countryId, @RequestBody Map<String, Object> request,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Country country = countryService.getCountryById(countryId);

            Place place = new Place();
            place.setName((String) request.get("name"));
            place.setDescription((String) request.get("description"));
            place.setCountry(country);

            @SuppressWarnings("unchecked")
            List<String> imageUrls = (List<String>) request.get("imageUrls");

            Place savedPlace = placeService.createPlace(place, imageUrls);
            return ResponseEntity.ok(savedPlace);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating place: " + e.getMessage());
        }
    }

    @PutMapping("/admin/places/{id}")
    public ResponseEntity<?> updatePlace(@PathVariable Long id, @RequestBody Map<String, Object> request,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Place existingPlace = placeService.getPlaceById(id);

            Place place = new Place();
            place.setName((String) request.get("name"));
            place.setDescription((String) request.get("description"));
            place.setCountry(existingPlace.getCountry());

            @SuppressWarnings("unchecked")
            List<String> imageUrls = (List<String>) request.get("imageUrls");

            Place updatedPlace = placeService.updatePlace(id, place, imageUrls);
            return ResponseEntity.ok(updatedPlace);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating place: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/places/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            placeService.deletePlace(id);
            return ResponseEntity.ok("Place deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting place: " + e.getMessage());
        }
    }

    @GetMapping("/admin/countries/{countryId}/places")
    public ResponseEntity<?> getPlacesByCountry(@PathVariable Long countryId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            List<Place> places = placeService.getPlacesByCountryId(countryId);
            return ResponseEntity.ok(places);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching places: " + e.getMessage());
        }
    }
}
