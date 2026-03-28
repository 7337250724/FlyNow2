package com.example.demo.controller;

import com.example.demo.model.Train;
import com.example.demo.model.User;
import com.example.demo.service.TrainService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/trains")
public class AdminTrainController {

    @Autowired
    private TrainService trainService;

    @GetMapping
    public ResponseEntity<?> getAllTrains(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<Train> trains = trainService.getAllTrains();
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainById(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Optional<Train> trainOpt = trainService.getTrainById(id);
        if (trainOpt.isPresent()) {
            return ResponseEntity.ok(trainOpt.get());
        } else {
            return ResponseEntity.status(404).body("Train not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> createTrain(@RequestBody Map<String, Object> request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Train train = new Train();
            train.setTrainName((String) request.get("trainName"));
            train.setTrainNumber((String) request.get("trainNumber"));
            train.setSource((String) request.get("source"));
            train.setDestination((String) request.get("destination"));
            train.setDepartureTime((String) request.get("departureTime"));
            train.setArrivalTime((String) request.get("arrivalTime"));

            if (request.get("sleeperSeats") != null) {
                train.setSleeperSeats(((Number) request.get("sleeperSeats")).intValue());
            }
            if (request.get("acSeats") != null) {
                train.setAcSeats(((Number) request.get("acSeats")).intValue());
            }

            if (request.get("sleeperPrice") != null) {
                train.setSleeperPrice(((Number) request.get("sleeperPrice")).doubleValue());
            }
            if (request.get("acPrice") != null) {
                train.setAcPrice(((Number) request.get("acPrice")).doubleValue());
            }

            train.setPriceCurrency((String) request.get("priceCurrency"));

            Train savedTrain = trainService.saveTrain(train);
            return ResponseEntity.ok(savedTrain);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating train: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrain(@PathVariable Long id, @RequestBody Map<String, Object> request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Optional<Train> trainOpt = trainService.getTrainById(id);
            if (trainOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Train not found");
            }

            Train train = trainOpt.get();
            train.setTrainName((String) request.get("trainName"));
            train.setTrainNumber((String) request.get("trainNumber"));
            train.setSource((String) request.get("source"));
            train.setDestination((String) request.get("destination"));
            train.setDepartureTime((String) request.get("departureTime"));
            train.setArrivalTime((String) request.get("arrivalTime"));

            if (request.get("sleeperSeats") != null) {
                train.setSleeperSeats(((Number) request.get("sleeperSeats")).intValue());
            }
            if (request.get("acSeats") != null) {
                train.setAcSeats(((Number) request.get("acSeats")).intValue());
            }

            if (request.get("sleeperPrice") != null) {
                train.setSleeperPrice(((Number) request.get("sleeperPrice")).doubleValue());
            }
            if (request.get("acPrice") != null) {
                train.setAcPrice(((Number) request.get("acPrice")).doubleValue());
            }

            train.setPriceCurrency((String) request.get("priceCurrency"));

            Train updatedTrain = trainService.saveTrain(train);
            return ResponseEntity.ok(updatedTrain);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating train: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrain(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUsername())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            trainService.deleteTrain(id);
            return ResponseEntity.ok("Train deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting train: " + e.getMessage());
        }
    }
}

