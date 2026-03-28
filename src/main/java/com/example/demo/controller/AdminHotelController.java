package com.example.demo.controller;

import com.example.demo.dto.HotelSummaryDTO;
import com.example.demo.model.Hotel;
import com.example.demo.model.User;
import com.example.demo.service.HotelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/hotels")
public class AdminHotelController {

    @Autowired
    private HotelService hotelService;

    // Get all hotels (lightweight for list view)
    @GetMapping
    public ResponseEntity<?> getAllHotels(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<HotelSummaryDTO> hotels = hotelService.getAllHotelsForList();
        return ResponseEntity.ok(hotels);
    }

    // Get hotel by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Optional<Hotel> hotelOpt = hotelService.getHotelById(id);
        if (hotelOpt.isPresent()) {
            return ResponseEntity.ok(hotelOpt.get());
        } else {
            return ResponseEntity.status(404).body("Hotel not found");
        }
    }

    // Create hotel
    @PostMapping
    public ResponseEntity<?> createHotel(@RequestBody Map<String, Object> request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Hotel hotel = new Hotel();
            hotel.setName((String) request.get("name"));
            hotel.setLocation((String) request.get("location"));
            hotel.setHotelType((String) request.get("hotelType")); // "DOMESTIC" or "INTERNATIONAL"
            hotel.setCountry((String) request.get("country"));
            hotel.setCity((String) request.get("city"));
            hotel.setHeroImageUrl((String) request.get("heroImageUrl"));

            if (request.get("pricePerNight") != null) {
                hotel.setPricePerNight(((Number) request.get("pricePerNight")).doubleValue());
            }

            hotel.setPriceCurrency((String) request.get("priceCurrency"));

            if (request.get("rating") != null) {
                hotel.setRating(((Number) request.get("rating")).doubleValue());
            }
            
            // Set starRating and roomType
            if (request.get("starRating") != null) {
                hotel.setStarRating(((Number) request.get("starRating")).intValue());
            }
            
            hotel.setRoomType((String) request.get("roomType"));

            hotel.setDescription((String) request.get("description"));
            hotel.setAmenities((String) request.get("amenities"));
            hotel.setCheckInTime((String) request.get("checkInTime"));
            hotel.setCheckOutTime((String) request.get("checkOutTime"));
            hotel.setContactPhone((String) request.get("contactPhone"));
            hotel.setContactEmail((String) request.get("contactEmail"));
            hotel.setAddress((String) request.get("address"));

            @SuppressWarnings("unchecked")
            List<String> galleryImageUrls = (List<String>) request.get("galleryImageUrls");

            Hotel savedHotel = hotelService.createHotel(hotel, galleryImageUrls);
            return ResponseEntity.ok(savedHotel);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating hotel: " + e.getMessage());
        }
    }

    // Update hotel
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHotel(@PathVariable Long id, @RequestBody Map<String, Object> request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Hotel hotel = new Hotel();
            hotel.setName((String) request.get("name"));
            hotel.setLocation((String) request.get("location"));
            hotel.setHotelType((String) request.get("hotelType"));
            hotel.setCountry((String) request.get("country"));
            hotel.setCity((String) request.get("city"));
            hotel.setHeroImageUrl((String) request.get("heroImageUrl"));

            if (request.get("pricePerNight") != null) {
                hotel.setPricePerNight(((Number) request.get("pricePerNight")).doubleValue());
            }

            hotel.setPriceCurrency((String) request.get("priceCurrency"));

            if (request.get("rating") != null) {
                hotel.setRating(((Number) request.get("rating")).doubleValue());
            }
            
            // Set starRating and roomType
            if (request.get("starRating") != null) {
                hotel.setStarRating(((Number) request.get("starRating")).intValue());
            }
            
            hotel.setRoomType((String) request.get("roomType"));

            hotel.setDescription((String) request.get("description"));
            hotel.setAmenities((String) request.get("amenities"));
            hotel.setCheckInTime((String) request.get("checkInTime"));
            hotel.setCheckOutTime((String) request.get("checkOutTime"));
            hotel.setContactPhone((String) request.get("contactPhone"));
            hotel.setContactEmail((String) request.get("contactEmail"));
            hotel.setAddress((String) request.get("address"));

            @SuppressWarnings("unchecked")
            List<String> galleryImageUrls = (List<String>) request.get("galleryImageUrls");

            Hotel updatedHotel = hotelService.updateHotel(id, hotel, galleryImageUrls);
            return ResponseEntity.ok(updatedHotel);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating hotel: " + e.getMessage());
        }
    }

    // Delete hotel
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            hotelService.deleteHotel(id);
            return ResponseEntity.ok("Hotel deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting hotel: " + e.getMessage());
        }
    }
}

