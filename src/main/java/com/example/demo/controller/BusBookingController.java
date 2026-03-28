package com.example.demo.controller;

import com.example.demo.model.Booking;
import com.example.demo.model.Bus;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.BusRepository;
import com.example.demo.service.BusService;
import com.example.demo.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class BusBookingController {

    @Autowired
    private BusService busService;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/book/bus")
    public String searchBuses(@RequestParam(required = false) String source,
            @RequestParam(required = false) String destination,
            Model model) {
        List<Bus> buses;
        if (source != null && !source.isEmpty() && destination != null && !destination.isEmpty()) {
            buses = busService.getBusesByRoute(source, destination);
        } else if (destination != null && !destination.isEmpty()) {
            buses = busService.getBusesByDestination(destination);
        } else {
            buses = busService.getAllBuses();
        }
        model.addAttribute("buses", buses);
        model.addAttribute("source", source != null ? source : "");
        model.addAttribute("destination", destination != null ? destination : "");
        return "bus-booking";
    }

    @GetMapping("/bus/{id}/book")
    public String showBusBookingForm(@PathVariable Long id,
            @RequestParam(required = false) String selectedSeat,
            Model model) {
        Optional<Bus> busOpt = busService.getBusById(id);
        if (busOpt.isPresent()) {
            Bus bus = busOpt.get();
            model.addAttribute("bus", bus);
            model.addAttribute("selectedSeat", selectedSeat != null ? selectedSeat : "");
            return "bus-booking-form";
        }
        return "redirect:/book/bus";
    }

    @PostMapping("/bus/book/process")
    public String processBusBooking(@RequestParam Long busId,
            @RequestParam String selectedSeat,
            @RequestParam String customerName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam Integer numberOfMembers,
            @RequestParam(required = false) String travelerDetails,
            @RequestParam String upiId,
            Model model,
            HttpServletResponse response) {
        try {
            Optional<Bus> busOpt = busService.getBusById(busId);
            if (busOpt.isEmpty()) {
                return "redirect:/book/bus?error=notFound";
            }

            Bus bus = busOpt.get();
            Booking booking = new Booking();
            booking.setSelectedBus(bus);
            booking.setSelectedSeat(selectedSeat);
            booking.setCustomerName(customerName);
            booking.setEmail(email);
            booking.setPhoneNumber(phoneNumber);
            booking.setNumberOfMembers(numberOfMembers);
            booking.setTravelerDetails(travelerDetails);
            booking.setUpiId(upiId);
            booking.setPaymentStatus("PAID");

            booking.setTotalPrice(bus.getTicketPrice() * numberOfMembers);
            booking.setPriceCurrency(bus.getPriceCurrency() != null ? bus.getPriceCurrency() : "₹");
            booking.setBookingReference("BUS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

            Booking savedBooking = bookingRepository.save(booking);

            // Generate PDF
            try {
                byte[] pdfBytes = pdfService.generateBusBookingPdf(savedBooking);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition",
                        "attachment; filename=bus-ticket-" + savedBooking.getBookingReference() + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "redirect:/book/success?bookingId=" + savedBooking.getBookingReference() + "&type=bus";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/book/bus?error=failed";
        }
    }
}
