package com.example.demo.controller;

import com.example.demo.enums.PackageType;
import com.example.demo.model.Booking;
import com.example.demo.model.Flight;
import com.example.demo.model.Hotel;
import com.example.demo.model.TravelPackage;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.service.BookingService;
import com.example.demo.service.PdfService;
import com.example.demo.service.TravelPackageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class BookingController {

    @Autowired
    private TravelPackageService packageService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private com.example.demo.repository.BusRepository busRepository;

    @Autowired
    private com.example.demo.repository.TrainRepository trainRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private com.example.demo.service.EmailService emailService;

    @GetMapping("/book/{packageId}")
    public String showBookingForm(@PathVariable String packageId, 
                                  @RequestParam(name = "packageType", required = false) String packageType,
                                  @RequestParam(name = "selectedFlightId", required = false) Long selectedFlightId,
                                  @RequestParam(name = "selectedBusId", required = false) Long selectedBusId,
                                  @RequestParam(name = "selectedTrainId", required = false) Long selectedTrainId,
                                  @RequestParam(name = "selectedTrainClass", required = false) String selectedTrainClass,
                                  @RequestParam(name = "selectedHotelId", required = false) Long selectedHotelId,
                                  @RequestParam(name = "selectedSeat", required = false) String selectedSeat,
                                  Model model) {
        // Redirect to contact-details page
        String redirectUrl = "/book/" + packageId + "/contact-details";
        if (packageType != null) {
            redirectUrl += "?packageType=" + packageType;
        }
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/book/{packageId}/contact-details")
    public String showContactDetails(@PathVariable String packageId,
                                     @RequestParam(name = "packageType", required = false) String packageType,
                                     Model model) {
        try {
            Long id = Long.parseLong(packageId);
            Optional<TravelPackage> pkgOpt = packageService.getPackageById(id);
            
            if (pkgOpt.isPresent()) {
                TravelPackage pkg = pkgOpt.get();
                model.addAttribute("package", pkg);
                return "contact-details";
            }
        } catch (NumberFormatException e) {
            // Invalid ID format
        }
        return "redirect:/packages";
    }

    @PostMapping("/book/{packageId}/select-options")
    public String showSelectOptions(@PathVariable String packageId,
                                   @RequestParam(name = "packageType", required = false) String packageType,
                                   @RequestParam(name = "customerName", required = false) String customerName,
                                   @RequestParam(name = "email", required = false) String email,
                                   @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                                   @RequestParam(name = "numberOfMembers", required = false) Integer numberOfMembers,
                                   @RequestParam(name = "travelerDetails", required = false) String travelerDetails,
                                   Model model) {
        try {
            Long id = Long.parseLong(packageId);
            Optional<TravelPackage> pkgOpt = packageService.getPackageById(id);
            
            if (pkgOpt.isPresent()) {
                TravelPackage pkg = pkgOpt.get();
                
                // Always use STANDARD package type
                PackageType packageTypeSelected = PackageType.STANDARD;
                
                Booking booking = new Booking();
                booking.setTravelPackage(pkg);
                booking.setPackageTypeSelected(packageTypeSelected);
                booking.setCustomerName(customerName);
                booking.setEmail(email);
                booking.setPhoneNumber(phoneNumber);
                booking.setNumberOfMembers(numberOfMembers != null ? numberOfMembers : 1);
                booking.setTravelerDetails(travelerDetails);
                
                // Set price - always use standard price
                booking.setTotalPrice(pkg.getStandardPrice());
                booking.setPriceCurrency(pkg.getPriceCurrency() != null ? pkg.getPriceCurrency() : "₹");
                
                // Fetch flights
                List<Flight> flights = flightRepository.findByDestination(pkg.getLocation());
                if (flights.isEmpty()) {
                    flights = flightRepository.findByDestinationContaining(pkg.getLocation());
                }
                model.addAttribute("flights", flights);
                
                // Also pass as JSON for JavaScript
                List<java.util.Map<String, Object>> flightsJson = new java.util.ArrayList<>();
                for (Flight flight : flights) {
                    java.util.Map<String, Object> flightMap = new java.util.HashMap<>();
                    flightMap.put("id", flight.getId());
                    flightMap.put("availableSeats", flight.getAvailableSeats() != null ? flight.getAvailableSeats() : "");
                    flightsJson.add(flightMap);
                }
                model.addAttribute("flightsJson", flightsJson);
                
                // Show all hotels
                List<Hotel> suggestedHotels = hotelRepository.findByLocation(pkg.getLocation());
                model.addAttribute("suggestedHotels", suggestedHotels);
                model.addAttribute("isLuxury", false);

                // Fetch buses and trains for domestic packages
                boolean isDomesticPackage = "DOMESTIC".equalsIgnoreCase(pkg.getPackageType());
                model.addAttribute("isDomesticPackage", isDomesticPackage);
                
                if (isDomesticPackage) {
                    List<com.example.demo.model.Bus> buses = busRepository.findByDestination(pkg.getLocation());
                    model.addAttribute("buses", buses);
                    
                    List<com.example.demo.model.Train> trains = trainRepository.findByDestination(pkg.getLocation());
                    model.addAttribute("trains", trains);
                } else {
                    model.addAttribute("buses", new java.util.ArrayList<>());
                    model.addAttribute("trains", new java.util.ArrayList<>());
                }

                model.addAttribute("booking", booking);
                model.addAttribute("package", pkg);
                return "select-options";
            }
        } catch (NumberFormatException e) {
            // Invalid ID format
        }
        return "redirect:/packages";
    }

    @PostMapping("/book/process")
    public String processBooking(@ModelAttribute Booking booking,
                                 @RequestParam(name = "packageTypeSelected", required = false) String packageTypeStr,
                                 @RequestParam(name = "selectedHotelId", required = false) Long selectedHotelId,
                                 @RequestParam(name = "selectedFlightId", required = false) Long selectedFlightId,
                                 @RequestParam(name = "selectedBusId", required = false) Long selectedBusId,
                                 @RequestParam(name = "selectedTrainId", required = false) Long selectedTrainId,
                                 @RequestParam(name = "selectedTrainClass", required = false) String selectedTrainClass,
                                 @RequestParam(name = "selectedSeat", required = false) String selectedSeat,
                                 @RequestParam(name = "selectedSeats", required = false) String selectedSeatsJson,
                                 @RequestParam(name = "selectedSeatsInt", required = false) String selectedSeatsIntJson,
                                 @RequestParam(name = "travelerDetails", required = false) String travelerDetailsJson,
                                 Model model) {
        try {
            // Log traveler details for debugging
            System.out.println("Received travelerDetails from form: " + booking.getTravelerDetails());
            System.out.println("Received travelerDetails from @RequestParam: " + travelerDetailsJson);
            
            // If travelerDetails is not set via @ModelAttribute, set it from @RequestParam
            if ((booking.getTravelerDetails() == null || booking.getTravelerDetails().isEmpty()) 
                && travelerDetailsJson != null && !travelerDetailsJson.isEmpty()) {
                booking.setTravelerDetails(travelerDetailsJson);
                System.out.println("Set travelerDetails from @RequestParam: " + travelerDetailsJson);
            }
            
            // Ensure travelerDetails is set
            if (booking.getTravelerDetails() == null || booking.getTravelerDetails().isEmpty()) {
                System.err.println("WARNING: travelerDetails is null or empty!");
            } else {
                System.out.println("Final travelerDetails value: " + booking.getTravelerDetails());
            }
            TravelPackage travelPackage = booking.getTravelPackage();
            if (travelPackage == null || travelPackage.getId() == null) {
                return "redirect:/packages";
            }

            // Get fresh package from database
            Optional<TravelPackage> pkgOpt = packageService.getPackageById(travelPackage.getId());
            if (pkgOpt.isEmpty()) {
                return "redirect:/packages";
            }
            TravelPackage pkg = pkgOpt.get();

            // Parse package type
            PackageType packageTypeSelected = PackageType.STANDARD; // Default
            if (packageTypeStr != null) {
                try {
                    packageTypeSelected = PackageType.valueOf(packageTypeStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    packageTypeSelected = PackageType.STANDARD;
                }
            }

            // Set price based on package type - ensure we use the correct price
            Double basePrice;
            Double windowSeatCharge = 0.0;
            
            if (packageTypeSelected == PackageType.STANDARD) {
                basePrice = pkg.getStandardPrice();
                if (basePrice == null || basePrice == 0) {
                    basePrice = pkg.getPrice(); // Fallback to regular price
                }
                
                // Standard: Add flight, bus, train, and hotel charges if selected
                Double flightCharge = 0.0;
                Double busCharge = 0.0;
                Double trainCharge = 0.0;
                Double hotelCharge = 0.0;
                
                if (selectedFlightId != null) {
                    Optional<Flight> flightOpt = flightRepository.findById(selectedFlightId);
                    if (flightOpt.isPresent()) {
                        Flight flight = flightOpt.get();
                        booking.setSelectedFlight(flight);
                        flightCharge = flight.getPrice() != null ? flight.getPrice() : 0.0;
                    }
                }
                
                if (selectedBusId != null) {
                    Optional<com.example.demo.model.Bus> busOpt = busRepository.findById(selectedBusId);
                    if (busOpt.isPresent()) {
                        com.example.demo.model.Bus bus = busOpt.get();
                        booking.setSelectedBus(bus);
                        busCharge = bus.getTicketPrice() != null ? bus.getTicketPrice() : 0.0;
                    }
                }
                
                if (selectedTrainId != null) {
                    Optional<com.example.demo.model.Train> trainOpt = trainRepository.findById(selectedTrainId);
                    if (trainOpt.isPresent()) {
                        com.example.demo.model.Train train = trainOpt.get();
                        booking.setSelectedTrain(train);
                        booking.setTrainClass(selectedTrainClass);
                        // Use AC or Sleeper price based on selected class
                        if ("AC".equalsIgnoreCase(selectedTrainClass) && train.getAcPrice() != null) {
                            trainCharge = train.getAcPrice();
                        } else if ("SLEEPER".equalsIgnoreCase(selectedTrainClass) && train.getSleeperPrice() != null) {
                            trainCharge = train.getSleeperPrice();
                        }
                    }
                }
                
                if (selectedHotelId != null) {
                    Optional<Hotel> hotelOpt = hotelRepository.findById(selectedHotelId);
                    if (hotelOpt.isPresent()) {
                        Hotel hotel = hotelOpt.get();
                        booking.setSelectedHotel(hotel);
                        // Calculate hotel charge based on package duration
                        if (hotel.getPricePerNight() != null && pkg.getDurationDays() != null) {
                            hotelCharge = hotel.getPricePerNight() * pkg.getDurationDays();
                        }
                    }
                }
                
                // Handle seat selection (from seat map or single seat)
                String finalSelectedSeats = null;
                if (selectedSeatsJson != null && !selectedSeatsJson.trim().isEmpty()) {
                    finalSelectedSeats = selectedSeatsJson.trim();
                } else if (selectedSeatsIntJson != null && !selectedSeatsIntJson.trim().isEmpty()) {
                    finalSelectedSeats = selectedSeatsIntJson.trim();
                } else if (selectedSeat != null && !selectedSeat.trim().isEmpty()) {
                    finalSelectedSeats = selectedSeat.trim();
                }
                
                if (finalSelectedSeats != null && !finalSelectedSeats.isEmpty()) {
                    booking.setSelectedSeat(finalSelectedSeats);
                    // For A1-A70, all are window seats, so charge 500 per seat
                    String seatsUpper = finalSelectedSeats.toUpperCase();
                    if (seatsUpper.matches(".*A\\d+.*")) {
                        // Count number of seats
                        int seatCount = 1;
                        if (finalSelectedSeats.startsWith("{")) {
                            // JSON format
                            try {
                                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                                java.util.Map<String, String> seatMap = mapper.readValue(finalSelectedSeats, java.util.Map.class);
                                seatCount = seatMap.size();
                            } catch (Exception e) {
                                seatCount = 1;
                            }
                        } else {
                            String[] seats = finalSelectedSeats.split(",");
                            seatCount = seats.length > 0 ? seats.length : 1;
                        }
                        windowSeatCharge = 500.0 * seatCount;
                        booking.setWindowSeatCharge(windowSeatCharge);
                    }
                }
                
                // Total = base price + flight + bus + train + hotel + window seat charge
                booking.setTotalPrice(basePrice + flightCharge + busCharge + trainCharge + hotelCharge + windowSeatCharge);
            }
            
            // Ensure price currency is set
            if (booking.getPriceCurrency() == null || booking.getPriceCurrency().isEmpty()) {
                booking.setPriceCurrency(pkg.getPriceCurrency() != null ? pkg.getPriceCurrency() : "₹");
            }

            // Create booking with package type logic
            Booking savedBooking = bookingService.createBooking(booking, pkg, packageTypeSelected);

            // Send booking confirmation email asynchronously
            try {
                emailService.sendBookingConfirmationEmail(savedBooking);
            } catch (Exception e) {
                System.err.println("Failed to send booking confirmation email: " + e.getMessage());
                // Don't fail the booking if email fails
            }

            model.addAttribute("booking", savedBooking);
            
            // Redirect to booking summary/success page after successful booking
            return "redirect:/book/summary?bookingId=" + savedBooking.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/packages";
        }
    }

    @GetMapping("/book/summary")
    public String bookingSummary(@RequestParam(required = false) Long bookingId, Model model) {
        try {
            if (bookingId == null) {
                return "redirect:/packages?error=noBookingId";
            }
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                return "redirect:/packages?error=bookingNotFound";
            }
            model.addAttribute("booking", booking);
            return "booking-summary";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/packages?error=serverError";
        }
    }

    @GetMapping("/book/success")
    public String bookingSuccess(@RequestParam(required = false) Long bookingId, Model model) {
        try {
            if (bookingId == null) {
                return "redirect:/packages?error=noBookingId";
            }
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                return "redirect:/packages?error=bookingNotFound";
            }
            model.addAttribute("booking", booking);
            return "booking-success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/packages?error=serverError";
        }
    }

    @GetMapping("/book/download-pdf/{bookingId}")
    public void downloadBookingPdf(@PathVariable Long bookingId, HttpServletResponse response) {
        try {
            Booking booking = bookingService.getBookingById(bookingId);
            byte[] pdfBytes = pdfService.generateBookingPdf(booking);
            
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=booking-" + booking.getBookingReference() + ".pdf");
            response.setContentLength(pdfBytes.length);
            
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book/flight")
    public String showFlightBooking(@RequestParam(required = false) String source,
                                   @RequestParam(required = false) String destination,
                                   Model model) {
        // Load all flights by default, or filter if search parameters provided
        List<Flight> flights;
        if (source != null && !source.isEmpty() && destination != null && !destination.isEmpty()) {
            // Search by route
            flights = flightRepository.findAll().stream()
                    .filter(f -> f.getOrigin().equalsIgnoreCase(source) && 
                                f.getDestination().equalsIgnoreCase(destination))
                    .toList();
        } else if (destination != null && !destination.isEmpty()) {
            flights = flightRepository.findByDestination(destination);
        } else {
            // Show all flights by default
            flights = flightRepository.findAll();
        }
        model.addAttribute("flights", flights);
        model.addAttribute("source", source != null ? source : "");
        model.addAttribute("destination", destination != null ? destination : "");
        return "flight-booking";
    }

    @GetMapping("/book/hotel")
    public String showHotelBooking(Model model) {
        return "hotel-booking";
    }

    @PostMapping("/book/flight/process")
    public String processFlightBooking(@ModelAttribute Booking booking) {
        // Legacy method - redirect to flights page instead
        return "redirect:/book/flight";
    }

    @PostMapping("/book/hotel/process")
    public String processHotelBooking(@ModelAttribute Booking booking) {
        // Legacy method - can be removed or updated
        return "redirect:/book/success";
    }
}
