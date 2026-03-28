package com.example.demo.dto;

public class HotelSummaryDTO {
    private Long id;
    private String name;
    private String location;
    private String hotelType;
    private String country;
    private String city;
    private Double pricePerNight;
    private String priceCurrency;
    private Double rating;

    public HotelSummaryDTO(Long id, String name, String location, String hotelType, String country, String city, Double pricePerNight, String priceCurrency, Double rating) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.hotelType = hotelType;
        this.country = country;
        this.city = city;
        this.pricePerNight = pricePerNight;
        this.priceCurrency = priceCurrency;
        this.rating = rating;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getHotelType() {
        return hotelType;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public Double getRating() {
        return rating;
    }
}

