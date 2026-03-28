package com.example.demo.controller;

import com.example.demo.model.Booking;
import com.example.demo.model.Flight;
import com.example.demo.model.SeatBooking;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.SeatBookingRepository;
import com.example.demo.service.FlightService;
import com.example.demo.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class FlightBookingController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private com.example.demo.service.EmailService emailService;

    @Autowired
    private SeatBookingRepository seatBookingRepository;

    @GetMapping("/flights")
    public String searchFlights(@RequestParam(required = false) String source,
                                @RequestParam(required = false) String destination,
                                Model model) {
        List<Flight> flights;
        if (source != null && !source.isEmpty() && destination != null && !destination.isEmpty()) {
            // Search by route
            flights = flightRepository.findAll().stream()
                    .filter(f -> f.getOrigin().equalsIgnoreCase(source) && 
                                f.getDestination().equalsIgnoreCase(destination))
                    .toList();
        } else if (destination != null && !destination.isEmpty()) {
            flights = flightService.getFlightsByDestination(destination);
        } else {
            flights = flightService.getAllFlights();
        }
        model.addAttribute("flights", flights);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        return "flight-booking";
    }

    @GetMapping("/flight/{id}/book")
    public String showFlightBookingForm(@PathVariable Long id,
                                       @RequestParam(required = false) String selectedClass,
                                       @RequestParam(required = false) String returnUrl,
                                       Model model) {
        Optional<Flight> flightOpt = flightService.getFlightById(id);
        if (flightOpt.isPresent()) {
            Flight flight = flightOpt.get();
            model.addAttribute("flight", flight);
            model.addAttribute("selectedClass", selectedClass != null ? selectedClass : "Economy");
            model.addAttribute("returnUrl", returnUrl != null ? returnUrl : "");
            return "flight-booking-form";
        }
        return "redirect:/flights";
    }

    @PostMapping("/flight/book/process")
    public String processFlightBooking(@RequestParam Long flightId,
                                      @RequestParam String selectedClass,
                                      @RequestParam(required = false) String selectedSeat,
                                      @RequestParam(required = false) String selectedSeatsJson,
                                      @RequestParam(required = false) String travelerDetails,
                                      @RequestParam String customerName,
                                      @RequestParam String email,
                                      @RequestParam String phoneNumber,
                                      @RequestParam Integer numberOfMembers,
                                      @RequestParam String upiId,
                                      @RequestParam(required = false) String returnUrl,
                                      Model model) {
        try {
            Optional<Flight> flightOpt = flightService.getFlightById(flightId);
            if (flightOpt.isEmpty()) {
                // If returnUrl is provided, redirect back with error, otherwise to flights page
                if (returnUrl != null && !returnUrl.isEmpty()) {
                    try {
                        String decodedReturnUrl = java.net.URLDecoder.decode(returnUrl, "UTF-8");
                        return "redirect:" + decodedReturnUrl + "&error=flightNotFound";
                    } catch (Exception e) {
                        return "redirect:/flights?error=notFound";
                    }
                }
                return "redirect:/flights?error=notFound";
            }

            Flight flight = flightOpt.get();
            Booking booking = new Booking();
            booking.setSelectedFlight(flight);
            booking.setSelectedClass(selectedClass);
            
            // Handle seat selection - prefer JSON format, fallback to single seat
            String finalSelectedSeat = null;
            if (selectedSeatsJson != null && !selectedSeatsJson.trim().isEmpty()) {
                finalSelectedSeat = selectedSeatsJson.trim();
            } else if (selectedSeat != null && !selectedSeat.trim().isEmpty()) {
                finalSelectedSeat = selectedSeat.trim();
            }
            booking.setSelectedSeat(finalSelectedSeat);
            
            // Store traveler details if provided
            if (travelerDetails != null && !travelerDetails.trim().isEmpty()) {
                booking.setTravelerDetails(travelerDetails.trim());
            }
            
            booking.setCustomerName(customerName);
            booking.setEmail(email);
            booking.setPhoneNumber(phoneNumber);
            booking.setNumberOfMembers(numberOfMembers);
            booking.setUpiId(upiId);
            booking.setPaymentStatus("PAID");

            // Calculate price based on class
            Double price = flight.getPrice(); // Default to economy
            if ("Business".equalsIgnoreCase(selectedClass) && flight.getBusinessPrice() != null) {
                price = flight.getBusinessPrice();
            } else if ("First".equalsIgnoreCase(selectedClass) && flight.getFirstClassPrice() != null) {
                price = flight.getFirstClassPrice();
            }

            // Validate that price is greater than 0 - payment is required
            if (price == null || price <= 0) {
                if (returnUrl != null && !returnUrl.isEmpty()) {
                    try {
                        String decodedReturnUrl = java.net.URLDecoder.decode(returnUrl, "UTF-8");
                        return "redirect:" + decodedReturnUrl + "&error=invalidPrice";
                    } catch (Exception e) {
                        return "redirect:/flights?error=invalidPrice";
                    }
                }
                return "redirect:/flights?error=invalidPrice";
            }

            // Calculate window seat charges if seats are selected
            Double windowSeatCharge = 0.0;
            if (finalSelectedSeat != null && !finalSelectedSeat.isEmpty()) {
                // Count window seats (A1-A70 are all window seats in this layout)
                int windowSeatCount = 0;
                if (finalSelectedSeat.startsWith("{")) {
                    // JSON format - count seats
                    try {
                        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                        java.util.Map<String, String> seatMap = mapper.readValue(finalSelectedSeat, java.util.Map.class);
                        windowSeatCount = seatMap.size(); // All A1-A70 are window seats
                    } catch (Exception e) {
                        windowSeatCount = 1;
                    }
                } else {
                    // Comma-separated or single seat
                    String[] seats = finalSelectedSeat.split(",");
                    windowSeatCount = seats.length;
                }
                windowSeatCharge = windowSeatCount * 500.0;
                booking.setWindowSeatCharge(windowSeatCharge);
            }

            Double totalPrice = (price * numberOfMembers) + windowSeatCharge;
            
            // Validate total price is greater than 0
            if (totalPrice <= 0) {
                if (returnUrl != null && !returnUrl.isEmpty()) {
                    try {
                        String decodedReturnUrl = java.net.URLDecoder.decode(returnUrl, "UTF-8");
                        return "redirect:" + decodedReturnUrl + "&error=paymentRequired";
                    } catch (Exception e) {
                        return "redirect:/flights?error=paymentRequired";
                    }
                }
                return "redirect:/flights?error=paymentRequired";
            }

            booking.setTotalPrice(totalPrice);
            booking.setPriceCurrency(flight.getPriceCurrency() != null ? flight.getPriceCurrency() : "₹");
            // Booking reference will be auto-generated by Booking constructor

            // Only save booking after payment validation - payment status is already set to PAID
            Booking savedBooking = bookingRepository.save(booking);

            // Save seat bookings to prevent double-booking
            if (savedBooking.getSelectedFlight() != null && finalSelectedSeat != null && !finalSelectedSeat.isEmpty()) {
                saveSeatBookings(savedBooking, finalSelectedSeat);
            }

            // Send booking confirmation email asynchronously
            try {
                emailService.sendBookingConfirmationEmail(savedBooking);
            } catch (Exception e) {
                System.err.println("Failed to send flight booking confirmation email: " + e.getMessage());
                // Don't fail the booking if email fails
            }

            // Redirect to returnUrl if provided, otherwise to success page
            if (returnUrl != null && !returnUrl.isEmpty()) {
                try {
                    // Decode the return URL
                    String decodedReturnUrl = java.net.URLDecoder.decode(returnUrl, "UTF-8");
                    // Ensure proper URL format (handle & vs ? for query params)
                    if (decodedReturnUrl.contains("?")) {
                        decodedReturnUrl = decodedReturnUrl + "&flightBooked=true&bookingId=" + savedBooking.getId();
                    } else {
                        decodedReturnUrl = decodedReturnUrl + "?flightBooked=true&bookingId=" + savedBooking.getId();
                    }
                    return "redirect:" + decodedReturnUrl;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "redirect:/flight/booking/success?bookingId=" + savedBooking.getId();
                }
            }

            return "redirect:/flight/booking/success?bookingId=" + savedBooking.getId();
        } catch (Exception e) {
            e.printStackTrace();
            // If returnUrl is provided, redirect back with error
            if (returnUrl != null && !returnUrl.isEmpty()) {
                try {
                    String decodedReturnUrl = java.net.URLDecoder.decode(returnUrl, "UTF-8");
                    return "redirect:" + decodedReturnUrl + (decodedReturnUrl.contains("?") ? "&" : "?") + "error=bookingFailed";
                } catch (Exception ex) {
                    return "redirect:/flights?error=bookingFailed";
                }
            }
            return "redirect:/flights?error=bookingFailed";
        }
    }

    @GetMapping("/flight/booking/success")
    public String flightBookingSuccess(@RequestParam Long bookingId, Model model) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            model.addAttribute("booking", bookingOpt.get());
            return "flight-booking-success";
        }
        return "redirect:/flights";
    }

    @GetMapping("/flight/booking/download-pdf/{bookingId}")
    public void downloadFlightBookingPdf(@PathVariable Long bookingId, HttpServletResponse response) {
        try {
            Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
            if (bookingOpt.isPresent()) {
                Booking booking = bookingOpt.get();
                byte[] pdfBytes = pdfService.generateFlightBookingPdf(booking);

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=flight-booking-" + booking.getBookingReference() + ".pdf");
                response.setContentLength(pdfBytes.length);

                response.getOutputStream().write(pdfBytes);
                response.getOutputStream().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Save seat bookings for a flight to prevent double-booking
     */
    @Transactional
    private void saveSeatBookings(Booking booking, String selectedSeats) {
        if (booking.getSelectedFlight() == null || selectedSeats == null || selectedSeats.trim().isEmpty()) {
            return;
        }

        List<String> seatsList = new java.util.ArrayList<>();
        
        // Parse seats from JSON or comma-separated format
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
                Optional<SeatBooking> existingBooking = seatBookingRepository.findByFlightIdAndSeatNumber(
                    booking.getSelectedFlight().getId(), seatNumber);
                
                if (existingBooking.isEmpty()) {
                    SeatBooking seatBooking = new SeatBooking();
                    seatBooking.setFlight(booking.getSelectedFlight());
                    seatBooking.setSeatNumber(seatNumber);
                    seatBooking.setBooking(booking);
                    seatBooking.setIsConfirmed(true);
                    seatBookingRepository.save(seatBooking);
                } else {
                    // Seat is already booked - log warning but don't fail the booking
                    System.err.println("Warning: Seat " + seatNumber + " on flight " + 
                        booking.getSelectedFlight().getId() + " is already booked.");
                }
            }
        }
    }
}

