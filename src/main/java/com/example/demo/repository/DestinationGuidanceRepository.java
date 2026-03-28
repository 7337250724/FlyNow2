package com.example.demo.repository;

import com.example.demo.model.DestinationGuidance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DestinationGuidanceRepository extends JpaRepository<DestinationGuidance, Long> {
    Optional<DestinationGuidance> findByTravelPackageId(Long packageId);
}

