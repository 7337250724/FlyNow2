package com.example.demo.model;

import java.util.List;

public class Package {
    private Long id;
    private String title;
    private String region; // NATIONAL or INTERNATIONAL
    private String heroImageUrl;
    private Integer durationDays;
    private Integer durationNights;
    private String shortDescription;
    private String highlight;
    private Double price;
    private List<String> activities;
    private List<DayItinerary> itinerary;
    private List<String> galleryImageUrls;
    private String aboutDescription;
    private List<String> whatsIncluded;
    private List<String> whatsNotIncluded;

    public Package() {
    }

    public Package(Long id, String title, String region, String heroImageUrl, Integer durationDays,
            Integer durationNights, String shortDescription, String highlight, Double price) {
        this.id = id;
        this.title = title;
        this.region = region;
        this.heroImageUrl = heroImageUrl;
        this.durationDays = durationDays;
        this.durationNights = durationNights;
        this.shortDescription = shortDescription;
        this.highlight = highlight;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getHeroImageUrl() {
        return heroImageUrl;
    }

    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public Integer getDurationNights() {
        return durationNights;
    }

    public void setDurationNights(Integer durationNights) {
        this.durationNights = durationNights;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public List<DayItinerary> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<DayItinerary> itinerary) {
        this.itinerary = itinerary;
    }

    public List<String> getGalleryImageUrls() {
        return galleryImageUrls;
    }

    public void setGalleryImageUrls(List<String> galleryImageUrls) {
        this.galleryImageUrls = galleryImageUrls;
    }

    public String getAboutDescription() {
        return aboutDescription;
    }

    public void setAboutDescription(String aboutDescription) {
        this.aboutDescription = aboutDescription;
    }

    public List<String> getWhatsIncluded() {
        return whatsIncluded;
    }

    public void setWhatsIncluded(List<String> whatsIncluded) {
        this.whatsIncluded = whatsIncluded;
    }

    public List<String> getWhatsNotIncluded() {
        return whatsNotIncluded;
    }

    public void setWhatsNotIncluded(List<String> whatsNotIncluded) {
        this.whatsNotIncluded = whatsNotIncluded;
    }

    public static class DayItinerary {
        private String day;
        private String title;
        private String description;

        public DayItinerary() {
        }

        public DayItinerary(String day, String title, String description) {
            this.day = day;
            this.title = title;
            this.description = description;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

