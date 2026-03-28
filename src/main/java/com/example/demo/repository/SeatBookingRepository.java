package com.example.demo.repository;

import com.example.demo.model.SeatBooking;
import com.example.demo.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, Long> {
    
    List<SeatBooking> findByFlightId(Long flightId);
    
    Optional<SeatBooking> findByFlightIdAndSeatNumber(Long flightId, String seatNumber);
    
    @Query("SELECT sb.seatNumber FROM SeatBooking sb WHERE sb.flight.id = :flightId AND sb.isConfirmed = true")
    List<String> findBookedSeatsByFlightId(@Param("flightId") Long flightId);
    
    @Query("SELECT COUNT(sb) FROM SeatBooking sb WHERE sb.flight.id = :flightId AND sb.isConfirmed = true")
    Long countBookedSeatsByFlightId(@Param("flightId") Long flightId);
    
    void deleteByBookingId(Long bookingId);
}

