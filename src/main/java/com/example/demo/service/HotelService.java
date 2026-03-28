package com.example.demo.service;

import com.example.demo.dto.HotelSummaryDTO;
import com.example.demo.model.Hotel;
import com.example.demo.model.HotelImage;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Transactional
    public Hotel createHotel(Hotel hotel, List<String> galleryImageUrls) {
        Hotel savedHotel = hotelRepository.save(hotel);

        if (galleryImageUrls != null && !galleryImageUrls.isEmpty()) {
            List<HotelImage> galleryImages = new ArrayList<>();
            for (int i = 0; i < galleryImageUrls.size(); i++) {
                String imageUrl = galleryImageUrls.get(i);
                if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                    HotelImage image = new HotelImage();
                    image.setHotel(savedHotel);
                    image.setImageUrl(imageUrl);
                    image.setDisplayOrder(i);
                    galleryImages.add(image);
                }
            }
            savedHotel.setGalleryImages(galleryImages);
        }

        return hotelRepository.save(savedHotel);
    }

    @Transactional
    public Hotel updateHotel(Long id, Hotel hotel, List<String> galleryImageUrls) {
        Optional<Hotel> existingOpt = hotelRepository.findByIdWithImages(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Hotel not found");
        }

        Hotel existing = existingOpt.get();

        // Update basic fields
        existing.setName(hotel.getName());
        existing.setLocation(hotel.getLocation());
        existing.setHotelType(hotel.getHotelType());
        existing.setCountry(hotel.getCountry());
        existing.setCity(hotel.getCity());
        existing.setHeroImageUrl(hotel.getHeroImageUrl());
        existing.setPricePerNight(hotel.getPricePerNight());
        existing.setPriceCurrency(hotel.getPriceCurrency());
        existing.setRating(hotel.getRating());
        existing.setDescription(hotel.getDescription());
        existing.setAmenities(hotel.getAmenities());
        existing.setCheckInTime(hotel.getCheckInTime());
        existing.setCheckOutTime(hotel.getCheckOutTime());
        existing.setContactPhone(hotel.getContactPhone());
        existing.setContactEmail(hotel.getContactEmail());
        existing.setAddress(hotel.getAddress());

        // Clear and update gallery images
        if (existing.getGalleryImages() == null) {
            existing.setGalleryImages(new ArrayList<>());
        } else {
            existing.getGalleryImages().clear();
        }

        if (galleryImageUrls != null && !galleryImageUrls.isEmpty()) {
            for (int i = 0; i < galleryImageUrls.size(); i++) {
                String imageUrl = galleryImageUrls.get(i);
                if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                    HotelImage image = new HotelImage();
                    image.setHotel(existing);
                    image.setImageUrl(imageUrl);
                    image.setDisplayOrder(i);
                    existing.getGalleryImages().add(image);
                }
            }
        }

        return hotelRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAllWithImages();
        return hotels;
    }

    @Transactional(readOnly = true)
    public List<HotelSummaryDTO> getAllHotelsForList() {
        return hotelRepository.findAllSimple().stream()
                .map(hotel -> new HotelSummaryDTO(
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getLocation(),
                        hotel.getHotelType(),
                        hotel.getCountry(),
                        hotel.getCity(),
                        hotel.getPricePerNight(),
                        hotel.getPriceCurrency(),
                        hotel.getRating()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Hotel> getHotelsByType(String hotelType) {
        List<Hotel> hotels = hotelRepository.findByHotelTypeWithImages(hotelType);
        return hotels;
    }

    @Transactional(readOnly = true)
    public Optional<Hotel> getHotelById(Long id) {
        Optional<Hotel> hotelOpt = hotelRepository.findByIdWithImages(id);
        return hotelOpt;
    }

    @Transactional
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}
