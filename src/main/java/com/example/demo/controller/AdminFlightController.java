package com.example.demo.controller;

import com.example.demo.model.Flight;
import com.example.demo.model.User;
import com.example.demo.service.FlightService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/flights")
public class AdminFlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<?> getAllFlights(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFlightById(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Optional<Flight> flightOpt = flightService.getFlightById(id);
        if (flightOpt.isPresent()) {
            return ResponseEntity.ok(flightOpt.get());
        } else {
            return ResponseEntity.status(404).body("Flight not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> createFlight(@RequestBody Map<String, Object> request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Flight flight = new Flight();
            flight.setFlightNumber((String) request.get("flightNumber"));
            flight.setAirline((String) request.get("airline"));
            flight.setOrigin((String) request.get("origin"));
            flight.setDestination((String) request.get("destination"));
            flight.setDepartureTime((String) request.get("departureTime"));
            flight.setArrivalTime((String) request.get("arrivalTime"));
            flight.setClassType((String) request.get("classType"));

            if (request.get("price") != null) {
                flight.setPrice(((Number) request.get("price")).doubleValue());
            }

            // Auto-calculate Business/First class prices if not provided
            if (request.get("businessPrice") != null && ((Number) request.get("businessPrice")).doubleValue() > 0) {
                flight.setBusinessPrice(((Number) request.get("businessPrice")).doubleValue());
            } else if (flight.getPrice() != null) {
                flight.setBusinessPrice(flight.getPrice() * 2.5);
            }

            if (request.get("firstClassPrice") != null && ((Number) request.get("firstClassPrice")).doubleValue() > 0) {
                flight.setFirstClassPrice(((Number) request.get("firstClassPrice")).doubleValue());
            } else if (flight.getPrice() != null) {
                flight.setFirstClassPrice(flight.getPrice() * 4.0);
            }

            flight.setPriceCurrency((String) request.get("priceCurrency"));

            if (request.get("economySeats") != null) {
                flight.setEconomySeats(((Number) request.get("economySeats")).intValue());
            } else {
                flight.setEconomySeats(140); // Default
            }

            if (request.get("businessSeats") != null) {
                flight.setBusinessSeats(((Number) request.get("businessSeats")).intValue());
            } else {
                flight.setBusinessSeats(30); // Default
            }

            if (request.get("firstClassSeats") != null) {
                flight.setFirstClassSeats(((Number) request.get("firstClassSeats")).intValue());
            } else {
                flight.setFirstClassSeats(10); // Default
            }

            flight.setAvailableSeats((String) request.get("availableSeats"));
            flight.setBusinessAvailableSeats((String) request.get("businessAvailableSeats"));
            flight.setFirstClassAvailableSeats((String) request.get("firstClassAvailableSeats"));

            if (request.get("totalSeats") != null) {
                flight.setTotalSeats(((Number) request.get("totalSeats")).intValue());
            }

            Flight savedFlight = flightService.saveFlight(flight);
            return ResponseEntity.ok(savedFlight);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating flight: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFlight(@PathVariable Long id, @RequestBody Map<String, Object> request,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Optional<Flight> flightOpt = flightService.getFlightById(id);
            if (flightOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Flight not found");
            }

            Flight flight = flightOpt.get();
            flight.setFlightNumber((String) request.get("flightNumber"));
            flight.setAirline((String) request.get("airline"));
            flight.setOrigin((String) request.get("origin"));
            flight.setDestination((String) request.get("destination"));
            flight.setDepartureTime((String) request.get("departureTime"));
            flight.setArrivalTime((String) request.get("arrivalTime"));
            flight.setClassType((String) request.get("classType"));

            if (request.get("price") != null) {
                flight.setPrice(((Number) request.get("price")).doubleValue());
            }

            // Auto-calculate Business/First class prices if not provided
            if (request.get("businessPrice") != null && ((Number) request.get("businessPrice")).doubleValue() > 0) {
                flight.setBusinessPrice(((Number) request.get("businessPrice")).doubleValue());
            } else if (flight.getPrice() != null) {
                flight.setBusinessPrice(flight.getPrice() * 2.5);
            }

            if (request.get("firstClassPrice") != null && ((Number) request.get("firstClassPrice")).doubleValue() > 0) {
                flight.setFirstClassPrice(((Number) request.get("firstClassPrice")).doubleValue());
            } else if (flight.getPrice() != null) {
                flight.setFirstClassPrice(flight.getPrice() * 4.0);
            }

            flight.setPriceCurrency((String) request.get("priceCurrency"));

            if (request.get("economySeats") != null) {
                flight.setEconomySeats(((Number) request.get("economySeats")).intValue());
            } else {
                flight.setEconomySeats(140); // Default
            }

            if (request.get("businessSeats") != null) {
                flight.setBusinessSeats(((Number) request.get("businessSeats")).intValue());
            } else {
                flight.setBusinessSeats(30); // Default
            }

            if (request.get("firstClassSeats") != null) {
                flight.setFirstClassSeats(((Number) request.get("firstClassSeats")).intValue());
            } else {
                flight.setFirstClassSeats(10); // Default
            }

            flight.setAvailableSeats((String) request.get("availableSeats"));
            flight.setBusinessAvailableSeats((String) request.get("businessAvailableSeats"));
            flight.setFirstClassAvailableSeats((String) request.get("firstClassAvailableSeats"));

            if (request.get("totalSeats") != null) {
                flight.setTotalSeats(((Number) request.get("totalSeats")).intValue());
            }

            Flight updatedFlight = flightService.saveFlight(flight);
            return ResponseEntity.ok(updatedFlight);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating flight: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFlight(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            flightService.deleteFlight(id);
            return ResponseEntity.ok("Flight deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting flight: " + e.getMessage());
        }
    }
}
