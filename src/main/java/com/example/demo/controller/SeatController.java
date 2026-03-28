package com.example.demo.controller;

import com.example.demo.model.Flight;
import com.example.demo.model.SeatBooking;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.SeatBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatBookingRepository seatBookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    // Get available seats for a flight
    @GetMapping("/flight/{flightId}/available")
    public ResponseEntity<Map<String, Object>> getAvailableSeats(@PathVariable Long flightId) {
        try {
            Optional<Flight> flightOpt = flightRepository.findById(flightId);
            if (flightOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Flight flight = flightOpt.get();
            
            // Generate all seats A1 to A70
            List<String> allSeats = new ArrayList<>();
            for (int i = 1; i <= 70; i++) {
                allSeats.add("A" + i);
            }
            
            // Get booked seats
            List<String> bookedSeats = seatBookingRepository.findBookedSeatsByFlightId(flightId);
            Set<String> bookedSet = new HashSet<>(bookedSeats);
            
            // Calculate available seats
            List<String> availableSeats = allSeats.stream()
                    .filter(seat -> !bookedSet.contains(seat))
                    .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("allSeats", allSeats);
            response.put("availableSeats", availableSeats);
            response.put("bookedSeats", bookedSeats);
            response.put("totalSeats", 70);
            response.put("availableCount", availableSeats.size());
            response.put("bookedCount", bookedSeats.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Check if specific seats are available
    @PostMapping("/flight/{flightId}/check")
    public ResponseEntity<Map<String, Object>> checkSeatAvailability(
            @PathVariable Long flightId,
            @RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<String> seatsToCheck = (List<String>) request.get("seats");
            
            if (seatsToCheck == null || seatsToCheck.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            List<String> bookedSeats = seatBookingRepository.findBookedSeatsByFlightId(flightId);
            Set<String> bookedSet = new HashSet<>(bookedSeats);
            
            Map<String, Boolean> availability = new HashMap<>();
            for (String seat : seatsToCheck) {
                availability.put(seat, !bookedSet.contains(seat));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("availability", availability);
            response.put("allAvailable", availability.values().stream().allMatch(Boolean::booleanValue));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

