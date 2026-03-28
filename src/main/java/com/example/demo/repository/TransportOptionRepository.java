package com.example.demo.repository;

import com.example.demo.model.TransportOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportOptionRepository extends JpaRepository<TransportOption, Long> {
    List<TransportOption> findByTravelPackageId(Long packageId);
}
