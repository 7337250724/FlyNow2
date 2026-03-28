package com.example.demo.service;

import com.example.demo.model.Package;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageService {

    private List<Package> packages;

    public PackageService() {
        initializePackages();
    }

    private void initializePackages() {
        packages = new ArrayList<>();

        // National Packages (10)
        packages.add(createNationalPackage(1L, "Goa 4N/5D Beach Escape", 
            "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800",
            5, 4, "Experience the sun, sand, and vibrant nightlife of Goa", 
            "Beach Paradise", 25000.0,
            Arrays.asList("Beach hopping", "Water sports", "Nightlife", "Casino visits"),
            createGoaItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800",
                "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=800",
                "https://images.unsplash.com/photo-1507525421304-6f87d75787b1?w=800"
            ),
            "Discover the perfect blend of relaxation and adventure in Goa, India's party capital.",
            Arrays.asList("Hotel stay", "Breakfast", "Airport pickup", "Sightseeing", "Water sports"),
            Arrays.asList("Lunch & Dinner", "Personal expenses", "Flight tickets")
        ));

        packages.add(createNationalPackage(2L, "Manali Snow Adventure 5N/6D",
            "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800",
            6, 5, "Adventure in the snow-capped mountains of Manali",
            "Mountain Magic", 35000.0,
            Arrays.asList("Skiing", "Snow trekking", "Hot springs", "Temple visits"),
            createManaliItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Experience the breathtaking beauty of the Himalayas in Manali.",
            Arrays.asList("Hotel stay", "All meals", "Skiing equipment", "Guide", "Transport"),
            Arrays.asList("Flight tickets", "Personal expenses", "Additional activities")
        ));

        packages.add(createNationalPackage(3L, "Kerala Backwaters 4N/5D",
            "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=800",
            5, 4, "Cruise through the serene backwaters of Kerala",
            "Tranquil Waters", 28000.0,
            Arrays.asList("Houseboat cruise", "Ayurveda spa", "Tea plantation tour", "Wildlife safari"),
            createKeralaItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Relax and rejuvenate in God's Own Country, Kerala.",
            Arrays.asList("Houseboat stay", "All meals", "Ayurveda session", "Sightseeing"),
            Arrays.asList("Flight tickets", "Personal expenses", "Additional spa treatments")
        ));

        packages.add(createNationalPackage(4L, "Rajasthan Royal Heritage 6N/7D",
            "https://images.unsplash.com/photo-1539650116574-75c0c6d73a6e?w=800",
            7, 6, "Explore the royal palaces and forts of Rajasthan",
            "Royal Experience", 40000.0,
            Arrays.asList("Palace tours", "Camel safari", "Folk dance", "Desert camping"),
            createRajasthanItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1539650116574-75c0c6d73a6e?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Step into the world of Maharajas and experience royal hospitality.",
            Arrays.asList("Heritage hotel stay", "All meals", "Entrance fees", "Guide", "Transport"),
            Arrays.asList("Flight tickets", "Personal expenses", "Shopping")
        ));

        packages.add(createNationalPackage(5L, "Darjeeling Tea Gardens 3N/4D",
            "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
            4, 3, "Visit the famous tea gardens and enjoy mountain views",
            "Tea & Mountains", 22000.0,
            Arrays.asList("Tea garden tour", "Toy train ride", "Sunrise at Tiger Hill", "Monastery visits"),
            createDarjeelingItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Experience the charm of hill stations and tea culture.",
            Arrays.asList("Hotel stay", "Breakfast", "Toy train ticket", "Sightseeing"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Personal expenses")
        ));

        packages.add(createNationalPackage(6L, "Varanasi Spiritual Journey 3N/4D",
            "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=800",
            4, 3, "Experience the spiritual essence of Varanasi",
            "Spiritual Bliss", 18000.0,
            Arrays.asList("Ganga Aarti", "Temple visits", "Boat ride", "Sarnath tour"),
            createVaranasiItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Discover the ancient city of Varanasi and its spiritual heritage.",
            Arrays.asList("Hotel stay", "Breakfast", "Boat ride", "Guide", "Transport"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Personal expenses")
        ));

        packages.add(createNationalPackage(7L, "Andaman Islands 5N/6D",
            "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=800",
            6, 5, "Explore pristine beaches and coral reefs",
            "Island Paradise", 45000.0,
            Arrays.asList("Scuba diving", "Snorkeling", "Beach hopping", "Light & Sound show"),
            createAndamanItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=800",
                "https://images.unsplash.com/photo-1507525421304-6f87d75787b1?w=800",
                "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800"
            ),
            "Discover the untouched beauty of Andaman Islands.",
            Arrays.asList("Hotel stay", "All meals", "Water activities", "Ferry tickets", "Sightseeing"),
            Arrays.asList("Flight tickets", "Personal expenses", "Additional water sports")
        ));

        packages.add(createNationalPackage(8L, "Ladakh Adventure 6N/7D",
            "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800",
            7, 6, "Journey through the high-altitude desert of Ladakh",
            "High Altitude", 50000.0,
            Arrays.asList("Pangong Lake", "Nubra Valley", "Monastery visits", "Mountain biking"),
            createLadakhItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800"
            ),
            "Experience the raw beauty and adventure of Ladakh.",
            Arrays.asList("Hotel stay", "All meals", "Inner line permits", "Transport", "Guide"),
            Arrays.asList("Flight tickets", "Personal expenses", "Additional activities")
        ));

        packages.add(createNationalPackage(9L, "Mysore & Coorg 4N/5D",
            "https://images.unsplash.com/photo-1507525421304-6f87d75787b1?w=800",
            5, 4, "Explore royal palaces and coffee plantations",
            "Royal & Coffee", 30000.0,
            Arrays.asList("Palace tour", "Coffee plantation", "Waterfalls", "Wildlife safari"),
            createMysoreItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1507525421304-6f87d75787b1?w=800",
                "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800"
            ),
            "Discover the royal heritage and natural beauty of Karnataka.",
            Arrays.asList("Hotel stay", "Breakfast", "Entrance fees", "Sightseeing", "Transport"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Personal expenses")
        ));

        packages.add(createNationalPackage(10L, "Shimla Hill Station 3N/4D",
            "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800",
            4, 3, "Enjoy the cool climate and scenic beauty of Shimla",
            "Hill Station", 20000.0,
            Arrays.asList("Toy train", "Mall Road", "Jakhoo Temple", "Ice skating"),
            createShimlaItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Escape to the charming hill station of Shimla.",
            Arrays.asList("Hotel stay", "Breakfast", "Toy train ticket", "Sightseeing"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Personal expenses")
        ));

        // International Packages (10)
        packages.add(createInternationalPackage(11L, "Singapore Family Fun 4N/5D",
            "https://images.unsplash.com/photo-1525625293386-3f8f99389edd?w=800",
            5, 4, "Experience the modern marvels of Singapore",
            "Modern Marvel", 85000.0,
            Arrays.asList("Universal Studios", "Marina Bay", "Sentosa Island", "Gardens by the Bay"),
            createSingaporeItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1525625293386-3f8f99389edd?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Discover the perfect blend of culture, entertainment, and modernity in Singapore.",
            Arrays.asList("Hotel stay", "Breakfast", "Universal Studios ticket", "Airport transfers", "Sightseeing"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Visa fees", "Personal expenses")
        ));

        packages.add(createInternationalPackage(12L, "Dubai Desert Safari 5N/6D",
            "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?w=800",
            6, 5, "Experience luxury and adventure in Dubai",
            "Desert Luxury", 95000.0,
            Arrays.asList("Desert safari", "Burj Khalifa", "Dubai Mall", "Palm Jumeirah"),
            createDubaiItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Experience the opulence and adventure of Dubai.",
            Arrays.asList("Hotel stay", "Breakfast", "Desert safari", "Burj Khalifa ticket", "Transport"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Visa fees", "Personal expenses")
        ));

        packages.add(createInternationalPackage(13L, "Bali Tropical Paradise 5N/6D",
            "https://images.unsplash.com/photo-1537996194471-e657df975ab4?w=800",
            6, 5, "Relax in the tropical paradise of Bali",
            "Tropical Escape", 65000.0,
            Arrays.asList("Beach hopping", "Temple visits", "Water sports", "Spa treatments"),
            createBaliItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1537996194471-e657df975ab4?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Unwind in the beautiful beaches and rich culture of Bali.",
            Arrays.asList("Resort stay", "Breakfast", "Water sports", "Sightseeing", "Transport"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Visa fees", "Personal expenses")
        ));

        packages.add(createInternationalPackage(14L, "Thailand Cultural Tour 6N/7D",
            "https://images.unsplash.com/photo-1552465011-b4e21bf6e79a?w=800",
            7, 6, "Explore the culture and beaches of Thailand",
            "Culture & Beaches", 70000.0,
            Arrays.asList("Temple tours", "Beach activities", "Elephant sanctuary", "Floating market"),
            createThailandItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1552465011-b4e21bf6e79a?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Discover the vibrant culture and stunning beaches of Thailand.",
            Arrays.asList("Hotel stay", "Breakfast", "Entrance fees", "Sightseeing", "Transport"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Visa fees", "Personal expenses")
        ));

        packages.add(createInternationalPackage(15L, "Switzerland Alpine Adventure 6N/7D",
            "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
            7, 6, "Experience the majestic Swiss Alps",
            "Alpine Wonder", 180000.0,
            Arrays.asList("Mountain railways", "Skiing", "Lake cruises", "Alpine villages"),
            createSwitzerlandItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Experience the breathtaking beauty of the Swiss Alps.",
            Arrays.asList("Hotel stay", "Breakfast", "Railway passes", "Sightseeing", "Transport"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Visa fees", "Personal expenses")
        ));

        packages.add(createInternationalPackage(16L, "Japan Cherry Blossom 5N/6D",
            "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=800",
            6, 5, "Witness the beauty of cherry blossoms in Japan",
            "Cherry Blossoms", 120000.0,
            Arrays.asList("Temple visits", "Cherry blossom viewing", "Bullet train", "Traditional experiences"),
            createJapanItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Experience the unique culture and natural beauty of Japan.",
            Arrays.asList("Hotel stay", "Breakfast", "Bullet train ticket", "Sightseeing", "Transport"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Visa fees", "Personal expenses")
        ));

        packages.add(createInternationalPackage(17L, "Maldives Island Resort 4N/5D",
            "https://images.unsplash.com/photo-1507525421304-6f87d75787b1?w=800",
            5, 4, "Luxury stay in overwater villas",
            "Luxury Island", 150000.0,
            Arrays.asList("Snorkeling", "Spa treatments", "Sunset cruise", "Water sports"),
            createMaldivesItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1507525421304-6f87d75787b1?w=800",
                "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=800",
                "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800"
            ),
            "Indulge in luxury at a pristine island resort in Maldives.",
            Arrays.asList("Overwater villa", "All meals", "Water activities", "Spa session", "Transfers"),
            Arrays.asList("Flight tickets", "Visa fees", "Personal expenses", "Additional activities")
        ));

        packages.add(createInternationalPackage(18L, "Paris & London 7N/8D",
            "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?w=800",
            8, 7, "Explore the iconic cities of Europe",
            "European Classics", 200000.0,
            Arrays.asList("Eiffel Tower", "Louvre Museum", "Big Ben", "Thames cruise"),
            createParisLondonItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Experience the romance of Paris and the charm of London.",
            Arrays.asList("Hotel stay", "Breakfast", "Entrance fees", "Sightseeing", "Transport"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Visa fees", "Personal expenses")
        ));

        packages.add(createInternationalPackage(19L, "Australia Great Barrier Reef 6N/7D",
            "https://images.unsplash.com/photo-1507525421304-6f87d75787b1?w=800",
            7, 6, "Dive into the wonders of the Great Barrier Reef",
            "Reef Adventure", 220000.0,
            Arrays.asList("Scuba diving", "Reef tours", "Wildlife encounters", "Beach activities"),
            createAustraliaItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1507525421304-6f87d75787b1?w=800",
                "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=800",
                "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800"
            ),
            "Explore the natural wonders of Australia's Great Barrier Reef.",
            Arrays.asList("Hotel stay", "Breakfast", "Reef tours", "Diving equipment", "Transport"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Visa fees", "Personal expenses")
        ));

        packages.add(createInternationalPackage(20L, "New York City Tour 5N/6D",
            "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?w=800",
            6, 5, "Experience the energy of the Big Apple",
            "City That Never Sleeps", 180000.0,
            Arrays.asList("Statue of Liberty", "Broadway show", "Central Park", "Museum visits"),
            createNewYorkItinerary(),
            Arrays.asList(
                "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?w=800",
                "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
            ),
            "Discover the iconic landmarks and vibrant culture of New York City.",
            Arrays.asList("Hotel stay", "Breakfast", "Broadway ticket", "Sightseeing", "Transport"),
            Arrays.asList("Lunch & Dinner", "Flight tickets", "Visa fees", "Personal expenses")
        ));
    }

    public List<Package> getAllPackages() {
        return packages;
    }

    public List<Package> getNationalPackages() {
        return packages.stream()
                .filter(p -> "NATIONAL".equals(p.getRegion()))
                .collect(Collectors.toList());
    }

    public List<Package> getInternationalPackages() {
        return packages.stream()
                .filter(p -> "INTERNATIONAL".equals(p.getRegion()))
                .collect(Collectors.toList());
    }

    public Package getPackageById(Long id) {
        return packages.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Helper methods to create packages
    private Package createNationalPackage(Long id, String title, String heroImage, int days, int nights,
            String desc, String highlight, double price, List<String> activities,
            List<Package.DayItinerary> itinerary, List<String> gallery, String about,
            List<String> included, List<String> notIncluded) {
        Package pkg = new Package(id, title, "NATIONAL", heroImage, days, nights, desc, highlight, price);
        pkg.setActivities(activities);
        pkg.setItinerary(itinerary);
        pkg.setGalleryImageUrls(gallery);
        pkg.setAboutDescription(about);
        pkg.setWhatsIncluded(included);
        pkg.setWhatsNotIncluded(notIncluded);
        return pkg;
    }

    private Package createInternationalPackage(Long id, String title, String heroImage, int days, int nights,
            String desc, String highlight, double price, List<String> activities,
            List<Package.DayItinerary> itinerary, List<String> gallery, String about,
            List<String> included, List<String> notIncluded) {
        Package pkg = new Package(id, title, "INTERNATIONAL", heroImage, days, nights, desc, highlight, price);
        pkg.setActivities(activities);
        pkg.setItinerary(itinerary);
        pkg.setGalleryImageUrls(gallery);
        pkg.setAboutDescription(about);
        pkg.setWhatsIncluded(included);
        pkg.setWhatsNotIncluded(notIncluded);
        return pkg;
    }

    // Itinerary creation methods
    private List<Package.DayItinerary> createGoaItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival and Beach Time", "Arrive in Goa, check-in, and relax at Calangute Beach"),
            new Package.DayItinerary("Day 2", "Water Sports Adventure", "Enjoy water sports at Baga Beach and visit Anjuna Beach"),
            new Package.DayItinerary("Day 3", "City Tour", "Visit Old Goa churches, Spice Plantation, and Dudhsagar Falls"),
            new Package.DayItinerary("Day 4", "Beach Hopping", "Explore North Goa beaches and enjoy nightlife"),
            new Package.DayItinerary("Day 5", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createManaliItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Manali", "Arrive and check-in, visit Hadimba Temple"),
            new Package.DayItinerary("Day 2", "Solang Valley", "Adventure activities at Solang Valley, skiing and snow activities"),
            new Package.DayItinerary("Day 3", "Rohtang Pass", "Visit Rohtang Pass for snow experience"),
            new Package.DayItinerary("Day 4", "Local Sightseeing", "Visit Vashisht Hot Springs, Old Manali, and Tibetan Monastery"),
            new Package.DayItinerary("Day 5", "Trekking", "Short trek to Jogini Falls"),
            new Package.DayItinerary("Day 6", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createKeralaItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Cochin", "Arrive and check-in, visit Fort Kochi"),
            new Package.DayItinerary("Day 2", "Munnar", "Drive to Munnar, visit tea plantations"),
            new Package.DayItinerary("Day 3", "Thekkady", "Visit Periyar Wildlife Sanctuary, spice plantation"),
            new Package.DayItinerary("Day 4", "Alleppey Houseboat", "Check-in to houseboat, cruise through backwaters"),
            new Package.DayItinerary("Day 5", "Departure", "Check-out and departure from Cochin")
        );
    }

    private List<Package.DayItinerary> createRajasthanItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Jaipur", "Arrive and check-in, visit City Palace"),
            new Package.DayItinerary("Day 2", "Jaipur Sightseeing", "Visit Amber Fort, Hawa Mahal, Jantar Mantar"),
            new Package.DayItinerary("Day 3", "Jodhpur", "Drive to Jodhpur, visit Mehrangarh Fort"),
            new Package.DayItinerary("Day 4", "Jaisalmer", "Drive to Jaisalmer, visit Golden Fort"),
            new Package.DayItinerary("Day 5", "Desert Safari", "Camel safari in Thar Desert, camping"),
            new Package.DayItinerary("Day 6", "Udaipur", "Drive to Udaipur, visit City Palace and Lake Pichola"),
            new Package.DayItinerary("Day 7", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createDarjeelingItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival", "Arrive in Darjeeling, check-in"),
            new Package.DayItinerary("Day 2", "Sunrise & Tea Gardens", "Early morning Tiger Hill sunrise, tea garden tour"),
            new Package.DayItinerary("Day 3", "Toy Train & Monasteries", "Joy ride on toy train, visit monasteries"),
            new Package.DayItinerary("Day 4", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createVaranasiItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival", "Arrive in Varanasi, check-in, evening Ganga Aarti"),
            new Package.DayItinerary("Day 2", "Temples & Ghats", "Morning boat ride, visit Kashi Vishwanath Temple"),
            new Package.DayItinerary("Day 3", "Sarnath", "Visit Sarnath, Buddhist temples and museum"),
            new Package.DayItinerary("Day 4", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createAndamanItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Port Blair", "Arrive and check-in, visit Cellular Jail"),
            new Package.DayItinerary("Day 2", "Havelock Island", "Ferry to Havelock, Radhanagar Beach"),
            new Package.DayItinerary("Day 3", "Water Activities", "Scuba diving and snorkeling"),
            new Package.DayItinerary("Day 4", "Neil Island", "Visit Neil Island, natural bridge"),
            new Package.DayItinerary("Day 5", "Port Blair", "Return to Port Blair, Ross Island"),
            new Package.DayItinerary("Day 6", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createLadakhItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Leh", "Arrive and acclimatize, rest"),
            new Package.DayItinerary("Day 2", "Local Sightseeing", "Visit Leh Palace, Shanti Stupa, Hall of Fame"),
            new Package.DayItinerary("Day 3", "Pangong Lake", "Drive to Pangong Lake, overnight stay"),
            new Package.DayItinerary("Day 4", "Nubra Valley", "Drive to Nubra Valley via Khardung La"),
            new Package.DayItinerary("Day 5", "Nubra to Leh", "Return to Leh, visit monasteries"),
            new Package.DayItinerary("Day 6", "Alchi & Likir", "Visit Alchi and Likir monasteries"),
            new Package.DayItinerary("Day 7", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createMysoreItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Mysore", "Arrive and check-in, visit Mysore Palace"),
            new Package.DayItinerary("Day 2", "Coorg", "Drive to Coorg, visit coffee plantations"),
            new Package.DayItinerary("Day 3", "Waterfalls & Wildlife", "Visit Abbey Falls, Dubare Elephant Camp"),
            new Package.DayItinerary("Day 4", "Mysore", "Return to Mysore, visit Chamundi Hill"),
            new Package.DayItinerary("Day 5", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createShimlaItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival", "Arrive in Shimla, check-in, visit Mall Road"),
            new Package.DayItinerary("Day 2", "Toy Train & Jakhoo", "Toy train ride, visit Jakhoo Temple"),
            new Package.DayItinerary("Day 3", "Kufri", "Visit Kufri, enjoy snow activities"),
            new Package.DayItinerary("Day 4", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createSingaporeItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival", "Arrive in Singapore, check-in, Marina Bay"),
            new Package.DayItinerary("Day 2", "Universal Studios", "Full day at Universal Studios"),
            new Package.DayItinerary("Day 3", "Sentosa Island", "Visit Sentosa, beaches and attractions"),
            new Package.DayItinerary("Day 4", "City Tour", "Gardens by the Bay, Orchard Road, Chinatown"),
            new Package.DayItinerary("Day 5", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createDubaiItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival", "Arrive in Dubai, check-in, Dubai Mall"),
            new Package.DayItinerary("Day 2", "Burj Khalifa & Desert", "Visit Burj Khalifa, evening desert safari"),
            new Package.DayItinerary("Day 3", "Palm Jumeirah", "Visit Palm Jumeirah, Aquaventure Waterpark"),
            new Package.DayItinerary("Day 4", "City Tour", "Dubai Museum, Gold Souk, Spice Souk"),
            new Package.DayItinerary("Day 5", "Dubai Marina", "Dubai Marina, JBR Beach"),
            new Package.DayItinerary("Day 6", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createBaliItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival", "Arrive in Bali, check-in, Kuta Beach"),
            new Package.DayItinerary("Day 2", "Ubud", "Visit Ubud, rice terraces, monkey forest"),
            new Package.DayItinerary("Day 3", "Temples", "Visit Tanah Lot, Uluwatu Temple"),
            new Package.DayItinerary("Day 4", "Water Sports", "Water sports at Tanjung Benoa"),
            new Package.DayItinerary("Day 5", "Beach Day", "Relax at Nusa Dua Beach"),
            new Package.DayItinerary("Day 6", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createThailandItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Bangkok", "Arrive and check-in, visit Wat Pho"),
            new Package.DayItinerary("Day 2", "Bangkok Tour", "Grand Palace, Wat Arun, floating market"),
            new Package.DayItinerary("Day 3", "Pattaya", "Drive to Pattaya, beach activities"),
            new Package.DayItinerary("Day 4", "Elephant Sanctuary", "Visit elephant sanctuary, Nong Nooch Garden"),
            new Package.DayItinerary("Day 5", "Pattaya", "Coral Island tour, water sports"),
            new Package.DayItinerary("Day 6", "Return to Bangkok", "Return to Bangkok, shopping"),
            new Package.DayItinerary("Day 7", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createSwitzerlandItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Zurich", "Arrive and check-in, city tour"),
            new Package.DayItinerary("Day 2", "Interlaken", "Travel to Interlaken, mountain views"),
            new Package.DayItinerary("Day 3", "Jungfraujoch", "Visit Top of Europe, Jungfraujoch"),
            new Package.DayItinerary("Day 4", "Lucerne", "Travel to Lucerne, lake cruise"),
            new Package.DayItinerary("Day 5", "Mount Titlis", "Visit Mount Titlis, glacier cave"),
            new Package.DayItinerary("Day 6", "Zurich", "Return to Zurich, shopping"),
            new Package.DayItinerary("Day 7", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createJapanItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Tokyo", "Arrive and check-in, Shibuya crossing"),
            new Package.DayItinerary("Day 2", "Tokyo Tour", "Visit Senso-ji Temple, Tokyo Skytree"),
            new Package.DayItinerary("Day 3", "Bullet Train to Kyoto", "Travel to Kyoto, Fushimi Inari Shrine"),
            new Package.DayItinerary("Day 4", "Kyoto", "Visit Kinkaku-ji, Arashiyama Bamboo Grove"),
            new Package.DayItinerary("Day 5", "Cherry Blossoms", "Cherry blossom viewing, traditional tea ceremony"),
            new Package.DayItinerary("Day 6", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createMaldivesItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival", "Arrive in Maldives, transfer to resort, check-in"),
            new Package.DayItinerary("Day 2", "Water Activities", "Snorkeling, water sports"),
            new Package.DayItinerary("Day 3", "Spa & Relaxation", "Spa treatments, beach relaxation"),
            new Package.DayItinerary("Day 4", "Sunset Cruise", "Sunset cruise, dolphin watching"),
            new Package.DayItinerary("Day 5", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createParisLondonItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Paris", "Arrive and check-in, Eiffel Tower"),
            new Package.DayItinerary("Day 2", "Paris Tour", "Louvre Museum, Notre-Dame, Champs-Élysées"),
            new Package.DayItinerary("Day 3", "Versailles", "Day trip to Palace of Versailles"),
            new Package.DayItinerary("Day 4", "Travel to London", "Eurostar to London, check-in"),
            new Package.DayItinerary("Day 5", "London Tour", "Big Ben, London Eye, Tower Bridge"),
            new Package.DayItinerary("Day 6", "London", "British Museum, Buckingham Palace"),
            new Package.DayItinerary("Day 7", "Thames Cruise", "Thames river cruise, shopping"),
            new Package.DayItinerary("Day 8", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createAustraliaItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival in Cairns", "Arrive and check-in"),
            new Package.DayItinerary("Day 2", "Great Barrier Reef", "Full day reef tour, snorkeling"),
            new Package.DayItinerary("Day 3", "Scuba Diving", "Scuba diving experience"),
            new Package.DayItinerary("Day 4", "Kuranda", "Kuranda Scenic Railway, Skyrail"),
            new Package.DayItinerary("Day 5", "Wildlife", "Wildlife encounters, Daintree Rainforest"),
            new Package.DayItinerary("Day 6", "Beach Day", "Relax at Palm Cove Beach"),
            new Package.DayItinerary("Day 7", "Departure", "Check-out and departure")
        );
    }

    private List<Package.DayItinerary> createNewYorkItinerary() {
        return Arrays.asList(
            new Package.DayItinerary("Day 1", "Arrival", "Arrive in New York, check-in, Times Square"),
            new Package.DayItinerary("Day 2", "Statue of Liberty", "Visit Statue of Liberty, Ellis Island"),
            new Package.DayItinerary("Day 3", "Museums", "Metropolitan Museum, Natural History Museum"),
            new Package.DayItinerary("Day 4", "Central Park & Broadway", "Central Park, evening Broadway show"),
            new Package.DayItinerary("Day 5", "Empire State & Shopping", "Empire State Building, Fifth Avenue shopping"),
            new Package.DayItinerary("Day 6", "Departure", "Check-out and departure")
        );
    }
}

