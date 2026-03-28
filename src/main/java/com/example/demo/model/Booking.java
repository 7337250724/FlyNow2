package com.example.demo.model;

import com.example.demo.enums.PackageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_reference", unique = true, nullable = false)
    private String bookingReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = true)
    @JsonIgnore
    private TravelPackage travelPackage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @JsonIgnore
    private Hotel selectedHotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    @JsonIgnore
    private Flight selectedFlight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id")
    @JsonIgnore
    private Train selectedTrain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id")
    @JsonIgnore
    private Bus selectedBus;

    @Column(name = "selected_seat", length = 50)
    private String selectedSeat; // e.g., "A1", "B2"

    @Column(name = "selected_class", length = 50)
    private String selectedClass; // "Economy", "Business", "First", "Sleeper", "AC", "Non-AC"

    @Column(name = "train_class", length = 50)
    private String trainClass; // "Sleeper", "AC"

    @Column(name = "bus_type_selected", length = 50)
    private String busTypeSelected; // "AC", "NON_AC", "SLEEPER"

    @Enumerated(EnumType.STRING)
    @Column(name = "package_type_selected", nullable = true)
    private PackageType packageTypeSelected; // STANDARD or LUXURY (null for standalone bookings)

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "number_of_members", nullable = false)
    private Integer numberOfMembers;

    @Column(name = "traveler_details", columnDefinition = "TEXT")
    private String travelerDetails; // JSON string of traveler details

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "price_currency", length = 10)
    private String priceCurrency;

    @Column(name = "upi_id")
    private String upiId;

    @Column(name = "payment_status", length = 50)
    private String paymentStatus; // "PENDING", "PAID", "CANCELLED"

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    // Package type specific inclusions
    @Column(name = "flights_included")
    private Boolean flightsIncluded;

    @Column(name = "hotels_included")
    private Boolean hotelsIncluded;

    @Column(name = "meals_included")
    private Boolean mealsIncluded;

    @Column(name = "sightseeing_included")
    private Boolean sightseeingIncluded;

    @Column(name = "transport_included")
    private Boolean transportIncluded;

    @Column(name = "window_seat_charge")
    private Double windowSeatCharge; // Extra charge for window seat (500)

    public Booking() {
        this.bookingReference = "PKG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.bookingDate = LocalDateTime.now();
        this.paymentStatus = "PENDING";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public TravelPackage getTravelPackage() {
        return travelPackage;
    }

    public void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;
    }

    public Hotel getSelectedHotel() {
        return selectedHotel;
    }

    public void setSelectedHotel(Hotel selectedHotel) {
        this.selectedHotel = selectedHotel;
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public String getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(String selectedSeat) {
        this.selectedSeat = selectedSeat;
    }

    public Train getSelectedTrain() {
        return selectedTrain;
    }

    public void setSelectedTrain(Train selectedTrain) {
        this.selectedTrain = selectedTrain;
    }

    public Bus getSelectedBus() {
        return selectedBus;
    }

    public void setSelectedBus(Bus selectedBus) {
        this.selectedBus = selectedBus;
    }

    public String getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
    }

    public String getTrainClass() {
        return trainClass;
    }

    public void setTrainClass(String trainClass) {
        this.trainClass = trainClass;
    }

    public String getBusTypeSelected() {
        return busTypeSelected;
    }

    public void setBusTypeSelected(String busTypeSelected) {
        this.busTypeSelected = busTypeSelected;
    }

    public PackageType getPackageTypeSelected() {
        return packageTypeSelected;
    }

    public void setPackageTypeSelected(PackageType packageTypeSelected) {
        this.packageTypeSelected = packageTypeSelected;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(Integer numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public String getTravelerDetails() {
        return travelerDetails;
    }

    public void setTravelerDetails(String travelerDetails) {
        this.travelerDetails = travelerDetails;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Boolean getFlightsIncluded() {
        return flightsIncluded;
    }

    public void setFlightsIncluded(Boolean flightsIncluded) {
        this.flightsIncluded = flightsIncluded;
    }

    public Boolean getHotelsIncluded() {
        return hotelsIncluded;
    }

    public void setHotelsIncluded(Boolean hotelsIncluded) {
        this.hotelsIncluded = hotelsIncluded;
    }

    public Boolean getMealsIncluded() {
        return mealsIncluded;
    }

    public void setMealsIncluded(Boolean mealsIncluded) {
        this.mealsIncluded = mealsIncluded;
    }

    public Boolean getSightseeingIncluded() {
        return sightseeingIncluded;
    }

    public void setSightseeingIncluded(Boolean sightseeingIncluded) {
        this.sightseeingIncluded = sightseeingIncluded;
    }

    public Boolean getTransportIncluded() {
        return transportIncluded;
    }

    public void setTransportIncluded(Boolean transportIncluded) {
        this.transportIncluded = transportIncluded;
    }

    public Double getWindowSeatCharge() {
        return windowSeatCharge;
    }

    public void setWindowSeatCharge(Double windowSeatCharge) {
        this.windowSeatCharge = windowSeatCharge;
    }
}
