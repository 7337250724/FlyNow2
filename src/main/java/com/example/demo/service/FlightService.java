package com.example.demo.service;

import com.example.demo.model.Flight;
import com.example.demo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Transactional(readOnly = true)
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Flight> getFlightsByDestination(String destination) {
        return flightRepository.findByDestination(destination);
    }

    @Transactional(readOnly = true)
    public List<Flight> getFlightsByDestinationAndClass(String destination, String classType) {
        return flightRepository.findByDestinationAndClassType(destination, classType);
    }

    @Transactional
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Transactional
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}

