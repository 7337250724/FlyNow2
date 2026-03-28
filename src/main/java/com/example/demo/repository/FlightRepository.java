package com.example.demo.repository;

import com.example.demo.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    // Find flights by destination (case-insensitive exact match)
    @Query("SELECT f FROM Flight f WHERE UPPER(TRIM(f.destination)) = UPPER(TRIM(:destination)) ORDER BY f.departureTime ASC")
    List<Flight> findByDestination(String destination);
    
    // Find flights by destination (case-insensitive, partial match)
    @Query("SELECT f FROM Flight f WHERE UPPER(TRIM(f.destination)) LIKE UPPER(CONCAT('%', TRIM(:destination), '%')) ORDER BY f.departureTime ASC")
    List<Flight> findByDestinationContaining(String destination);
    
    // Find flights by destination and class type
    @Query("SELECT f FROM Flight f WHERE UPPER(TRIM(f.destination)) = UPPER(TRIM(:destination)) AND f.classType = :classType ORDER BY f.departureTime ASC")
    List<Flight> findByDestinationAndClassType(String destination, String classType);
}

