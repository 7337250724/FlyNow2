package com.example.demo.service;

import com.example.demo.dto.PackageSummaryDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelPackageService {

    @Autowired
    private TravelPackageRepository packageRepository;

    @Autowired
    private PackageReviewRepository reviewRepository;

    @Autowired
    private DestinationGuidanceRepository destinationGuidanceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public TravelPackage createPackage(TravelPackage travelPackage, List<Map<String, Object>> itineraryDaysData,
                                       List<Map<String, Object>> featuresData, List<String> galleryImageUrls) {
        // Save the package first
        TravelPackage savedPackage = packageRepository.save(travelPackage);

        // Add itinerary days
        if (itineraryDaysData != null) {
            List<PackageItineraryDay> itineraryDays = new ArrayList<>();
            for (Map<String, Object> dayData : itineraryDaysData) {
                PackageItineraryDay day = new PackageItineraryDay();
                day.setTravelPackage(savedPackage);
                day.setDayNumber((Integer) dayData.get("dayNumber"));
                day.setTitle((String) dayData.get("title"));
                day.setDescription((String) dayData.get("description"));
                day.setImageUrl((String) dayData.get("imageUrl"));
                itineraryDays.add(day);
            }
            savedPackage.setItineraryDays(itineraryDays);
        }

        // Add features
        if (featuresData != null) {
            List<PackageFeature> features = new ArrayList<>();
            for (Map<String, Object> featureData : featuresData) {
                PackageFeature feature = new PackageFeature();
                feature.setTravelPackage(savedPackage);
                feature.setIcon((String) featureData.get("icon"));
                feature.setText((String) featureData.get("text"));
                features.add(feature);
            }
            savedPackage.setFeatures(features);
        }

        // Add gallery images
        if (galleryImageUrls != null) {
            List<PackageImage> galleryImages = new ArrayList<>();
            for (int i = 0; i < galleryImageUrls.size(); i++) {
                PackageImage image = new PackageImage();
                image.setTravelPackage(savedPackage);
                image.setImageUrl(galleryImageUrls.get(i));
                image.setDisplayOrder(i);
                galleryImages.add(image);
            }
            savedPackage.setGalleryImages(galleryImages);
        }

        return packageRepository.save(savedPackage);
    }

    @Transactional
    public TravelPackage updatePackage(Long id, TravelPackage travelPackage, List<Map<String, Object>> itineraryDaysData,
                                       List<Map<String, Object>> featuresData, List<String> galleryImageUrls) {
        Optional<TravelPackage> existingOpt = packageRepository.findByIdWithItinerary(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Package not found");
        }

        TravelPackage existing = existingOpt.get();
        
        // Update basic fields
        existing.setName(travelPackage.getName());
        existing.setLocation(travelPackage.getLocation());
        existing.setPackageType(travelPackage.getPackageType());
        existing.setHeroImageUrl(travelPackage.getHeroImageUrl());
        existing.setDurationDays(travelPackage.getDurationDays());
        existing.setPrice(travelPackage.getPrice());
        existing.setStandardPrice(travelPackage.getStandardPrice());
        existing.setLuxuryPrice(travelPackage.getLuxuryPrice());
        existing.setPriceCurrency(travelPackage.getPriceCurrency());
        existing.setShortDescription(travelPackage.getShortDescription());
        existing.setAboutDescription(travelPackage.getAboutDescription());
        existing.setVisaRequirement(travelPackage.getVisaRequirement());
        
        // Update travel type fields
        existing.setTravelTypes(travelPackage.getTravelTypes());
        existing.setIsFamilyFriendly(travelPackage.getIsFamilyFriendly());
        existing.setIsCoupleFriendly(travelPackage.getIsCoupleFriendly());
        existing.setIsAdventure(travelPackage.getIsAdventure());
        existing.setIsSpiritual(travelPackage.getIsSpiritual());
        existing.setIsCorporate(travelPackage.getIsCorporate());

        // Update itinerary days - modify existing collection in place to avoid orphan removal issues
        if (existing.getItineraryDays() == null) {
            existing.setItineraryDays(new ArrayList<>());
        } else {
            existing.getItineraryDays().clear();
        }
        if (itineraryDaysData != null) {
            for (Map<String, Object> dayData : itineraryDaysData) {
                PackageItineraryDay day = new PackageItineraryDay();
                day.setTravelPackage(existing);
                day.setDayNumber((Integer) dayData.get("dayNumber"));
                day.setTitle((String) dayData.get("title"));
                day.setDescription((String) dayData.get("description"));
                day.setImageUrl((String) dayData.get("imageUrl"));
                existing.getItineraryDays().add(day);
            }
        }

        // Update features - modify existing collection in place to avoid orphan removal issues
        if (existing.getFeatures() == null) {
            existing.setFeatures(new ArrayList<>());
        } else {
            existing.getFeatures().clear();
        }
        if (featuresData != null) {
            for (Map<String, Object> featureData : featuresData) {
                PackageFeature feature = new PackageFeature();
                feature.setTravelPackage(existing);
                feature.setIcon((String) featureData.get("icon"));
                feature.setText((String) featureData.get("text"));
                existing.getFeatures().add(feature);
            }
        }

        // Update gallery images - modify existing collection in place to avoid orphan removal issues
        if (existing.getGalleryImages() == null) {
            existing.setGalleryImages(new ArrayList<>());
        } else {
            existing.getGalleryImages().clear();
        }
        if (galleryImageUrls != null) {
            for (int i = 0; i < galleryImageUrls.size(); i++) {
                PackageImage image = new PackageImage();
                image.setTravelPackage(existing);
                image.setImageUrl(galleryImageUrls.get(i));
                image.setDisplayOrder(i);
                existing.getGalleryImages().add(image);
            }
        }

        return packageRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public List<TravelPackage> getAllPackages() {
        List<TravelPackage> packages = packageRepository.findAllWithItinerary();
        // Eagerly load features and gallery images to avoid lazy loading issues
        packages.forEach(pkg -> {
            if (pkg.getFeatures() != null) {
                pkg.getFeatures().size(); // Trigger lazy loading
            }
            if (pkg.getGalleryImages() != null) {
                pkg.getGalleryImages().size(); // Trigger lazy loading
            }
        });
        return packages;
    }

    // Lightweight method for admin list view - returns DTOs without relationships
    @Transactional(readOnly = true)
    public List<PackageSummaryDTO> getAllPackagesForList() {
        List<TravelPackage> packages = packageRepository.findAllSimple();
        return packages.stream()
                .map(pkg -> new PackageSummaryDTO(
                        pkg.getId(),
                        pkg.getName(),
                        pkg.getLocation(),
                        pkg.getPackageType(),
                        pkg.getDurationDays(),
                        pkg.getPrice(),
                        pkg.getPriceCurrency()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TravelPackage> getPackagesByType(String packageType) {
        List<TravelPackage> packages = packageRepository.findByPackageTypeWithItinerary(packageType);
        // Eagerly load features and gallery images to avoid lazy loading issues
        packages.forEach(pkg -> {
            if (pkg.getFeatures() != null) {
                pkg.getFeatures().size(); // Trigger lazy loading
            }
            if (pkg.getGalleryImages() != null) {
                pkg.getGalleryImages().size(); // Trigger lazy loading
            }
        });
        return packages;
    }

    @Transactional(readOnly = true)
    public Optional<TravelPackage> getPackageById(Long id) {
        Optional<TravelPackage> pkgOpt = packageRepository.findByIdWithItinerary(id);
        if (pkgOpt.isPresent()) {
            TravelPackage pkg = pkgOpt.get();
            // Eagerly load features and gallery images to avoid lazy loading issues
            // Access the collections to trigger lazy loading within the transaction
            if (pkg.getFeatures() != null) {
                pkg.getFeatures().size(); // Trigger lazy loading
            }
            if (pkg.getGalleryImages() != null) {
                pkg.getGalleryImages().size(); // Trigger lazy loading
            }
            // Ensure itineraryDays are also loaded (they should be from the query)
            if (pkg.getItineraryDays() != null) {
                pkg.getItineraryDays().size(); // Ensure loaded
            }
        }
        return pkgOpt;
    }

    @Transactional(readOnly = true)
    public Optional<TravelPackage> getPackageBySlug(String slug) {
        // For now, we'll use ID. Can add slug field later if needed
        try {
            Long id = Long.parseLong(slug);
            return getPackageById(id);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public List<TravelPackage> getPackagesByTravelType(String travelType, List<TravelPackage> packages) {
        if (travelType == null || travelType.isEmpty()) {
            return packages;
        }
        
        String upperTravelType = travelType.toUpperCase();
        return packages.stream()
                .filter(pkg -> {
                    if (pkg.getTravelTypes() != null && pkg.getTravelTypes().toUpperCase().contains(upperTravelType)) {
                        return true;
                    }
                    // Check boolean flags
                    switch (upperTravelType) {
                        case "SOLO":
                            // Check if travelTypes contains SOLO or if it's suitable for solo travelers
                            // (packages without family/couple flags are often suitable for solo)
                            return pkg.getTravelTypes() != null && pkg.getTravelTypes().toUpperCase().contains("SOLO");
                        case "FEMALE":
                        case "FEMALES":
                            // Check if travelTypes contains FEMALE or FEMALES
                            return pkg.getTravelTypes() != null && 
                                   (pkg.getTravelTypes().toUpperCase().contains("FEMALE") || 
                                    pkg.getTravelTypes().toUpperCase().contains("FEMALES"));
                        case "FAMILY":
                            return Boolean.TRUE.equals(pkg.getIsFamilyFriendly());
                        case "COUPLE":
                        case "HONEYMOON":
                            return Boolean.TRUE.equals(pkg.getIsCoupleFriendly());
                        case "ADVENTURE":
                            return Boolean.TRUE.equals(pkg.getIsAdventure());
                        case "SPIRITUAL":
                            return Boolean.TRUE.equals(pkg.getIsSpiritual());
                        case "CORPORATE":
                            return Boolean.TRUE.equals(pkg.getIsCorporate());
                        case "BUDGET":
                            // Check if travelTypes contains BUDGET OR if price is below threshold based on package type
                            if (pkg.getTravelTypes() != null && pkg.getTravelTypes().toUpperCase().contains("BUDGET")) {
                                return true;
                            }
                            // Check price threshold based on package type and currency
                            if (pkg.getStandardPrice() != null) {
                                String packageType = pkg.getPackageType();
                                String currency = pkg.getPriceCurrency();
                                
                                // For DOMESTIC packages with ₹ currency: threshold is ₹5000
                                if ("DOMESTIC".equalsIgnoreCase(packageType) && 
                                    (currency == null || "₹".equals(currency) || "INR".equalsIgnoreCase(currency))) {
                                    return pkg.getStandardPrice() < 5000;
                                }
                                // For INTERNATIONAL packages with $ currency: threshold is $4000
                                else if ("INTERNATIONAL".equalsIgnoreCase(packageType) && 
                                         ("$".equals(currency) || "USD".equalsIgnoreCase(currency))) {
                                    return pkg.getStandardPrice() < 4000;
                                }
                                // Fallback: if currency doesn't match, use default threshold based on package type
                                else if ("DOMESTIC".equalsIgnoreCase(packageType)) {
                                    return pkg.getStandardPrice() < 5000;
                                } else if ("INTERNATIONAL".equalsIgnoreCase(packageType)) {
                                    return pkg.getStandardPrice() < 4000;
                                }
                            }
                            return false;
                        case "LUXURY":
                            // Check if travelTypes contains LUXURY OR if price is above threshold
                            return (pkg.getTravelTypes() != null && pkg.getTravelTypes().toUpperCase().contains("LUXURY")) ||
                                   (pkg.getLuxuryPrice() != null && pkg.getLuxuryPrice() >= 50000);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TravelPackage> getPackagesBySeason(String season, List<TravelPackage> packages) {
        if (season == null || season.isEmpty()) {
            return packages;
        }
        
        String upperSeason = season.toUpperCase();
        return packages.stream()
                .filter(pkg -> {
                    if (pkg.getSeasons() != null && pkg.getSeasons().toUpperCase().contains(upperSeason)) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePackage(Long id) {
        // First, delete all related records that have foreign key constraints
        
        // 1. Delete all reviews for this package
        List<PackageReview> reviews = reviewRepository.findAllByTravelPackageId(id);
        if (!reviews.isEmpty()) {
            reviewRepository.deleteAll(reviews);
        }
        
        // 2. Delete destination guidance for this package
        Optional<DestinationGuidance> guidanceOpt = destinationGuidanceRepository.findByTravelPackageId(id);
        guidanceOpt.ifPresent(destinationGuidanceRepository::delete);
        
        // 3. Delete bookings for this package
        // Note: The database has a NOT NULL constraint on package_id, so we must delete bookings
        // instead of setting package_id to null
        List<Booking> bookings = bookingRepository.findByTravelPackageId(id);
        if (!bookings.isEmpty()) {
            bookingRepository.deleteAll(bookings);
        }
        
        // 4. Finally, delete the package itself
        // The cascade relationships (itineraryDays, features, galleryImages) will be deleted automatically
        packageRepository.deleteById(id);
    }
}

