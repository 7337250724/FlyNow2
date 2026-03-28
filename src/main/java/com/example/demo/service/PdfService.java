package com.example.demo.service;

import com.example.demo.model.Booking;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generateBookingPdf(Booking booking) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Color.BLACK);
            Paragraph title = new Paragraph("Travel Booking Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Booking Reference
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.DARK_GRAY);
            Paragraph bookingRef = new Paragraph("Booking Reference: " + booking.getBookingReference(), headerFont);
            bookingRef.setSpacingAfter(10);
            document.add(bookingRef);

            // Customer Information Table
            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);
            customerTable.setSpacingBefore(10);
            customerTable.setSpacingAfter(10);

            addTableHeader(customerTable, "Customer Information");
            addTableRow(customerTable, "Customer Name:", booking.getCustomerName());
            addTableRow(customerTable, "Email:", booking.getEmail());
            addTableRow(customerTable, "Phone Number:", booking.getPhoneNumber());
            addTableRow(customerTable, "Number of Travelers:", String.valueOf(booking.getNumberOfMembers()));
            document.add(customerTable);

            // Package Information Table
            PdfPTable packageTable = new PdfPTable(2);
            packageTable.setWidthPercentage(100);
            packageTable.setSpacingBefore(10);
            packageTable.setSpacingAfter(10);

            addTableHeader(packageTable, "Package Information");
            addTableRow(packageTable, "Package Name:", booking.getTravelPackage().getName());
            addTableRow(packageTable, "Location:", booking.getTravelPackage().getLocation());
            addTableRow(packageTable, "Duration:", booking.getTravelPackage().getDurationDays() + " Days");
            addTableRow(packageTable, "Package Type:", booking.getPackageTypeSelected().toString());
            addTableRow(packageTable, "Total Price:", 
                       (booking.getPriceCurrency() != null ? booking.getPriceCurrency() : "₹") + 
                       String.format("%.2f", booking.getTotalPrice()));
            
            // Add flight information for Luxury packages
            if (booking.getPackageTypeSelected() == com.example.demo.enums.PackageType.LUXURY && booking.getSelectedFlight() != null) {
                addTableRow(packageTable, "Flight Number:", booking.getSelectedFlight().getFlightNumber());
                addTableRow(packageTable, "Airline:", booking.getSelectedFlight().getAirline());
                addTableRow(packageTable, "Route:", booking.getSelectedFlight().getOrigin() + " → " + booking.getSelectedFlight().getDestination());
                addTableRow(packageTable, "Departure:", booking.getSelectedFlight().getDepartureTime());
                addTableRow(packageTable, "Arrival:", booking.getSelectedFlight().getArrivalTime());
                addTableRow(packageTable, "Class:", booking.getSelectedFlight().getClassType());
                if (booking.getSelectedSeat() != null && !booking.getSelectedSeat().isEmpty()) {
                    addTableRow(packageTable, "Seat Number:", booking.getSelectedSeat());
                }
            }
            
            // Add hotel information for Luxury packages
            if (booking.getPackageTypeSelected() == com.example.demo.enums.PackageType.LUXURY && booking.getSelectedHotel() != null) {
                addTableRow(packageTable, "Hotel Name:", booking.getSelectedHotel().getName());
                addTableRow(packageTable, "Hotel Location:", booking.getSelectedHotel().getLocation());
                if (booking.getSelectedHotel().getStarRating() != null) {
                    addTableRow(packageTable, "Star Rating:", booking.getSelectedHotel().getStarRating() + "★");
                }
                if (booking.getSelectedHotel().getRoomType() != null) {
                    addTableRow(packageTable, "Room Type:", booking.getSelectedHotel().getRoomType());
                }
            }
            
            document.add(packageTable);

            // Inclusions Table
            PdfPTable inclusionsTable = new PdfPTable(2);
            inclusionsTable.setWidthPercentage(100);
            inclusionsTable.setSpacingBefore(10);
            inclusionsTable.setSpacingAfter(10);

            addTableHeader(inclusionsTable, "Package Inclusions");
            addTableRow(inclusionsTable, "Flights:", 
                       (booking.getFlightsIncluded() != null && booking.getFlightsIncluded()) ? "Included" : "Not Included");
            addTableRow(inclusionsTable, "Hotels:", 
                       (booking.getHotelsIncluded() != null && booking.getHotelsIncluded()) ? "Included" : "Not Included");
            addTableRow(inclusionsTable, "Meals:", 
                       (booking.getMealsIncluded() != null && booking.getMealsIncluded()) ? "Included" : "Not Included");
            addTableRow(inclusionsTable, "Sightseeing:", 
                       (booking.getSightseeingIncluded() != null && booking.getSightseeingIncluded()) ? "Included" : "Not Included");
            addTableRow(inclusionsTable, "Transport:", 
                       (booking.getTransportIncluded() != null && booking.getTransportIncluded()) ? "Included" : "Not Included");
            document.add(inclusionsTable);

            // Selected Hotel (if applicable)
            if (booking.getSelectedHotel() != null) {
                PdfPTable hotelTable = new PdfPTable(2);
                hotelTable.setWidthPercentage(100);
                hotelTable.setSpacingBefore(10);
                hotelTable.setSpacingAfter(10);

                addTableHeader(hotelTable, "Selected Hotel");
                addTableRow(hotelTable, "Hotel Name:", booking.getSelectedHotel().getName());
                addTableRow(hotelTable, "Location:", booking.getSelectedHotel().getLocation());
                document.add(hotelTable);
            }

            // Booking Date
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
            Paragraph bookingDate = new Paragraph("Booking Date: " + 
                                                  booking.getBookingDate().format(formatter), dateFont);
            bookingDate.setAlignment(Element.ALIGN_CENTER);
            bookingDate.setSpacingBefore(20);
            document.add(bookingDate);

            // Footer
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY);
            Paragraph footer = new Paragraph("Thank you for choosing Fly Now Travel Agency!", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
    }

    private void addTableHeader(PdfPTable table, String headerText) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
        PdfPCell header = new PdfPCell(new Phrase(headerText, headerFont));
        header.setColspan(2);
        header.setBackgroundColor(new Color(241, 93, 48)); // #f15d30
        header.setPadding(10);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
    }

    public byte[] generateFlightBookingPdf(Booking booking) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Color.BLACK);
            Paragraph title = new Paragraph("Flight Booking Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Booking Reference
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.DARK_GRAY);
            Paragraph bookingRef = new Paragraph("Booking Reference: " + booking.getBookingReference(), headerFont);
            bookingRef.setSpacingAfter(10);
            document.add(bookingRef);

            // Customer Information Table
            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);
            customerTable.setSpacingBefore(10);
            customerTable.setSpacingAfter(10);

            addTableHeader(customerTable, "Customer Information");
            addTableRow(customerTable, "Customer Name:", booking.getCustomerName());
            addTableRow(customerTable, "Email:", booking.getEmail());
            addTableRow(customerTable, "Phone Number:", booking.getPhoneNumber());
            addTableRow(customerTable, "Number of Travelers:", String.valueOf(booking.getNumberOfMembers()));
            document.add(customerTable);

            // Flight Information Table
            if (booking.getSelectedFlight() != null) {
                PdfPTable flightTable = new PdfPTable(2);
                flightTable.setWidthPercentage(100);
                flightTable.setSpacingBefore(10);
                flightTable.setSpacingAfter(10);

                addTableHeader(flightTable, "Flight Information");
                addTableRow(flightTable, "Flight Number:", booking.getSelectedFlight().getFlightNumber());
                addTableRow(flightTable, "Airline:", booking.getSelectedFlight().getAirline());
                addTableRow(flightTable, "Route:", 
                           booking.getSelectedFlight().getOrigin() + " → " + booking.getSelectedFlight().getDestination());
                addTableRow(flightTable, "Departure Time:", booking.getSelectedFlight().getDepartureTime());
                addTableRow(flightTable, "Arrival Time:", booking.getSelectedFlight().getArrivalTime());
                
                if (booking.getSelectedClass() != null && !booking.getSelectedClass().isEmpty()) {
                    addTableRow(flightTable, "Class:", booking.getSelectedClass());
                } else if (booking.getSelectedFlight().getClassType() != null) {
                    addTableRow(flightTable, "Class:", booking.getSelectedFlight().getClassType());
                }
                
                if (booking.getSelectedSeat() != null && !booking.getSelectedSeat().isEmpty()) {
                    addTableRow(flightTable, "Seat Number:", booking.getSelectedSeat());
                }
                
                addTableRow(flightTable, "Total Price:", 
                           (booking.getPriceCurrency() != null ? booking.getPriceCurrency() : "₹") + 
                           String.format("%.2f", booking.getTotalPrice()));
                
                document.add(flightTable);
            }

            // Payment Information
            PdfPTable paymentTable = new PdfPTable(2);
            paymentTable.setWidthPercentage(100);
            paymentTable.setSpacingBefore(10);
            paymentTable.setSpacingAfter(10);

            addTableHeader(paymentTable, "Payment Information");
            addTableRow(paymentTable, "Payment Status:", booking.getPaymentStatus() != null ? booking.getPaymentStatus() : "PAID");
            if (booking.getUpiId() != null && !booking.getUpiId().isEmpty()) {
                addTableRow(paymentTable, "UPI ID:", booking.getUpiId());
            }
            document.add(paymentTable);

            // Booking Date
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
            if (booking.getBookingDate() != null) {
                Paragraph bookingDate = new Paragraph("Booking Date: " + 
                                                      booking.getBookingDate().format(formatter), dateFont);
                bookingDate.setAlignment(Element.ALIGN_CENTER);
                bookingDate.setSpacingBefore(20);
                document.add(bookingDate);
            }

            // Important Instructions
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLACK);
            Paragraph instructionsTitle = new Paragraph("Important Instructions", sectionFont);
            instructionsTitle.setSpacingBefore(20);
            instructionsTitle.setSpacingAfter(10);
            document.add(instructionsTitle);

            Font instructionFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
            Paragraph instruction1 = new Paragraph("• Check-in: Please arrive at the airport at least 2 hours before departure for domestic flights and 3 hours for international flights.", instructionFont);
            instruction1.setSpacingAfter(5);
            document.add(instruction1);

            Paragraph instruction2 = new Paragraph("• Documents Required: Carry a valid government-issued photo ID (Aadhaar, Passport, Driving License, or Voter ID).", instructionFont);
            instruction2.setSpacingAfter(5);
            document.add(instruction2);

            Paragraph instruction3 = new Paragraph("• Baggage: Check your airline's baggage allowance policy. Typically, 7kg hand baggage is allowed free of charge.", instructionFont);
            instruction3.setSpacingAfter(5);
            document.add(instruction3);

            String seatInfo = booking.getSelectedSeat() != null && !booking.getSelectedSeat().isEmpty() && !"AUTO".equals(booking.getSelectedSeat()) 
                ? "Your seat " + booking.getSelectedSeat() + " has been confirmed." 
                : "Your seat will be assigned at check-in.";
            Paragraph instruction4 = new Paragraph("• Seat Selection: " + seatInfo, instructionFont);
            instruction4.setSpacingAfter(5);
            document.add(instruction4);

            Paragraph instruction5 = new Paragraph("• Boarding Pass: Download your boarding pass from the airline's website or mobile app 24 hours before departure, or collect it at the airport.", instructionFont);
            instruction5.setSpacingAfter(5);
            document.add(instruction5);

            Paragraph instruction6 = new Paragraph("• Contact: For any queries, contact our customer support at support@flynow.com or call +91-1800-XXX-XXXX.", instructionFont);
            instruction6.setSpacingAfter(5);
            document.add(instruction6);

            Paragraph instruction7 = new Paragraph("• Changes/Cancellation: Flight changes or cancellations are subject to airline policies and may incur charges.", instructionFont);
            instruction7.setSpacingAfter(5);
            document.add(instruction7);

            Paragraph instruction8 = new Paragraph("• Health & Safety: Follow all airport and airline health protocols. Wear masks if required and maintain social distancing.", instructionFont);
            instruction8.setSpacingAfter(10);
            document.add(instruction8);

            // Footer
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY);
            Paragraph footer = new Paragraph("Thank you for choosing Fly Now Travel Agency!", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating flight booking PDF: " + e.getMessage(), e);
        }
    }

    public byte[] generateTrainBookingPdf(Booking booking) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Color.BLACK);
            Paragraph title = new Paragraph("Train Booking Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Booking Reference
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.DARK_GRAY);
            Paragraph bookingRef = new Paragraph("Booking Reference: " + booking.getBookingReference(), headerFont);
            bookingRef.setSpacingAfter(10);
            document.add(bookingRef);

            // Customer Information Table
            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);
            customerTable.setSpacingBefore(10);
            customerTable.setSpacingAfter(10);

            addTableHeader(customerTable, "Customer Information");
            addTableRow(customerTable, "Customer Name:", booking.getCustomerName());
            addTableRow(customerTable, "Email:", booking.getEmail());
            addTableRow(customerTable, "Phone Number:", booking.getPhoneNumber());
            addTableRow(customerTable, "Number of Travelers:", String.valueOf(booking.getNumberOfMembers()));
            document.add(customerTable);

            // Train Information Table
            if (booking.getSelectedTrain() != null) {
                PdfPTable trainTable = new PdfPTable(2);
                trainTable.setWidthPercentage(100);
                trainTable.setSpacingBefore(10);
                trainTable.setSpacingAfter(10);

                addTableHeader(trainTable, "Train Information");
                addTableRow(trainTable, "Train Name:", booking.getSelectedTrain().getTrainName());
                addTableRow(trainTable, "Train Number:", booking.getSelectedTrain().getTrainNumber());
                addTableRow(trainTable, "Route:", 
                           booking.getSelectedTrain().getSource() + " → " + booking.getSelectedTrain().getDestination());
                addTableRow(trainTable, "Departure Time:", booking.getSelectedTrain().getDepartureTime());
                addTableRow(trainTable, "Arrival Time:", booking.getSelectedTrain().getArrivalTime());
                
                if (booking.getSelectedClass() != null && !booking.getSelectedClass().isEmpty()) {
                    addTableRow(trainTable, "Class:", booking.getSelectedClass());
                }
                
                addTableRow(trainTable, "Total Price:", 
                           (booking.getPriceCurrency() != null ? booking.getPriceCurrency() : "₹") + 
                           String.format("%.2f", booking.getTotalPrice()));
                
                document.add(trainTable);
            }

            // Payment Information
            PdfPTable paymentTable = new PdfPTable(2);
            paymentTable.setWidthPercentage(100);
            paymentTable.setSpacingBefore(10);
            paymentTable.setSpacingAfter(10);

            addTableHeader(paymentTable, "Payment Information");
            addTableRow(paymentTable, "Payment Status:", booking.getPaymentStatus() != null ? booking.getPaymentStatus() : "PAID");
            if (booking.getUpiId() != null && !booking.getUpiId().isEmpty()) {
                addTableRow(paymentTable, "UPI ID:", booking.getUpiId());
            }
            document.add(paymentTable);

            // Booking Date
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
            if (booking.getBookingDate() != null) {
                Paragraph bookingDate = new Paragraph("Booking Date: " + 
                                                      booking.getBookingDate().format(formatter), dateFont);
                bookingDate.setAlignment(Element.ALIGN_CENTER);
                bookingDate.setSpacingBefore(20);
                document.add(bookingDate);
            }

            // Footer
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY);
            Paragraph footer = new Paragraph("Thank you for choosing Fly Now Travel Agency!", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating train booking PDF: " + e.getMessage(), e);
        }
    }

    public byte[] generateBusBookingPdf(Booking booking) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Color.BLACK);
            Paragraph title = new Paragraph("Bus Booking Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Booking Reference
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.DARK_GRAY);
            Paragraph bookingRef = new Paragraph("Booking Reference: " + booking.getBookingReference(), headerFont);
            bookingRef.setSpacingAfter(10);
            document.add(bookingRef);

            // Customer Information Table
            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);
            customerTable.setSpacingBefore(10);
            customerTable.setSpacingAfter(10);

            addTableHeader(customerTable, "Customer Information");
            addTableRow(customerTable, "Customer Name:", booking.getCustomerName());
            addTableRow(customerTable, "Email:", booking.getEmail());
            addTableRow(customerTable, "Phone Number:", booking.getPhoneNumber());
            addTableRow(customerTable, "Number of Travelers:", String.valueOf(booking.getNumberOfMembers()));
            document.add(customerTable);

            // Bus Information Table
            if (booking.getSelectedBus() != null) {
                PdfPTable busTable = new PdfPTable(2);
                busTable.setWidthPercentage(100);
                busTable.setSpacingBefore(10);
                busTable.setSpacingAfter(10);

                addTableHeader(busTable, "Bus Information");
                addTableRow(busTable, "Bus Name:", booking.getSelectedBus().getBusName());
                addTableRow(busTable, "Bus Type:", booking.getSelectedBus().getBusType());
                addTableRow(busTable, "Route:", 
                           booking.getSelectedBus().getSource() + " → " + booking.getSelectedBus().getDestination());
                addTableRow(busTable, "Departure Time:", booking.getSelectedBus().getDepartureTime());
                addTableRow(busTable, "Arrival Time:", booking.getSelectedBus().getArrivalTime());
                
                if (booking.getSelectedSeat() != null && !booking.getSelectedSeat().isEmpty()) {
                    addTableRow(busTable, "Seat Number:", booking.getSelectedSeat());
                }
                
                addTableRow(busTable, "Total Price:", 
                           (booking.getPriceCurrency() != null ? booking.getPriceCurrency() : "₹") + 
                           String.format("%.2f", booking.getTotalPrice()));
                
                document.add(busTable);
            }

            // Payment Information
            PdfPTable paymentTable = new PdfPTable(2);
            paymentTable.setWidthPercentage(100);
            paymentTable.setSpacingBefore(10);
            paymentTable.setSpacingAfter(10);

            addTableHeader(paymentTable, "Payment Information");
            addTableRow(paymentTable, "Payment Status:", booking.getPaymentStatus() != null ? booking.getPaymentStatus() : "PAID");
            if (booking.getUpiId() != null && !booking.getUpiId().isEmpty()) {
                addTableRow(paymentTable, "UPI ID:", booking.getUpiId());
            }
            document.add(paymentTable);

            // Booking Date
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
            if (booking.getBookingDate() != null) {
                Paragraph bookingDate = new Paragraph("Booking Date: " + 
                                                      booking.getBookingDate().format(formatter), dateFont);
                bookingDate.setAlignment(Element.ALIGN_CENTER);
                bookingDate.setSpacingBefore(20);
                document.add(bookingDate);
            }

            // Footer
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY);
            Paragraph footer = new Paragraph("Thank you for choosing Fly Now Travel Agency!", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating bus booking PDF: " + e.getMessage(), e);
        }
    }

    private void addTableRow(PdfPTable table, String label, String value) {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setPadding(8);
        labelCell.setBackgroundColor(new Color(245, 245, 245));
        table.addCell(labelCell);
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "N/A", valueFont));
        valueCell.setPadding(8);
        table.addCell(valueCell);
    }
}
