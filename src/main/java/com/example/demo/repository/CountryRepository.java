package com.example.demo.repository;

import com.example.demo.model.Country;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    
    @EntityGraph(attributePaths = {"places", "images"})
    @Query("SELECT c FROM Country c")
    List<Country> findAllWithPlacesAndImages();
    
    @EntityGraph(attributePaths = {"places", "images"})
    @Query("SELECT c FROM Country c WHERE c.id = :id")
    Optional<Country> findByIdWithPlacesAndImages(Long id);
    
    // Simple query without relationships for list views
    @Query("SELECT c FROM Country c ORDER BY c.createdAt DESC")
    List<Country> findAllSimple();
}
