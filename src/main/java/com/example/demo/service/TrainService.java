package com.example.demo.service;

import com.example.demo.model.Train;
import com.example.demo.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    @Transactional(readOnly = true)
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Train> getTrainById(Long id) {
        return trainRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Train> getTrainsByDestination(String destination) {
        return trainRepository.findByDestination(destination);
    }

    @Transactional(readOnly = true)
    public List<Train> getTrainsByRoute(String source, String destination) {
        return trainRepository.findBySourceAndDestination(source, destination);
    }

    @Transactional
    public Train saveTrain(Train train) {
        return trainRepository.save(train);
    }

    @Transactional
    public void deleteTrain(Long id) {
        trainRepository.deleteById(id);
    }
}

