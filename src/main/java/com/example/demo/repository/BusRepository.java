package com.example.demo.repository;

import com.example.demo.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    
    // Find buses by destination
    @Query("SELECT b FROM Bus b WHERE b.destination = :destination ORDER BY b.departureTime ASC")
    List<Bus> findByDestination(String destination);
    
    // Find buses by source and destination
    @Query("SELECT b FROM Bus b WHERE b.source = :source AND b.destination = :destination ORDER BY b.departureTime ASC")
    List<Bus> findBySourceAndDestination(String source, String destination);
}

