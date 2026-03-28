package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "travel_packages")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(name = "package_type", nullable = false)
    private String packageType; // "DOMESTIC" or "INTERNATIONAL"

    @Column(name = "hero_image_url")
    private String heroImageUrl;

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @Column(name = "price", nullable = false)
    private Double price; // Legacy field - use standardPrice or luxuryPrice

    @Column(name = "standard_price", nullable = false)
    private Double standardPrice;

    @Column(name = "luxury_price", nullable = false)
    private Double luxuryPrice;

    @Column(name = "price_currency", length = 10)
    private String priceCurrency; // "₹" or "$"

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "about_description", length = 2000)
    private String aboutDescription;

    @Column(name = "visa_requirement")
    private String visaRequirement; // "VISA_REQUIRED", "VISA_FREE", "VISA_ON_ARRIVAL" (only for international)

    // Travel Type Categories (can be multiple, comma-separated)
    @Column(name = "travel_types", length = 500)
    private String travelTypes; // "CORPORATE,FAMILY,COUPLE,ADVENTURE,SPIRITUAL,SOLO,BUDGET,LUXURY"

    // Season Type (can be multiple, comma-separated)
    @Column(name = "seasons", length = 200)
    private String seasons; // "WINTER,SUMMER,RAINY,SPRING,MONSOON"

    @Column(name = "is_family_friendly")
    private Boolean isFamilyFriendly; // true if suitable for families with children

    @Column(name = "is_couple_friendly")
    private Boolean isCoupleFriendly; // true if suitable for couples/honeymoon

    @Column(name = "is_adventure")
    private Boolean isAdventure; // true if adventure/trekking package

    @Column(name = "is_spiritual")
    private Boolean isSpiritual; // true if spiritual/cultural package

    @Column(name = "is_corporate")
    private Boolean isCorporate; // true if corporate travel package

    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private List<PackageItineraryDay> itineraryDays;

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private List<PackageFeature> features;

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private List<PackageImage> galleryImages;

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private List<TransportOption> transportOptions;

    public TravelPackage() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
        // Set default standard and luxury prices if not set
        if (this.standardPrice == null) {
            this.standardPrice = price;
        }
        if (this.luxuryPrice == null) {
            this.luxuryPrice = price * 1.5; // Default luxury is 50% more
        }
    }

    public Double getStandardPrice() {
        if (standardPrice != null) {
            return standardPrice;
        }
        if (price != null) {
            return price;
        }
        return 0.0; // Default to 0 if both are null
    }

    public void setStandardPrice(Double standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Double getLuxuryPrice() {
        if (luxuryPrice != null) {
            return luxuryPrice;
        }
        if (price != null) {
            return price * 1.5; // Default luxury is 50% more
        }
        return 0.0; // Default to 0 if price is null
    }

    public void setLuxuryPrice(Double luxuryPrice) {
        this.luxuryPrice = luxuryPrice;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAboutDescription() {
        return aboutDescription;
    }

    public void setAboutDescription(String aboutDescription) {
        this.aboutDescription = aboutDescription;
    }

    public String getVisaRequirement() {
        return visaRequirement;
    }

    public void setVisaRequirement(String visaRequirement) {
        this.visaRequirement = visaRequirement;
    }

    public String getTravelTypes() {
        return travelTypes;
    }

    public void setTravelTypes(String travelTypes) {
        this.travelTypes = travelTypes;
    }

    public String getSeasons() {
        return seasons;
    }

    public void setSeasons(String seasons) {
        this.seasons = seasons;
    }

    public Boolean getIsFamilyFriendly() {
        return isFamilyFriendly;
    }

    public void setIsFamilyFriendly(Boolean isFamilyFriendly) {
        this.isFamilyFriendly = isFamilyFriendly;
    }

    public Boolean getIsCoupleFriendly() {
        return isCoupleFriendly;
    }

    public void setIsCoupleFriendly(Boolean isCoupleFriendly) {
        this.isCoupleFriendly = isCoupleFriendly;
    }

    public Boolean getIsAdventure() {
        return isAdventure;
    }

    public void setIsAdventure(Boolean isAdventure) {
        this.isAdventure = isAdventure;
    }

    public Boolean getIsSpiritual() {
        return isSpiritual;
    }

    public void setIsSpiritual(Boolean isSpiritual) {
        this.isSpiritual = isSpiritual;
    }

    public Boolean getIsCorporate() {
        return isCorporate;
    }

    public void setIsCorporate(Boolean isCorporate) {
        this.isCorporate = isCorporate;
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

    public List<PackageItineraryDay> getItineraryDays() {
        return itineraryDays;
    }

    public void setItineraryDays(List<PackageItineraryDay> itineraryDays) {
        this.itineraryDays = itineraryDays;
    }

    public List<PackageFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<PackageFeature> features) {
        this.features = features;
    }

    public List<PackageImage> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(List<PackageImage> galleryImages) {
        this.galleryImages = galleryImages;
    }

    public List<TransportOption> getTransportOptions() {
        return transportOptions;
    }

    public void setTransportOptions(List<TransportOption> transportOptions) {
        this.transportOptions = transportOptions;
    }
}
