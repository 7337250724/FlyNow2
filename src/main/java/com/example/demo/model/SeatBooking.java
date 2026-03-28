package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seat_bookings", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"flight_id", "seat_number"})
})
public class SeatBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
    
    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber; // e.g., "A1", "A2", ..., "A70"
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
    
    @Column(name = "booked_at", nullable = false)
    private LocalDateTime bookedAt;
    
    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed = false;
    
    public SeatBooking() {
        this.bookedAt = LocalDateTime.now();
        this.isConfirmed = false;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Flight getFlight() {
        return flight;
    }
    
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    
    public String getSeatNumber() {
        return seatNumber;
    }
    
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    public Booking getBooking() {
        return booking;
    }
    
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
    public LocalDateTime getBookedAt() {
        return bookedAt;
    }
    
    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
    
    public Boolean getIsConfirmed() {
        return isConfirmed;
    }
    
    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
}

