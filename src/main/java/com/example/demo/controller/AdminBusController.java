package com.example.demo.controller;

import com.example.demo.model.Bus;
import com.example.demo.model.User;
import com.example.demo.service.BusService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/buses")
public class AdminBusController {

    @Autowired
    private BusService busService;

    @GetMapping
    public ResponseEntity<?> getAllBuses(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<Bus> buses = busService.getAllBuses();
        return ResponseEntity.ok(buses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBusById(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Optional<Bus> busOpt = busService.getBusById(id);
        if (busOpt.isPresent()) {
            return ResponseEntity.ok(busOpt.get());
        } else {
            return ResponseEntity.status(404).body("Bus not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> createBus(@RequestBody Map<String, Object> request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Bus bus = new Bus();
            bus.setBusName((String) request.get("busName"));
            bus.setBusType((String) request.get("busType"));
            bus.setSource((String) request.get("source"));
            bus.setDestination((String) request.get("destination"));
            bus.setDepartureTime((String) request.get("departureTime"));
            bus.setArrivalTime((String) request.get("arrivalTime"));

            if (request.get("totalSeats") != null) {
                bus.setTotalSeats(((Number) request.get("totalSeats")).intValue());
            }
            if (request.get("availableSeats") != null) {
                bus.setAvailableSeats(((Number) request.get("availableSeats")).intValue());
            }

            if (request.get("ticketPrice") != null) {
                bus.setTicketPrice(((Number) request.get("ticketPrice")).doubleValue());
            }

            bus.setPriceCurrency((String) request.get("priceCurrency"));
            bus.setSeatLayout((String) request.get("seatLayout"));

            Bus savedBus = busService.saveBus(bus);
            return ResponseEntity.ok(savedBus);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating bus: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBus(@PathVariable Long id, @RequestBody Map<String, Object> request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Optional<Bus> busOpt = busService.getBusById(id);
            if (busOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Bus not found");
            }

            Bus bus = busOpt.get();
            bus.setBusName((String) request.get("busName"));
            bus.setBusType((String) request.get("busType"));
            bus.setSource((String) request.get("source"));
            bus.setDestination((String) request.get("destination"));
            bus.setDepartureTime((String) request.get("departureTime"));
            bus.setArrivalTime((String) request.get("arrivalTime"));

            if (request.get("totalSeats") != null) {
                bus.setTotalSeats(((Number) request.get("totalSeats")).intValue());
            }
            if (request.get("availableSeats") != null) {
                bus.setAvailableSeats(((Number) request.get("availableSeats")).intValue());
            }

            if (request.get("ticketPrice") != null) {
                bus.setTicketPrice(((Number) request.get("ticketPrice")).doubleValue());
            }

            bus.setPriceCurrency((String) request.get("priceCurrency"));
            bus.setSeatLayout((String) request.get("seatLayout"));

            Bus updatedBus = busService.saveBus(bus);
            return ResponseEntity.ok(updatedBus);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating bus: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBus(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            busService.deleteBus(id);
            return ResponseEntity.ok("Bus deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting bus: " + e.getMessage());
        }
    }
}

