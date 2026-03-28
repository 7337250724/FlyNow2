package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trains")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_name", nullable = false)
    private String trainName;

    @Column(name = "train_number", nullable = false, unique = true)
    private String trainNumber;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "departure_time", nullable = false)
    private String departureTime; // e.g., "10:30 AM"

    @Column(name = "arrival_time", nullable = false)
    private String arrivalTime; // e.g., "2:45 PM"

    @Column(name = "travel_date")
    @Temporal(TemporalType.DATE)
    private Date travelDate;

    @Column(name = "sleeper_seats")
    private Integer sleeperSeats;

    @Column(name = "ac_seats")
    private Integer acSeats;

    @Column(name = "sleeper_price", nullable = false)
    private Double sleeperPrice;

    @Column(name = "ac_price", nullable = false)
    private Double acPrice;

    @Column(name = "price_currency", length = 10)
    private String priceCurrency; // "₹" or "$"

    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Train() {
        this.priceCurrency = "₹";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public Integer getSleeperSeats() {
        return sleeperSeats;
    }

    public void setSleeperSeats(Integer sleeperSeats) {
        this.sleeperSeats = sleeperSeats;
    }

    public Integer getAcSeats() {
        return acSeats;
    }

    public void setAcSeats(Integer acSeats) {
        this.acSeats = acSeats;
    }

    public Double getSleeperPrice() {
        return sleeperPrice;
    }

    public void setSleeperPrice(Double sleeperPrice) {
        this.sleeperPrice = sleeperPrice;
    }

    public Double getAcPrice() {
        return acPrice;
    }

    public void setAcPrice(Double acPrice) {
        this.acPrice = acPrice;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
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

