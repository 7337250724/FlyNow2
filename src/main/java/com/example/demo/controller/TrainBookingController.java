package com.example.demo.controller;

import com.example.demo.model.Booking;
import com.example.demo.model.Train;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.TrainRepository;
import com.example.demo.service.TrainService;
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
public class TrainBookingController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/book/train")
    public String searchTrains(@RequestParam(required = false) String source,
                              @RequestParam(required = false) String destination,
                              Model model) {
        List<Train> trains;
        if (source != null && !source.isEmpty() && destination != null && !destination.isEmpty()) {
            trains = trainService.getTrainsByRoute(source, destination);
        } else if (destination != null && !destination.isEmpty()) {
            trains = trainService.getTrainsByDestination(destination);
        } else {
            trains = trainService.getAllTrains();
        }
        model.addAttribute("trains", trains);
        model.addAttribute("source", source != null ? source : "");
        model.addAttribute("destination", destination != null ? destination : "");
        return "train-booking";
    }

    @GetMapping("/train/{id}/book")
    public String showTrainBookingForm(@PathVariable Long id,
                                      @RequestParam(required = false) String selectedClass,
                                      Model model) {
        Optional<Train> trainOpt = trainService.getTrainById(id);
        if (trainOpt.isPresent()) {
            Train train = trainOpt.get();
            model.addAttribute("train", train);
            model.addAttribute("selectedClass", selectedClass != null ? selectedClass : "Sleeper");
            return "train-booking-form";
        }
        return "redirect:/book/train";
    }

    @PostMapping("/train/book/process")
    public String processTrainBooking(@RequestParam Long trainId,
                                     @RequestParam String selectedClass,
                                     @RequestParam String customerName,
                                     @RequestParam String email,
                                     @RequestParam String phoneNumber,
                                     @RequestParam Integer numberOfMembers,
                                     @RequestParam String upiId,
                                     Model model,
                                     HttpServletResponse response) {
        try {
            Optional<Train> trainOpt = trainService.getTrainById(trainId);
            if (trainOpt.isEmpty()) {
                return "redirect:/book/train?error=notFound";
            }

            Train train = trainOpt.get();
            Booking booking = new Booking();
            booking.setSelectedTrain(train);
            booking.setSelectedClass(selectedClass);
            booking.setCustomerName(customerName);
            booking.setEmail(email);
            booking.setPhoneNumber(phoneNumber);
            booking.setNumberOfMembers(numberOfMembers);
            booking.setUpiId(upiId);
            booking.setPaymentStatus("PAID");

            // Calculate price based on class
            Double price = train.getSleeperPrice(); // Default to sleeper
            if ("AC".equalsIgnoreCase(selectedClass)) {
                price = train.getAcPrice();
            }

            booking.setTotalPrice(price * numberOfMembers);
            booking.setPriceCurrency(train.getPriceCurrency() != null ? train.getPriceCurrency() : "₹");
            booking.setBookingReference("TRAIN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

            Booking savedBooking = bookingRepository.save(booking);

            // Generate PDF
            try {
                byte[] pdfBytes = pdfService.generateTrainBookingPdf(savedBooking);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=train-ticket-" + savedBooking.getBookingReference() + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "redirect:/book/success?bookingId=" + savedBooking.getBookingReference() + "&type=train";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/book/train?error=failed";
        }
    }
}

