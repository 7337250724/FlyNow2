package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.model.HotelBooking;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.name:FlyNow Travel Agency}")
    private String appName;

    @Async("emailExecutor")
    public void sendBookingConfirmationEmail(Booking booking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, appName);
            helper.setTo(booking.getEmail());
            helper.setSubject("🎉 Your Trip is Confirmed! - Booking Reference: " + booking.getBookingReference());

            // Prepare template context
            Context context = new Context(Locale.ENGLISH);
            context.setVariable("booking", booking);
            context.setVariable("appName", appName);
            context.setVariable("bookingDate", booking.getBookingDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a")));
            
            // Package details
            if (booking.getTravelPackage() != null) {
                context.setVariable("packageName", booking.getTravelPackage().getName());
                context.setVariable("packageLocation", booking.getTravelPackage().getLocation());
                context.setVariable("durationDays", booking.getTravelPackage().getDurationDays());
            }
            
            // Transport details
            context.setVariable("hasFlight", booking.getSelectedFlight() != null);
            context.setVariable("hasBus", booking.getSelectedBus() != null);
            context.setVariable("hasTrain", booking.getSelectedTrain() != null);
            context.setVariable("hasHotel", booking.getSelectedHotel() != null);
            
            // Parse traveler details from JSON
            List<Map<String, Object>> travelers = new ArrayList<>();
            if (booking.getTravelerDetails() != null && !booking.getTravelerDetails().isEmpty()) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    travelers = objectMapper.readValue(booking.getTravelerDetails(), 
                        new TypeReference<List<Map<String, Object>>>() {});
                } catch (Exception e) {
                    System.err.println("Error parsing traveler details JSON: " + e.getMessage());
                }
            }
            context.setVariable("travelers", travelers);
            
            // Generate HTML email content
            String htmlContent = templateEngine.process("email/booking-confirmation", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("Booking confirmation email sent to: " + booking.getEmail());
        } catch (MessagingException e) {
            System.err.println("Failed to send booking confirmation email: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Async("emailExecutor")
    public void sendHotelBookingConfirmationEmail(HotelBooking booking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, appName);
            helper.setTo(booking.getEmail());
            helper.setSubject("Hotel Booking Confirmation - " + booking.getBookingReference());

            // Prepare template context
            Context context = new Context(Locale.ENGLISH);
            context.setVariable("booking", booking);
            context.setVariable("appName", appName);
            context.setVariable("bookingDate", booking.getBookingDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a")));
            
            if (booking.getHotel() != null) {
                context.setVariable("hotelName", booking.getHotel().getName());
                context.setVariable("hotelLocation", booking.getHotel().getLocation());
                context.setVariable("hotelRating", booking.getHotel().getStarRating());
            }
            
            if (booking.getCheckInDate() != null && booking.getCheckOutDate() != null) {
                long nights = java.time.temporal.ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
                context.setVariable("numberOfNights", nights);
            }

            // Generate HTML email content
            String htmlContent = templateEngine.process("email/hotel-booking-confirmation", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("Hotel booking confirmation email sent to: " + booking.getEmail());
        } catch (MessagingException e) {
            System.err.println("Failed to send hotel booking confirmation email: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error sending hotel booking email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

