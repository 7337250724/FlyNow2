package com.example.demo.service;

import com.example.demo.enums.PackageType;
import com.example.demo.model.Booking;
import com.example.demo.model.SeatBooking;
import com.example.demo.model.TravelPackage;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.SeatBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatBookingRepository seatBookingRepository;

    @Transactional
    public Booking createBooking(Booking booking, TravelPackage travelPackage, PackageType packageTypeSelected) {
        // Set package type specific inclusions based on STANDARD or LUXURY
        if (packageTypeSelected == PackageType.STANDARD) {
            // Standard Package: Only sightseeing and transport
            booking.setFlightsIncluded(false);
            booking.setHotelsIncluded(false);
            booking.setMealsIncluded(false);
            booking.setSightseeingIncluded(true);
            booking.setTransportIncluded(true);
        } else if (packageTypeSelected == PackageType.LUXURY) {
            // Luxury Package: All premium services included
            booking.setFlightsIncluded(booking.getSelectedFlight() != null);
            booking.setHotelsIncluded(booking.getSelectedHotel() != null);
            booking.setMealsIncluded(true);
            booking.setSightseeingIncluded(true);
            booking.setTransportIncluded(true);
        }

        booking.setPackageTypeSelected(packageTypeSelected);
        booking.setTravelPackage(travelPackage);
        booking.setPaymentStatus("PAID");

        Booking savedBooking = bookingRepository.save(booking);

        // Save seat bookings if flight and seats are selected
        if (savedBooking.getSelectedFlight() != null && savedBooking.getSelectedSeat() != null) {
            String selectedSeats = savedBooking.getSelectedSeat();
            // Handle multiple seats (comma-separated or JSON)
            java.util.List<String> seatsList = new java.util.ArrayList<>();
            
            if (selectedSeats.startsWith("{")) {
                // JSON format from seat map: {"1":"A1","2":"A2"}
                try {
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    java.util.Map<String, String> seatMap = mapper.readValue(selectedSeats, 
                        mapper.getTypeFactory().constructMapType(java.util.Map.class, String.class, String.class));
                    seatsList.addAll(seatMap.values());
                } catch (Exception e) {
                    // Fallback to comma-separated
                    String[] seats = selectedSeats.split(",");
                    for (String seat : seats) {
                        if (seat != null && !seat.trim().isEmpty()) {
                            seatsList.add(seat.trim());
                        }
                    }
                }
            } else {
                // Comma-separated format
                String[] seats = selectedSeats.split(",");
                for (String seat : seats) {
                    if (seat != null && !seat.trim().isEmpty()) {
                        seatsList.add(seat.trim());
                    }
                }
            }

            // Save each seat booking
            for (String seatNumber : seatsList) {
                if (seatNumber != null && !seatNumber.trim().isEmpty()) {
                    seatNumber = seatNumber.trim();
                    // Check if seat is already booked
                    var existingBooking = seatBookingRepository.findByFlightIdAndSeatNumber(
                        savedBooking.getSelectedFlight().getId(), seatNumber);
                    
                    if (existingBooking.isEmpty()) {
                        SeatBooking seatBooking = new SeatBooking();
                        seatBooking.setFlight(savedBooking.getSelectedFlight());
                        seatBooking.setSeatNumber(seatNumber);
                        seatBooking.setBooking(savedBooking);
                        seatBooking.setIsConfirmed(true);
                        seatBookingRepository.save(seatBooking);
                    }
                }
            }
        }

        return savedBooking;
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findByOrderByBookingDateDesc();
    }

    @Transactional(readOnly = true)
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }
}

