package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "transport_options")
public class TransportOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    @JsonIgnore
    private TravelPackage travelPackage;

    @Column(nullable = false, length = 20)
    private String type; // "FLIGHT", "TRAIN", "BUS"

    @Column(nullable = false)
    private String provider; // e.g., "IndiGo", "Rajdhani Express"

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String duration; // e.g., "2h 30m"

    @Column(name = "departure_time")
    private String departureTime; // e.g., "10:00 AM"

    @Column(name = "arrival_time")
    private String arrivalTime; // e.g., "12:30 PM"

    public TransportOption() {
    }

    public TransportOption(String type, String provider, Double price, String duration, String departureTime,
            String arrivalTime) {
        this.type = type;
        this.provider = provider;
        this.price = price;
        this.duration = duration;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TravelPackage getTravelPackage() {
        return travelPackage;
    }

    public void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
}
