package com.example.demo.controller;

import com.example.demo.model.Hotel;
import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotels")
    public String listHotels(
            @RequestParam(name = "type", required = false) String type,
            Model model) {
        List<Hotel> hotels;
        String hotelType;

        if (type != null && "international".equalsIgnoreCase(type)) {
            hotels = hotelService.getHotelsByType("INTERNATIONAL");
            hotelType = "International";
        } else if (type != null && "domestic".equalsIgnoreCase(type) || type != null && "national".equalsIgnoreCase(type)) {
            hotels = hotelService.getHotelsByType("DOMESTIC");
            hotelType = "National";
        } else {
            // Show all hotels
            hotels = hotelService.getAllHotels();
            hotelType = "All";
        }

        model.addAttribute("hotels", hotels);
        model.addAttribute("hotelType", hotelType);
        return "hotels-list";
    }

    @GetMapping("/hotel/{id}")
    public String showHotelDetails(@PathVariable String id, Model model) {
        try {
            Long hotelId = Long.parseLong(id);
            Optional<Hotel> hotelOpt = hotelService.getHotelById(hotelId);
            if (hotelOpt.isPresent()) {
                model.addAttribute("hotel", hotelOpt.get());
                return "hotel-details";
            }
        } catch (NumberFormatException e) {
            // Invalid ID format
        }
        return "redirect:/hotels";
    }
}

