package com.example.demo.controller;

import com.example.demo.model.Bus;
import com.example.demo.model.DestinationGuidance;
import com.example.demo.model.Flight;
import com.example.demo.model.Hotel;
import com.example.demo.model.Train;
import com.example.demo.model.TravelPackage;
import com.example.demo.repository.BusRepository;
import com.example.demo.repository.DestinationGuidanceRepository;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.PackageReviewRepository;
import com.example.demo.repository.TrainRepository;
import com.example.demo.service.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PackageController {

    @Autowired
    private TravelPackageService packageService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private DestinationGuidanceRepository destinationGuidanceRepository;

    @Autowired
    private PackageReviewRepository reviewRepository;

    @GetMapping("/packages")
    public String listPackages(Model model) {
        // Count packages for the landing page
        long nationalCount = packageService.getPackagesByType("DOMESTIC").size();
        long internationalCount = packageService.getPackagesByType("INTERNATIONAL").size();

        // Count other services
        long flightCount = flightRepository.count();
        long hotelCount = hotelRepository.count();
        long trainCount = trainRepository.count();
        long busCount = busRepository.count();

        model.addAttribute("nationalCount", nationalCount);
        model.addAttribute("internationalCount", internationalCount);
        model.addAttribute("flightCount", flightCount);
        model.addAttribute("hotelCount", hotelCount);
        model.addAttribute("trainCount", trainCount);
        model.addAttribute("busCount", busCount);

        return "packages"; // Return the landing page
    }

    @GetMapping("/packages/{type}")
    public String listPackagesByType(
            @PathVariable("type") String type,
            @RequestParam(name = "travelType", required = false) String travelType,
            @RequestParam(name = "season", required = false) String season,
            Model model) {

        List<TravelPackage> packages;
        String packageTypeLabel;
        String dbType;

        if ("international".equalsIgnoreCase(type)) {
            dbType = "INTERNATIONAL";
            packageTypeLabel = "International";
        } else if ("national".equalsIgnoreCase(type) || "domestic".equalsIgnoreCase(type)) {
            dbType = "DOMESTIC";
            packageTypeLabel = "Domestic";
        } else {
            // Default or invalid type, redirect to landing page
            return "redirect:/packages";
        }

        packages = packageService.getPackagesByType(dbType);

        // Filter by travel type if specified
        if (travelType != null && !travelType.isEmpty()) {
            packages = packageService.getPackagesByTravelType(travelType, packages);
        }

        // Filter by season if specified
        if (season != null && !season.isEmpty()) {
            packages = packageService.getPackagesBySeason(season, packages);
        }

        // Filter out any null packages to prevent template errors
        if (packages != null) {
            packages = packages.stream().filter(java.util.Objects::nonNull)
                    .collect(java.util.stream.Collectors.toList());
        }

        model.addAttribute("packages", packages);
        model.addAttribute("packageType", packageTypeLabel);
        model.addAttribute("selectedTravelType", travelType);
        model.addAttribute("selectedSeason", season);
        model.addAttribute("type", type); // Pass type to template for filter links

        return "packages-list";
    }

    @GetMapping("/package/{id}")
    public String showPackageDetails(@PathVariable String id, Model model) {
        try {
            Long packageId = Long.parseLong(id);
            Optional<TravelPackage> pkgOpt = packageService.getPackageById(packageId);
            if (pkgOpt.isPresent()) {
                TravelPackage pkg = pkgOpt.get();

                // Ensure standardPrice and luxuryPrice are set if null
                if (pkg.getStandardPrice() == null && pkg.getPrice() != null) {
                    pkg.setStandardPrice(pkg.getPrice());
                }
                if (pkg.getLuxuryPrice() == null && pkg.getPrice() != null) {
                    pkg.setLuxuryPrice(pkg.getPrice() * 1.5);
                }

                model.addAttribute("package", pkg);

                // Fetch destination guidance if available
                Optional<DestinationGuidance> guidanceOpt = destinationGuidanceRepository
                        .findByTravelPackageId(packageId);
                guidanceOpt.ifPresent(guidance -> model.addAttribute("guidance", guidance));

                // Fetch approved reviews for this package
                try {
                    List<com.example.demo.model.PackageReview> reviews = reviewRepository
                            .findApprovedReviewsByPackageId(packageId);
                    model.addAttribute("reviews", reviews);

                    // Calculate average rating
                    if (!reviews.isEmpty()) {
                        double avgRating = reviews.stream()
                                .filter(r -> r.getRating() != null)
                                .mapToInt(com.example.demo.model.PackageReview::getRating)
                                .average()
                                .orElse(0.0);
                        model.addAttribute("averageRating", avgRating);
                        model.addAttribute("totalReviews", reviews.size());
                    } else {
                        model.addAttribute("averageRating", 0.0);
                        model.addAttribute("totalReviews", 0);
                    }
                } catch (Exception e) {
                    model.addAttribute("reviews", new java.util.ArrayList<>());
                    model.addAttribute("averageRating", 0.0);
                    model.addAttribute("totalReviews", 0);
                }

                // Fetch 4★+ hotels for Luxury packages (only if location is not null)
                if (pkg.getLocation() != null && !pkg.getLocation().trim().isEmpty()) {
                    try {
                        List<Hotel> luxuryHotels = hotelRepository
                                .findByLocationAndStarRatingGreaterThanEqual(pkg.getLocation(), 4);
                        model.addAttribute("luxuryHotels", luxuryHotels);
                    } catch (Exception e) {
                        model.addAttribute("luxuryHotels", new java.util.ArrayList<>());
                    }

                    // Fetch all hotels for Standard package suggestions
                    try {
                        List<Hotel> allHotels = hotelRepository.findByLocation(pkg.getLocation());
                        model.addAttribute("suggestedHotels", allHotels);
                    } catch (Exception e) {
                        model.addAttribute("suggestedHotels", new java.util.ArrayList<>());
                    }

                    // Fetch transport options (flights, buses, trains) - For DOMESTIC packages,
                    // show all transports
                    try {
                        String packageLocation = pkg.getLocation() != null ? pkg.getLocation().trim() : "";
                        List<Flight> flights = new java.util.ArrayList<>();
                        List<Bus> buses = new java.util.ArrayList<>();
                        List<Train> trains = new java.util.ArrayList<>();

                        if (!packageLocation.isEmpty()) {
                            // Normalize package location for matching (uppercase, trimmed)
                            String locationNormalized = packageLocation.toUpperCase().trim();

                            // Check if this is a DOMESTIC package - show all transport options
                            boolean isDomestic = "DOMESTIC".equalsIgnoreCase(pkg.getPackageType());

                            // Fetch flights - match by package destination
                            List<Flight> allFlights = flightRepository.findAll();
                            flights = allFlights.stream()
                                    .filter(f -> {
                                        if (f.getDestination() == null || f.getDestination().trim().isEmpty()) {
                                            return false;
                                        }
                                        String flightDest = f.getDestination().trim().toUpperCase();
                                        return flightDest.equals(locationNormalized) ||
                                                flightDest.contains(locationNormalized) ||
                                                locationNormalized.contains(flightDest);
                                    })
                                    .collect(java.util.stream.Collectors.toList());

                            // For DOMESTIC packages, also fetch buses and trains
                            if (isDomestic) {
                                // Fetch buses - match by destination
                                List<Bus> allBuses = busRepository.findAll();
                                buses = allBuses.stream()
                                        .filter(b -> {
                                            if (b.getDestination() == null || b.getDestination().trim().isEmpty()) {
                                                return false;
                                            }
                                            String busDest = b.getDestination().trim().toUpperCase();
                                            return busDest.equals(locationNormalized) ||
                                                    busDest.contains(locationNormalized) ||
                                                    locationNormalized.contains(busDest);
                                        })
                                        .collect(java.util.stream.Collectors.toList());

                                // Fetch trains - match by destination
                                List<Train> allTrains = trainRepository.findAll();
                                trains = allTrains.stream()
                                        .filter(t -> {
                                            if (t.getDestination() == null || t.getDestination().trim().isEmpty()) {
                                                return false;
                                            }
                                            String trainDest = t.getDestination().trim().toUpperCase();
                                            return trainDest.equals(locationNormalized) ||
                                                    trainDest.contains(locationNormalized) ||
                                                    locationNormalized.contains(trainDest);
                                        })
                                        .collect(java.util.stream.Collectors.toList());
                            }

                            // Log for debugging
                            System.out.println("Package Location: " + packageLocation);
                            System.out.println("Package Type: " + pkg.getPackageType());
                            System.out.println("Found " + flights.size() + " matching flights");
                            if (isDomestic) {
                                System.out.println("Found " + buses.size() + " matching buses");
                                System.out.println("Found " + trains.size() + " matching trains");
                            }
                        }

                        model.addAttribute("flights", flights);
                        model.addAttribute("buses", buses);
                        model.addAttribute("trains", trains);
                        model.addAttribute("flightsMatchedByDestination", !flights.isEmpty());
                        model.addAttribute("isDomesticPackage", "DOMESTIC".equalsIgnoreCase(pkg.getPackageType()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        model.addAttribute("flights", new java.util.ArrayList<>());
                        model.addAttribute("buses", new java.util.ArrayList<>());
                        model.addAttribute("trains", new java.util.ArrayList<>());
                        model.addAttribute("flightsMatchedByDestination", false);
                        model.addAttribute("isDomesticPackage", false);
                    }
                } else {
                    model.addAttribute("luxuryHotels", new java.util.ArrayList<>());
                    model.addAttribute("suggestedHotels", new java.util.ArrayList<>());
                    model.addAttribute("flights", new java.util.ArrayList<>());
                    model.addAttribute("buses", new java.util.ArrayList<>());
                    model.addAttribute("trains", new java.util.ArrayList<>());
                    model.addAttribute("isDomesticPackage", false);
                }

                return "package-details";
            }
        } catch (NumberFormatException e) {
            // Invalid ID format
        } catch (Exception e) {
            e.printStackTrace();
            // Log the error for debugging
        }
        return "redirect:/packages";
    }
}
