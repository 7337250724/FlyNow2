package com.example.demo.dto;

public class PackageSummaryDTO {
    private Long id;
    private String name;
    private String location;
    private String packageType;
    private Integer durationDays;
    private Double price;
    private String priceCurrency;

    public PackageSummaryDTO() {
    }

    public PackageSummaryDTO(Long id, String name, String location, String packageType, 
                            Integer durationDays, Double price, String priceCurrency) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.packageType = packageType;
        this.durationDays = durationDays;
        this.price = price;
        this.priceCurrency = priceCurrency;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }
}

