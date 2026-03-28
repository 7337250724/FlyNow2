package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "destination_guidance")
public class DestinationGuidance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    @JsonIgnore
    private TravelPackage travelPackage;

    @Column(name = "route_planning", columnDefinition = "TEXT")
    private String routePlanning; // Detailed route information

    @Column(name = "best_seasons", length = 500)
    private String bestSeasons; // Best time to visit, e.g., "October to March"

    @Column(name = "required_documents", columnDefinition = "TEXT")
    private String requiredDocuments; // List of required documents

    @Column(name = "safety_suggestions", columnDefinition = "TEXT")
    private String safetySuggestions; // Safety tips and precautions

    @Column(name = "budget_recommendations", columnDefinition = "TEXT")
    private String budgetRecommendations; // Budget tips and recommendations

    @Column(name = "corporate_guidelines", columnDefinition = "TEXT")
    private String corporateGuidelines; // Corporate travel guidelines (if applicable)

    @Column(name = "family_activities", columnDefinition = "TEXT")
    private String familyActivities; // Family-friendly activities and suggestions

    @Column(name = "couple_activities", columnDefinition = "TEXT")
    private String coupleActivities; // Romantic activities and suggestions

    @Column(name = "adventure_details", columnDefinition = "TEXT")
    private String adventureDetails; // Adventure activities, treks, expeditions

    @Column(name = "spiritual_details", columnDefinition = "TEXT")
    private String spiritualDetails; // Spiritual and cultural information

    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public DestinationGuidance() {
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

    public String getRoutePlanning() {
        return routePlanning;
    }

    public void setRoutePlanning(String routePlanning) {
        this.routePlanning = routePlanning;
    }

    public String getBestSeasons() {
        return bestSeasons;
    }

    public void setBestSeasons(String bestSeasons) {
        this.bestSeasons = bestSeasons;
    }

    public String getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(String requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public String getSafetySuggestions() {
        return safetySuggestions;
    }

    public void setSafetySuggestions(String safetySuggestions) {
        this.safetySuggestions = safetySuggestions;
    }

    public String getBudgetRecommendations() {
        return budgetRecommendations;
    }

    public void setBudgetRecommendations(String budgetRecommendations) {
        this.budgetRecommendations = budgetRecommendations;
    }

    public String getCorporateGuidelines() {
        return corporateGuidelines;
    }

    public void setCorporateGuidelines(String corporateGuidelines) {
        this.corporateGuidelines = corporateGuidelines;
    }

    public String getFamilyActivities() {
        return familyActivities;
    }

    public void setFamilyActivities(String familyActivities) {
        this.familyActivities = familyActivities;
    }

    public String getCoupleActivities() {
        return coupleActivities;
    }

    public void setCoupleActivities(String coupleActivities) {
        this.coupleActivities = coupleActivities;
    }

    public String getAdventureDetails() {
        return adventureDetails;
    }

    public void setAdventureDetails(String adventureDetails) {
        this.adventureDetails = adventureDetails;
    }

    public String getSpiritualDetails() {
        return spiritualDetails;
    }

    public void setSpiritualDetails(String spiritualDetails) {
        this.spiritualDetails = spiritualDetails;
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

