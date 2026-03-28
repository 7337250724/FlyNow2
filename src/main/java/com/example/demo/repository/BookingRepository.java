package com.example.demo.repository;

import com.example.demo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByOrderByBookingDateDesc();
    
    @Query("SELECT DISTINCT b FROM Booking b " +
           "LEFT JOIN FETCH b.travelPackage " +
           "LEFT JOIN FETCH b.selectedFlight " +
           "LEFT JOIN FETCH b.selectedHotel " +
           "ORDER BY b.bookingDate DESC")
    List<Booking> findAllWithRelations();
    
    @Query("SELECT b FROM Booking b WHERE b.travelPackage.id = :packageId")
    List<Booking> findByTravelPackageId(Long packageId);
}

