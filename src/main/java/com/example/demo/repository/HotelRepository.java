package com.example.demo.repository;

import com.example.demo.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT DISTINCT h FROM Hotel h LEFT JOIN FETCH h.galleryImages WHERE h.hotelType = :hotelType ORDER BY h.createdAt DESC")
    List<Hotel> findByHotelTypeWithImages(String hotelType);

    @Query("SELECT DISTINCT h FROM Hotel h LEFT JOIN FETCH h.galleryImages WHERE h.id = :id")
    Optional<Hotel> findByIdWithImages(Long id);

    @Query("SELECT DISTINCT h FROM Hotel h LEFT JOIN FETCH h.galleryImages ORDER BY h.createdAt DESC")
    List<Hotel> findAllWithImages();

    // Simple query without relationships for list views
    @Query("SELECT h FROM Hotel h ORDER BY h.createdAt DESC")
    List<Hotel> findAllSimple();
    
    // Find hotels by location
    @Query("SELECT h FROM Hotel h WHERE h.location = :location ORDER BY h.createdAt DESC")
    List<Hotel> findByLocation(String location);
    
    // Find hotels by location with minimum star rating (for Luxury packages)
    @Query("SELECT h FROM Hotel h WHERE h.location = :location AND h.starRating >= :minRating ORDER BY h.starRating DESC, h.createdAt DESC")
    List<Hotel> findByLocationAndStarRatingGreaterThanEqual(String location, Integer minRating);
}

