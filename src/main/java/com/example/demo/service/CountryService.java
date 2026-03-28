package com.example.demo.service;

import com.example.demo.model.Country;
import com.example.demo.model.CountryImage;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.CountryImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryImageRepository countryImageRepository;

    @Transactional
    public Country createCountry(Country country, List<String> imageUrls) {
        Country savedCountry = countryRepository.save(country);

        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                    CountryImage image = new CountryImage(imageUrl, savedCountry);
                    countryImageRepository.save(image);
                }
            }
        }

        return savedCountry;
    }

    @Transactional
    public Country updateCountry(Long id, Country country, List<String> imageUrls) {
        Optional<Country> existingCountry = countryRepository.findById(id);
        if (existingCountry.isPresent()) {
            Country updatedCountry = existingCountry.get();
            updatedCountry.setName(country.getName());
            updatedCountry.setDescription(country.getDescription());
            updatedCountry.setHeroImage(country.getHeroImage());
            updatedCountry.setRegion(country.getRegion());

            // Delete existing images and add new ones
            countryImageRepository.deleteByCountryId(id);

            if (imageUrls != null && !imageUrls.isEmpty()) {
                for (String imageUrl : imageUrls) {
                    if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                        CountryImage image = new CountryImage(imageUrl, updatedCountry);
                        countryImageRepository.save(image);
                    }
                }
            }

            return countryRepository.save(updatedCountry);
        }
        throw new RuntimeException("Country not found with id: " + id);
    }

    @Transactional
    public void deleteCountry(Long id) {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Country not found with id: " + id);
        }
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAllWithPlacesAndImages();
    }

    // Lightweight method for admin list view - doesn't load relationships
    public List<Country> getAllCountriesForList() {
        return countryRepository.findAllSimple();
    }

    public Country getCountryById(Long id) {
        return countryRepository.findByIdWithPlacesAndImages(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
    }
}
