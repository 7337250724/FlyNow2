package com.example.demo.service;

import com.example.demo.model.Place;
import com.example.demo.model.PlaceImage;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.PlaceImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceImageRepository placeImageRepository;

    @Transactional
    public Place createPlace(Place place, List<String> imageUrls) {
        Place savedPlace = placeRepository.save(place);

        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                    PlaceImage image = new PlaceImage(imageUrl, savedPlace);
                    placeImageRepository.save(image);
                }
            }
        }

        return savedPlace;
    }

    @Transactional
    public Place updatePlace(Long id, Place place, List<String> imageUrls) {
        Optional<Place> existingPlace = placeRepository.findById(id);
        if (existingPlace.isPresent()) {
            Place updatedPlace = existingPlace.get();
            updatedPlace.setName(place.getName());
            updatedPlace.setDescription(place.getDescription());

            // Delete existing images and add new ones
            placeImageRepository.deleteByPlaceId(id);

            if (imageUrls != null && !imageUrls.isEmpty()) {
                for (String imageUrl : imageUrls) {
                    if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                        PlaceImage image = new PlaceImage(imageUrl, updatedPlace);
                        placeImageRepository.save(image);
                    }
                }
            }

            return placeRepository.save(updatedPlace);
        }
        throw new RuntimeException("Place not found with id: " + id);
    }

    @Transactional
    public void deletePlace(Long id) {
        if (placeRepository.existsById(id)) {
            placeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Place not found with id: " + id);
        }
    }

    public List<Place> getPlacesByCountryId(Long countryId) {
        return placeRepository.findByCountryId(countryId);
    }

    public Place getPlaceById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + id));
    }
}
