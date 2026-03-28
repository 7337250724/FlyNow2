package com.example.demo.service;

import com.example.demo.model.Bus;
import com.example.demo.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Transactional(readOnly = true)
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Bus> getBusById(Long id) {
        return busRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Bus> getBusesByDestination(String destination) {
        return busRepository.findByDestination(destination);
    }

    @Transactional(readOnly = true)
    public List<Bus> getBusesByRoute(String source, String destination) {
        return busRepository.findBySourceAndDestination(source, destination);
    }

    @Transactional
    public Bus saveBus(Bus bus) {
        return busRepository.save(bus);
    }

    @Transactional
    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }
}

