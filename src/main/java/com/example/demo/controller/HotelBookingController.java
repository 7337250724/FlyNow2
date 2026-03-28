package com.example.demo.controller;

import com.example.demo.model.Hotel;
import com.example.demo.model.HotelBooking;
import com.example.demo.repository.HotelBookingRepository;
import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
public class HotelBookingController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    @Autowired
    private com.example.demo.service.EmailService emailService;

    @GetMapping("/book-hotel/{hotelId}")
    public String showHotelBookingForm(@PathVariable String hotelId, Model model) {
        try {
            Long id = Long.parseLong(hotelId);
            Optional<Hotel> hotelOpt = hotelService.getHotelById(id);

            if (hotelOpt.isPresent()) {
                Hotel hotel = hotelOpt.get();
                String hotelName = hotel.getName();
                String hotelLocation = hotel.getLocation();
                Double pricePerNight = hotel.getPricePerNight();
                String priceCurrency = hotel.getPriceCurrency() != null ? hotel.getPriceCurrency() : "₹";

                HotelBooking booking = new HotelBooking();
                booking.setHotel(hotel);
                booking.setPriceCurrency(priceCurrency);

                model.addAttribute("booking", booking);
                model.addAttribute("hotel", hotel);
                model.addAttribute("hotelName", hotelName);
                model.addAttribute("hotelLocation", hotelLocation);
                model.addAttribute("pricePerNight", pricePerNight);
                model.addAttribute("priceCurrency", priceCurrency);
                return "hotel-booking-form";
            }
        } catch (NumberFormatException e) {
            // Invalid ID format
        }
        return "redirect:/hotels";
    }

    @PostMapping("/book-hotel/process")
    public String processHotelBooking(@ModelAttribute HotelBooking booking, 
                                     @org.springframework.web.bind.annotation.RequestParam(required = false) Long hotelId) {
        // Fetch hotel if hotelId is provided
        if (hotelId != null) {
            Optional<Hotel> hotelOpt = hotelService.getHotelById(hotelId);
            if (hotelOpt.isPresent()) {
                booking.setHotel(hotelOpt.get());
            }
        }

        // Calculate total price based on number of nights and rooms
        if (booking.getHotel() != null && booking.getCheckInDate() != null && booking.getCheckOutDate() != null) {
            long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
            if (nights > 0) {
                Double pricePerNight = booking.getHotel().getPricePerNight();
                Integer numberOfRooms = booking.getNumberOfRooms() != null ? booking.getNumberOfRooms() : 1;
                Double totalPrice = pricePerNight * nights * numberOfRooms;
                booking.setTotalPrice(totalPrice);
            }
        }

        // Save booking
        hotelBookingRepository.save(booking);
        
        // Send booking confirmation email asynchronously
        try {
            emailService.sendHotelBookingConfirmationEmail(booking);
        } catch (Exception e) {
            System.err.println("Failed to send hotel booking confirmation email: " + e.getMessage());
            // Don't fail the booking if email fails
        }
        
        return "redirect:/book-hotel/success?reference=" + booking.getBookingReference();
    }

    @GetMapping("/book-hotel/success")
    public String hotelBookingSuccess(@org.springframework.web.bind.annotation.RequestParam String reference, Model model) {
        model.addAttribute("bookingReference", reference);
        return "hotel-booking-success";
    }
}

