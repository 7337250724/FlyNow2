package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "place_images")
public class PlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    public PlaceImage() {
    }

    public PlaceImage(String imageUrl, Place place) {
        this.imageUrl = imageUrl;
        this.place = place;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
