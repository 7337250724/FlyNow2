package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "flights")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_number", nullable = false, unique = true)
    private String flightNumber;

    @Column(name = "airline", nullable = false)
    private String airline;

    @Column(name = "origin", nullable = false)
    private String origin;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "departure_time", nullable = false)
    private String departureTime; // e.g., "10:30 AM"

    @Column(name = "arrival_time", nullable = false)
    private String arrivalTime; // e.g., "2:45 PM"

    @Column(name = "flight_date")
    @Temporal(TemporalType.DATE)
    private Date flightDate;

    @Column(name = "class_type", length = 50)
    private String classType; // "Economy", "Business", "First" (default/primary class)

    @Column(name = "price", nullable = false)
    private Double price; // Economy price (base price)

    @Column(name = "business_price")
    private Double businessPrice;

    @Column(name = "first_class_price")
    private Double firstClassPrice;

    @Column(name = "price_currency", length = 10)
    private String priceCurrency; // "₹" or "$"

    @Column(name = "economy_seats")
    private Integer economySeats;

    @Column(name = "business_seats")
    private Integer businessSeats;

    @Column(name = "first_class_seats")
    private Integer firstClassSeats;

    @Column(name = "available_seats", length = 1000)
    private String availableSeats; // Comma-separated: "A1,A2,A3,B1,B2,B3" (for economy)

    @Column(name = "business_available_seats", length = 1000)
    private String businessAvailableSeats;

    @Column(name = "first_class_available_seats", length = 1000)
    private String firstClassAvailableSeats;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Flight() {
        this.classType = "Economy";
        this.priceCurrency = "₹";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(Double businessPrice) {
        this.businessPrice = businessPrice;
    }

    public Double getFirstClassPrice() {
        return firstClassPrice;
    }

    public void setFirstClassPrice(Double firstClassPrice) {
        this.firstClassPrice = firstClassPrice;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public Integer getEconomySeats() {
        return economySeats;
    }

    public void setEconomySeats(Integer economySeats) {
        this.economySeats = economySeats;
    }

    public Integer getBusinessSeats() {
        return businessSeats;
    }

    public void setBusinessSeats(Integer businessSeats) {
        this.businessSeats = businessSeats;
    }

    public Integer getFirstClassSeats() {
        return firstClassSeats;
    }

    public void setFirstClassSeats(Integer firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }

    public String getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(String availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getBusinessAvailableSeats() {
        return businessAvailableSeats;
    }

    public void setBusinessAvailableSeats(String businessAvailableSeats) {
        this.businessAvailableSeats = businessAvailableSeats;
    }

    public String getFirstClassAvailableSeats() {
        return firstClassAvailableSeats;
    }

    public void setFirstClassAvailableSeats(String firstClassAvailableSeats) {
        this.firstClassAvailableSeats = firstClassAvailableSeats;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}

