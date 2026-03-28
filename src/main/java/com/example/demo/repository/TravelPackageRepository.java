package com.example.demo.repository;

import com.example.demo.model.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {
    
    @Query("SELECT DISTINCT tp FROM TravelPackage tp LEFT JOIN FETCH tp.itineraryDays ORDER BY tp.createdAt DESC")
    List<TravelPackage> findAllWithItinerary();
    
    @Query("SELECT DISTINCT tp FROM TravelPackage tp " +
           "LEFT JOIN FETCH tp.itineraryDays " +
           "WHERE tp.id = :id")
    Optional<TravelPackage> findByIdWithItinerary(Long id);
    
    @Query("SELECT DISTINCT tp FROM TravelPackage tp LEFT JOIN FETCH tp.itineraryDays WHERE tp.packageType = :packageType ORDER BY tp.createdAt DESC")
    List<TravelPackage> findByPackageTypeWithItinerary(String packageType);
    
    // Simple query without relationships for list views
    @Query("SELECT tp FROM TravelPackage tp ORDER BY tp.createdAt DESC")
    List<TravelPackage> findAllSimple();
    
    // Find packages by travel type flags
    @Query("SELECT DISTINCT tp FROM TravelPackage tp LEFT JOIN FETCH tp.itineraryDays WHERE tp.isFamilyFriendly = true ORDER BY tp.createdAt DESC")
    List<TravelPackage> findFamilyFriendlyPackages();
    
    @Query("SELECT DISTINCT tp FROM TravelPackage tp LEFT JOIN FETCH tp.itineraryDays WHERE tp.isCoupleFriendly = true ORDER BY tp.createdAt DESC")
    List<TravelPackage> findCoupleFriendlyPackages();
    
    @Query("SELECT DISTINCT tp FROM TravelPackage tp LEFT JOIN FETCH tp.itineraryDays WHERE tp.isAdventure = true ORDER BY tp.createdAt DESC")
    List<TravelPackage> findAdventurePackages();
    
    @Query("SELECT DISTINCT tp FROM TravelPackage tp LEFT JOIN FETCH tp.itineraryDays WHERE tp.isSpiritual = true ORDER BY tp.createdAt DESC")
    List<TravelPackage> findSpiritualPackages();
    
    @Query("SELECT DISTINCT tp FROM TravelPackage tp LEFT JOIN FETCH tp.itineraryDays WHERE tp.isCorporate = true ORDER BY tp.createdAt DESC")
    List<TravelPackage> findCorporatePackages();
    
    // Find packages by travel type string (contains)
    @Query("SELECT DISTINCT tp FROM TravelPackage tp LEFT JOIN FETCH tp.itineraryDays WHERE tp.travelTypes LIKE %:travelType% ORDER BY tp.createdAt DESC")
    List<TravelPackage> findByTravelType(String travelType);
}

