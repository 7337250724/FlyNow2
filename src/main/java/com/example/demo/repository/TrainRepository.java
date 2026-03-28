package com.example.demo.repository;

import com.example.demo.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    
    // Find trains by destination
    @Query("SELECT t FROM Train t WHERE t.destination = :destination ORDER BY t.departureTime ASC")
    List<Train> findByDestination(String destination);
    
    // Find trains by source and destination
    @Query("SELECT t FROM Train t WHERE t.source = :source AND t.destination = :destination ORDER BY t.departureTime ASC")
    List<Train> findBySourceAndDestination(String source, String destination);
}

