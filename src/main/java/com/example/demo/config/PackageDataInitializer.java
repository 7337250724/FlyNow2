package com.example.demo.config;

import com.example.demo.model.*;
import com.example.demo.repository.TravelPackageRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.BusRepository;
import com.example.demo.repository.TrainRepository;
import com.example.demo.repository.HotelImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class PackageDataInitializer implements CommandLineRunner {

        @Autowired
        private TravelPackageRepository packageRepository;

        @Autowired
        private HotelRepository hotelRepository;

        @Autowired
        private FlightRepository flightRepository;

        @Autowired
        private BusRepository busRepository;

        @Autowired
        private TrainRepository trainRepository;

        @Autowired
        private com.example.demo.repository.BookingRepository bookingRepository;

        @Autowired
        private com.example.demo.repository.SeatBookingRepository seatBookingRepository;

        @Autowired
        private com.example.demo.repository.HotelBookingRepository hotelBookingRepository;

        @Autowired
        private com.example.demo.repository.PackageReviewRepository packageReviewRepository;

        @Autowired
        private HotelImageRepository hotelImageRepository;

        @Override
        @Transactional
        public void run(String... args) throws Exception {
                // Clear existing data to ensure fresh population
                System.out.println("Clearing existing data...");
                seatBookingRepository.deleteAllInBatch();
                hotelBookingRepository.deleteAllInBatch();
                packageReviewRepository.deleteAllInBatch();
                bookingRepository.deleteAllInBatch();

                bookingRepository.deleteAllInBatch();

                hotelImageRepository.deleteAllInBatch();
                hotelRepository.deleteAllInBatch();
                packageRepository.deleteAll();
                flightRepository.deleteAllInBatch();
                busRepository.deleteAllInBatch();
                trainRepository.deleteAllInBatch();

                System.out.println("Initializing default packages...");

                initializeDomesticPackages();

                initializeInternationalPackages();

                initializeHotels();

                // Fix any potential legacy data issues
                fixExistingFlightPrices();

                System.out.println("Default packages initialized successfully.");

        }

        private void initializeDomesticPackages() {
                // 1. Rajasthan Royal
                createPackage(
                                "Rajasthan Royal",
                                "Rajasthan, India",
                                "DOMESTIC",
                                "/images/destination-1.jpg",
                                7,
                                25000.0,
                                "₹",
                                "Experience the royal heritage of Rajasthan.",
                                "Journey through the Land of Kings, visiting majestic forts, opulent palaces, and vibrant markets. This comprehensive tour takes you deep into the heart of India's royal heritage. Explore the amber-hued forts of Jaipur, the blue-washed streets of Jodhpur, and the romantic lakes of Udaipur. Experience the thrill of a desert safari in Jaisalmer and witness the architectural marvels that stand as testaments to a glorious past. Indulge in authentic Rajasthani cuisine and immerse yourself in the colorful culture of this vibrant state.",
                                "FAMILY,COUPLE,CULTURE",
                                "WINTER,SPRING",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival in Jaipur", "Welcome to the Pink City.",
                                                                "https://images.unsplash.com/photo-1599661046289-e31897846e41?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Jaipur Sightseeing", "Visit Amber Fort and City Palace.",
                                                                "https://images.unsplash.com/photo-1524230507669-5ff97982bb5e?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Jodhpur", "Drive to the Blue City.",
                                                                "https://images.unsplash.com/photo-1534234828569-16d299386d8d?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Udaipur", "Travel to the City of Lakes.",
                                                                "https://images.unsplash.com/photo-1615836245337-f5b9b2303f10?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Udaipur Tour", "Boat ride on Lake Pichola.",
                                                                "https://images.unsplash.com/photo-1587595431973-160d0d94add1?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Pushkar", "Visit the Holy Lake.",
                                                                "https://images.unsplash.com/photo-1572885887266-963d33a3237b?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(7, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1477587458883-47145ed94245?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-hotel", "Luxury Hotels"),
                                                createFeature("fa-car", "Private Transfers")),
                                Arrays.asList("https://images.unsplash.com/photo-1477587458883-47145ed94245?q=80&w=2070&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1599661046289-e31897846e41?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1524230507669-5ff97982bb5e?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1534234828569-16d299386d8d?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "IndiGo", 5000.0, "1h 30m", "10:00 AM",
                                                                "11:30 AM"),
                                                createTransport("FLIGHT", "Air India", 6000.0, "1h 45m", "02:00 PM",
                                                                "03:45 PM"),
                                                createTransport("TRAIN", "Rajdhani Express", 2000.0, "4h 30m",
                                                                "06:00 AM", "10:30 AM"),
                                                createTransport("TRAIN", "Shatabdi Express", 1500.0, "5h 00m",
                                                                "03:00 PM", "08:00 PM"),
                                                createTransport("BUS", "Volvo AC", 800.0, "6h 00m", "10:00 PM",
                                                                "04:00 AM"),
                                                createTransport("BUS", "Luxury Sleeper", 1000.0, "6h 30m", "11:00 PM",
                                                                "05:30 AM")));

                // 2. Kerala Bliss
                createPackage(
                                "Kerala Bliss",
                                "Kerala, India",
                                "DOMESTIC",
                                "/images/destination-2.jpg",
                                5,
                                18000.0,
                                "₹",
                                "Relax in God's Own Country.",
                                "Enjoy the serene backwaters, lush tea plantations of Munnar, and the beaches of Alleppey. Kerala, known as God's Own Country, offers a tranquil escape into nature. Cruise through the palm-fringed backwaters on a traditional houseboat, walk through the misty tea gardens of Munnar, and relax on the pristine beaches of the Malabar Coast. This package is designed for relaxation and rejuvenation, featuring Ayurvedic treatments, cultural performances like Kathakali, and the freshest seafood you'll ever taste.",
                                "COUPLE,NATURE,RELAXATION",
                                "WINTER,MONSOON",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival in Kochi", "Transfer to Munnar.",
                                                                "https://images.unsplash.com/photo-1593693397690-362cb9666fc2?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Munnar", "Tea gardens and dams.",
                                                                "https://images.unsplash.com/photo-1593693397690-362cb9666fc2?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Thekkady", "Wildlife sanctuary.",
                                                                "https://images.unsplash.com/photo-1583252994493-2c6769747142?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Alleppey", "Houseboat stay.",
                                                                "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1593693397690-362cb9666fc2?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-ship", "Houseboat"),
                                                createFeature("fa-leaf", "Nature Walks")),
                                Arrays.asList("https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1593693397690-362cb9666fc2?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1583252994493-2c6769747142?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1506744038136-46273834b3fb?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "IndiGo", 6000.0, "2h 00m", "09:00 AM",
                                                                "11:00 AM"),
                                                createTransport("FLIGHT", "SpiceJet", 5500.0, "2h 15m", "01:00 PM",
                                                                "03:15 PM"),
                                                createTransport("TRAIN", "Kerala Express", 2500.0, "24h 00m",
                                                                "11:00 AM", "11:00 AM"),
                                                createTransport("TRAIN", "Rajdhani", 3000.0, "20h 00m", "04:00 PM",
                                                                "12:00 PM"),
                                                createTransport("BUS", "KSRTC Volvo", 1200.0, "12h 00m", "06:00 PM",
                                                                "06:00 AM"),
                                                createTransport("BUS", "Private Sleeper", 1500.0, "11h 00m", "07:00 PM",
                                                                "06:00 AM")));

                // 3. Goa Beach Party
                createPackage(
                                "Goa Beach Party",
                                "Goa, India",
                                "DOMESTIC",
                                "/images/destination-3.jpg",
                                4,
                                15000.0,
                                "₹",
                                "Sun, Sand, and Sea.",
                                "Experience the vibrant nightlife, pristine beaches, and Portuguese heritage of Goa. This is the ultimate beach vacation, combining sun, sand, and sea with a lively party atmosphere. Explore the historic churches of Old Goa, try your hand at water sports on Calangute Beach, and dance the night away at world-famous clubs. Whether you're looking for adventure or relaxation, Goa has something for everyone, from secluded coves to bustling markets.",
                                "FRIENDS,PARTY,BEACH",
                                "WINTER,SUMMER",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival", "Check-in and beach leisure.",
                                                                "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "North Goa", "Fort Aguada and Baga Beach.",
                                                                "https://images.unsplash.com/photo-1587922546307-776227941871?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "South Goa", "Colva Beach and Churches.",
                                                                "https://images.unsplash.com/photo-1544252890-a1e7e871212f?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-umbrella-beach", "Beach Sports"),
                                                createFeature("fa-glass-cheers", "Nightlife")),
                                Arrays.asList("https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1587922546307-776227941871?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1544252890-a1e7e871212f?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1596895111956-bf1cf0599ce5?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "GoFirst", 4000.0, "1h 15m", "11:00 AM",
                                                                "12:15 PM"),
                                                createTransport("FLIGHT", "IndiGo", 4500.0, "1h 10m", "03:00 PM",
                                                                "04:10 PM"),
                                                createTransport("TRAIN", "Konkan Kanya", 1500.0, "10h 00m",
                                                                "10:00 PM", "08:00 AM"),
                                                createTransport("TRAIN", "Mandovi Express", 1200.0, "12h 00m",
                                                                "07:00 AM", "07:00 PM"),
                                                createTransport("BUS", "Paulo Travels", 1000.0, "12h 00m", "08:00 PM",
                                                                "08:00 AM"),
                                                createTransport("BUS", "Neeta Volvo", 1100.0, "11h 30m", "09:00 PM",
                                                                "08:30 AM")));

                // 4. Manali Mountains
                createPackage(
                                "Manali Mountains",
                                "Manali, India",
                                "DOMESTIC",
                                "/images/destination-4.jpg",
                                5,
                                12000.0,
                                "₹",
                                "Escape to the Himalayas.",
                                "Snow-capped peaks, pine forests, and adventure sports await in Manali. Nestled in the Himalayas, Manali is a paradise for nature lovers and adventure seekers alike. Roam through the apple orchards, visit the ancient Hadimba Temple, and take in the breathtaking views from Solang Valley. For the adventurous, there's paragliding, river rafting, and trekking. This package offers a perfect blend of scenic beauty and adrenaline-pumping activities.",
                                "COUPLE,ADVENTURE,NATURE",
                                "SUMMER,WINTER",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival", "Transfer to hotel.",
                                                                "https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Local Sightseeing", "Hadimba Temple and Mall Road.",
                                                                "https://images.unsplash.com/photo-1597048473663-02f5733f3803?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Solang Valley", "Adventure sports.",
                                                                "https://images.unsplash.com/photo-1585644198527-04c7d6664272?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Rohtang Pass", "Snow point visit.",
                                                                "https://images.unsplash.com/photo-1605649487215-476786814580?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Departure", "Transfer to bus stand.",
                                                                "https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-snowflake", "Snow Activities"),
                                                createFeature("fa-hiking", "Trekking")),
                                Arrays.asList("https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1597048473663-02f5733f3803?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1585644198527-04c7d6664272?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1605649487215-476786814580?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Air India", 4000.0, "1h 20m", "08:00 AM",
                                                                "09:20 AM"),
                                                createTransport("FLIGHT", "Alliance Air", 4500.0, "1h 30m", "12:00 PM",
                                                                "01:30 PM"),
                                                createTransport("TRAIN", "Kalka Mail", 1000.0, "8h 00m", "10:00 PM",
                                                                "06:00 AM"),
                                                createTransport("TRAIN", "Shatabdi", 1200.0, "6h 00m", "07:00 AM",
                                                                "01:00 PM"),
                                                createTransport("BUS", "HPTDC Volvo", 1500.0, "14h 00m", "05:00 PM",
                                                                "07:00 AM"),
                                                createTransport("BUS", "Private Volvo", 1200.0, "13h 00m", "06:00 PM",
                                                                "07:00 AM")));

                // 5. Ladakh Adventure
                createPackage(
                                "Ladakh Adventure",
                                "Ladakh, India",
                                "DOMESTIC",
                                "/images/destination-5.jpg",
                                6,
                                35000.0,
                                "₹",
                                "The Land of High Passes.",
                                "Explore the stark beauty of Ladakh, Pangong Lake, and Nubra Valley. Known as the Land of High Passes, Ladakh offers a landscape like no other. Drive through the world's highest motorable passes, marvel at the changing colors of Pangong Lake, and ride the double-humped camels in the sand dunes of Nubra Valley. Visit ancient monasteries perched on hilltops and experience the unique culture of this high-altitude desert. This adventure is a test of endurance and a feast for the eyes.",
                                "ADVENTURE,BIKING,NATURE",
                                "SUMMER",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival in Leh", "Acclimatization.",
                                                                "https://images.unsplash.com/photo-1581793745862-99fde7fa73d2?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Leh Local", "Monasteries and Hall of Fame.",
                                                                "https://images.unsplash.com/photo-1595843685627-029412534568?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Nubra Valley", "Drive via Khardung La.",
                                                                "https://images.unsplash.com/photo-1595183378370-4f519524024c?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Pangong Lake", "Camping by the lake.",
                                                                "https://images.unsplash.com/photo-1566323648-9128913b0c96?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Back to Leh", "Shopping in Leh market.",
                                                                "https://images.unsplash.com/photo-1581793745862-99fde7fa73d2?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1581793745862-99fde7fa73d2?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-motorcycle", "Bike Rental"),
                                                createFeature("fa-campground", "Camping")),
                                Arrays.asList("https://images.unsplash.com/photo-1581793745862-99fde7fa73d2?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1595843685627-029412534568?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1595183378370-4f519524024c?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1566323648-9128913b0c96?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "GoFirst", 5500.0, "1h 15m", "06:00 AM",
                                                                "07:15 AM"),
                                                createTransport("FLIGHT", "Air India", 6000.0, "1h 20m", "08:00 AM",
                                                                "09:20 AM"),
                                                createTransport("TRAIN", "Jammu Mail", 1200.0, "12h 00m", "09:00 PM",
                                                                "09:00 AM"),
                                                createTransport("TRAIN", "Jhelum Express", 1500.0, "14h 00m",
                                                                "08:00 PM", "10:00 AM"),
                                                createTransport("BUS", "HRTC Volvo", 1800.0, "18h 00m", "04:00 PM",
                                                                "10:00 AM"),
                                                createTransport("BUS", "Private Volvo", 2000.0, "17h 00m", "05:00 PM",
                                                                "10:00 AM")));

                // 6. Andaman Island Retreat
                createPackage(
                                "Andaman Island Retreat",
                                "Andaman, India",
                                "DOMESTIC",
                                "/images/destination-6.jpg",
                                6,
                                40000.0,
                                "₹",
                                "Tropical Paradise.",
                                "White sandy beaches, crystal clear waters, and colonial history. Escape to the tropical paradise of the Andaman Islands. Relax on the world-famous Radhanagar Beach, snorkel in the vibrant coral reefs of Elephant Beach, and witness the bioluminescence at Havelock. Explore the limestone caves of Baratang and delve into India's freedom struggle at the Cellular Jail. This package offers the perfect mix of relaxation, adventure, and history.",
                                "HONEYMOON,BEACH,WATERSPORTS",
                                "WINTER,SUMMER",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival in Port Blair", "Cellular Jail visit.",
                                                                "https://images.unsplash.com/photo-1589979481223-deb893043163?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Havelock Island", "Ferry to Havelock.",
                                                                "https://images.unsplash.com/photo-1517427677506-ade074eb1432?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Radhanagar Beach", "Sunset at Asia's best beach.",
                                                                "https://images.unsplash.com/photo-1596324683536-e8229d89201e?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Elephant Beach", "Snorkeling and water sports.",
                                                                "https://images.unsplash.com/photo-1544551763-46a013bb70d5?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Neil Island", "Natural Bridge and beaches.",
                                                                "https://images.unsplash.com/photo-1590053335805-728b184285b0?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Ferry back and airport transfer.",
                                                                "https://images.unsplash.com/photo-1589979481223-deb893043163?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-swimmer", "Snorkeling"),
                                                createFeature("fa-ship", "Cruise")),
                                Arrays.asList("https://images.unsplash.com/photo-1589979481223-deb893043163?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1517427677506-ade074eb1432?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1596324683536-e8229d89201e?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1544551763-46a013bb70d5?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "IndiGo", 8000.0, "2h 30m", "05:00 AM",
                                                                "07:30 AM"),
                                                createTransport("FLIGHT", "Vistara", 9000.0, "2h 45m", "10:00 AM",
                                                                "12:45 PM"),
                                                createTransport("TRAIN", "Coromandel Exp", 2000.0, "26h 00m",
                                                                "04:00 PM", "06:00 PM"),
                                                createTransport("TRAIN", "Chennai Mail", 1800.0, "28h 00m", "11:00 PM",
                                                                "03:00 AM"),
                                                createTransport("BUS", "Ship - MV Campbell", 3000.0, "60h 00m",
                                                                "10:00 AM", "10:00 PM"),
                                                createTransport("BUS", "Ship - MV Nicobar", 2500.0, "65h 00m",
                                                                "12:00 PM", "05:00 AM")));

                // 7. Kashmir Paradise
                createPackage(
                                "Kashmir Paradise",
                                "Srinagar, India",
                                "DOMESTIC",
                                "/images/destination-7.jpg",
                                6,
                                28000.0,
                                "₹",
                                "Paradise on Earth.",
                                "Houseboats on Dal Lake, Mughal Gardens, and snow in Gulmarg. Kashmir, often called Paradise on Earth, is a land of breathtaking beauty. Stay in a traditional houseboat on the Dal Lake, stroll through the manicured Mughal Gardens, and take a gondola ride to the snow-capped peaks of Gulmarg. Visit the saffron fields of Pampore and the meadows of Pahalgam. This tour captures the essence of Kashmir's natural splendor and warm hospitality.",
                                "COUPLE,NATURE,FAMILY",
                                "SUMMER,WINTER",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival in Srinagar", "Shikara ride on Dal Lake.",
                                                                "https://images.unsplash.com/photo-1566837945700-30057527ade0?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Gulmarg", "Gondola ride.",
                                                                "https://images.unsplash.com/photo-1595846519845-68e298c2edd8?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Pahalgam", "Valley of Shepherds.",
                                                                "https://images.unsplash.com/photo-1598091383021-15ddea10925d?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Sonamarg", "Meadow of Gold.",
                                                                "https://images.unsplash.com/photo-1562602741-2cda27f17426?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Srinagar Sightseeing", "Mughal Gardens.",
                                                                "https://images.unsplash.com/photo-1566837945700-30057527ade0?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1566837945700-30057527ade0?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-snowflake", "Snow"),
                                                createFeature("fa-ship", "Shikara Ride")),
                                Arrays.asList("https://images.unsplash.com/photo-1566837945700-30057527ade0?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1595846519845-68e298c2edd8?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1598091383021-15ddea10925d?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1562602741-2cda27f17426?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "IndiGo", 5000.0, "1h 30m", "11:00 AM",
                                                                "12:30 PM"),
                                                createTransport("FLIGHT", "SpiceJet", 4500.0, "1h 40m", "02:00 PM",
                                                                "03:40 PM"),
                                                createTransport("TRAIN", "Jammu Rajdhani", 2500.0, "10h 00m",
                                                                "09:00 PM", "07:00 AM"),
                                                createTransport("TRAIN", "Jhelum Express", 1500.0, "14h 00m",
                                                                "08:00 PM", "10:00 AM"),
                                                createTransport("BUS", "JKSRTC Volvo", 1200.0, "14h 00m", "06:00 PM",
                                                                "08:00 AM"),
                                                createTransport("BUS", "Private Sleeper", 1400.0, "13h 00m", "07:00 PM",
                                                                "08:00 AM")));

                // 8. Golden Triangle
                createPackage(
                                "Golden Triangle",
                                "Delhi, India",
                                "DOMESTIC",
                                "/images/destination-8.jpg",
                                6,
                                20000.0,
                                "₹",
                                "India's Cultural Heart.",
                                "Explore the history of Delhi, the love of Agra, and the royalty of Jaipur. The Golden Triangle tour is the perfect introduction to India's rich cultural heritage. Marvel at the Taj Mahal, one of the Seven Wonders of the World, explore the majestic forts of Jaipur, and wander through the historic streets of Delhi. This journey connects the three iconic cities, offering a glimpse into the Mughal and Rajput eras that shaped India's history.",
                                "CULTURE,HISTORY,FAMILY",
                                "WINTER,SPRING",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival in Delhi", "City tour.",
                                                                "https://images.unsplash.com/photo-1587474260584-136574528ed5?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Delhi to Agra", "Visit Taj Mahal.",
                                                                "https://images.unsplash.com/photo-1564507592333-c60657eea523?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Fatehpur Sikri", "Enroute to Jaipur.",
                                                                "https://images.unsplash.com/photo-1592639296346-560c37a23694?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Jaipur", "Amber Fort and City Palace.",
                                                                "https://images.unsplash.com/photo-1477587458883-47145ed94245?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Jaipur Markets", "Shopping and Hawa Mahal.",
                                                                "https://images.unsplash.com/photo-1599661046289-e31897846e41?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Return to Delhi.",
                                                                "https://images.unsplash.com/photo-1587474260584-136574528ed5?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-monument", "Taj Mahal"),
                                                createFeature("fa-history", "Forts")),
                                Arrays.asList("https://images.unsplash.com/photo-1564507592333-c60657eea523?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1587474260584-136574528ed5?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1592639296346-560c37a23694?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1524492412937-b28074a5d7da?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Air India", 3000.0, "1h 00m", "07:00 AM",
                                                                "08:00 AM"),
                                                createTransport("FLIGHT", "IndiGo", 3500.0, "1h 10m", "05:00 PM",
                                                                "06:10 PM"),
                                                createTransport("TRAIN", "Gatimaan Express", 1500.0, "1h 40m",
                                                                "08:10 AM", "09:50 AM"),
                                                createTransport("TRAIN", "Shatabdi", 1200.0, "2h 00m", "06:00 AM",
                                                                "08:00 AM"),
                                                createTransport("BUS", "Volvo AC", 800.0, "4h 00m", "07:00 AM",
                                                                "11:00 AM"),
                                                createTransport("BUS", "Private Bus", 600.0, "4h 30m", "02:00 PM",
                                                                "06:30 PM")));

                // 9. Varanasi Spiritual
                createPackage(
                                "Varanasi Spiritual",
                                "Varanasi, India",
                                "DOMESTIC",
                                "/images/destination-9.jpg",
                                4,
                                12000.0,
                                "₹",
                                "The City of Light.",
                                "Experience the spiritual energy of the Ganges, ancient temples, and the mesmerizing Ganga Aarti. Varanasi, one of the world's oldest living cities, is a place of profound spirituality. Witness the spectacular Ganga Aarti at the ghats, take a sunrise boat ride on the holy river, and navigate the narrow winding alleys filled with temples and shrines. Visit Sarnath, where Lord Buddha gave his first sermon. This journey is a soul-stirring experience that will stay with you forever.",
                                "SPIRITUAL,CULTURE,SOLO",
                                "WINTER,SPRING",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival", "Evening Ganga Aarti.",
                                                                "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Temple Tour", "Kashi Vishwanath and Sarnath.",
                                                                "https://images.unsplash.com/photo-1622194998097-137209947761?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Ghats Boat Ride", "Sunrise boat ride.",
                                                                "https://images.unsplash.com/photo-1591389703635-e15a07b842d7?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1561361513-2d000a50f0dc?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-om", "Temples"),
                                                createFeature("fa-water", "Ganga Aarti")),
                                Arrays.asList("https://images.unsplash.com/photo-1561361513-2d000a50f0dc?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1622194998097-137209947761?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1591389703635-e15a07b842d7?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1571545763668-52467554472d?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "IndiGo", 4000.0, "1h 20m", "10:00 AM",
                                                                "11:20 AM"),
                                                createTransport("FLIGHT", "SpiceJet", 3500.0, "1h 30m", "02:00 PM",
                                                                "03:30 PM"),
                                                createTransport("TRAIN", "Vande Bharat", 1500.0, "8h 00m", "06:00 AM",
                                                                "02:00 PM"),
                                                createTransport("TRAIN", "Shiv Ganga Exp", 1200.0, "10h 00m",
                                                                "07:00 PM", "05:00 AM"),
                                                createTransport("BUS", "UPSRTC Volvo", 800.0, "12h 00m", "08:00 PM",
                                                                "08:00 AM"),
                                                createTransport("BUS", "Private Sleeper", 1000.0, "11h 00m", "09:00 PM",
                                                                "08:00 AM")));

                // 10. Rishikesh Yoga
                createPackage(
                                "Rishikesh Yoga",
                                "Rishikesh, India",
                                "DOMESTIC",
                                "/images/destination-10.jpg",
                                5,
                                15000.0,
                                "₹",
                                "Yoga Capital of the World.",
                                "Rejuvenate with yoga, meditation, and white water rafting in the Ganges. Rishikesh, the Yoga Capital of the World, offers a perfect blend of spirituality and adventure. Attend yoga sessions by the Ganges, meditate in peaceful ashrams, and experience the thrill of white water rafting. Visit the iconic suspension bridges, Ram Jhula and Laxman Jhula, and the famous Beatles Ashram. This package is designed to heal your body, mind, and soul.",
                                "SPIRITUAL,ADVENTURE,WELLNESS",
                                "SPRING,AUTUMN",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival", "Check-in to Ashram/Hotel.",
                                                                "https://images.unsplash.com/photo-1591208920956-25e7960f79d9?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Yoga & Meditation", "Morning session and Ganga Aarti.",
                                                                "https://images.unsplash.com/photo-1599447421405-0753f59aa182?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "River Rafting", "Adventure on the Ganges.",
                                                                "https://images.unsplash.com/photo-1530026405186-ed1f139313f8?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Beatles Ashram", "Visit historic sites.",
                                                                "https://images.unsplash.com/photo-1591208920956-25e7960f79d9?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Departure", "Transfer to Dehradun airport.",
                                                                "https://images.unsplash.com/photo-1591208920956-25e7960f79d9?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-spa", "Yoga"), createFeature("fa-water", "Rafting")),
                                Arrays.asList("https://images.unsplash.com/photo-1591208920956-25e7960f79d9?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1599447421405-0753f59aa182?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1530026405186-ed1f139313f8?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1603867106100-0d2039fc8755?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "IndiGo", 3500.0, "1h 00m", "07:00 AM",
                                                                "08:00 AM"),
                                                createTransport("FLIGHT", "Air India", 4000.0, "1h 10m", "04:00 PM",
                                                                "05:10 PM"),
                                                createTransport("TRAIN", "Shatabdi", 1000.0, "6h 00m", "07:00 AM",
                                                                "01:00 PM"),
                                                createTransport("TRAIN", "Jan Shatabdi", 800.0, "7h 00m", "03:00 PM",
                                                                "10:00 PM"),
                                                createTransport("BUS", "Volvo AC", 700.0, "6h 00m", "10:00 PM",
                                                                "04:00 AM"),
                                                createTransport("BUS", "Private Bus", 600.0, "7h 00m", "11:00 PM",
                                                                "06:00 AM")));

                // 11. Ooty & Coorg
                createPackage(
                                "Ooty & Coorg",
                                "Ooty, India",
                                "DOMESTIC",
                                "/images/destination-11.jpg",
                                6,
                                22000.0,
                                "₹",
                                "Hill Stations of the South.",
                                "Lush green coffee plantations, botanical gardens, and misty hills. Escape to the cool climes of Ooty and Coorg, the jewels of South India. Walk through aromatic coffee and spice plantations, visit the cascading Abbey Falls, and take a ride on the heritage Nilgiri Mountain Railway. Explore the botanical gardens of Ooty and the dubare elephant camp in Coorg. This tour is a refreshing retreat into nature's lap.",
                                "NATURE,COUPLE,FAMILY",
                                "SUMMER,WINTER",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival in Bangalore", "Drive to Coorg.",
                                                                "https://images.unsplash.com/photo-1596021688661-3424d4444856?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Coorg Sightseeing",
                                                                "Abbey Falls and Dubare Elephant Camp.",
                                                                "https://images.unsplash.com/photo-1596021688661-3424d4444856?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Coorg to Ooty", "Scenic drive.",
                                                                "https://images.unsplash.com/photo-1548695607-9c73430ba065?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Ooty Gardens", "Botanical Garden and Ooty Lake.",
                                                                "https://images.unsplash.com/photo-1548695607-9c73430ba065?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Coonoor", "Toy train ride.",
                                                                "https://images.unsplash.com/photo-1516483638261-f4dbaf036963?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to Coimbatore/Bangalore.",
                                                                "https://images.unsplash.com/photo-1548695607-9c73430ba065?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-coffee", "Coffee Plantations"),
                                                createFeature("fa-train", "Toy Train")),
                                Arrays.asList("https://images.unsplash.com/photo-1548695607-9c73430ba065?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1596021688661-3424d4444856?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1516483638261-f4dbaf036963?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1582556369584-d80977852d60?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "IndiGo", 4000.0, "1h 00m", "06:00 AM",
                                                                "07:00 AM"),
                                                createTransport("FLIGHT", "AirAsia", 3500.0, "1h 10m", "08:00 PM",
                                                                "09:10 PM"),
                                                createTransport("TRAIN", "Shatabdi", 1200.0, "7h 00m", "06:00 AM",
                                                                "01:00 PM"),
                                                createTransport("TRAIN", "Intercity Exp", 800.0, "8h 00m", "02:00 PM",
                                                                "10:00 PM"),
                                                createTransport("BUS", "KSRTC Flybus", 1000.0, "7h 00m", "10:00 PM",
                                                                "05:00 AM"),
                                                createTransport("BUS", "Private Sleeper", 1200.0, "6h 30m", "11:00 PM",
                                                                "05:30 AM")));

                // 12. Darjeeling & Gangtok
                createPackage(
                                "Darjeeling & Gangtok",
                                "Darjeeling, India",
                                "DOMESTIC",
                                "/images/destination-12.jpg",
                                6,
                                25000.0,
                                "₹",
                                "Eastern Himalayas.",
                                "Sunrise at Tiger Hill, tea gardens, and Buddhist monasteries. Witness the grandeur of the Eastern Himalayas on this journey through Darjeeling and Gangtok. Watch the sunrise over Mt. Kanchenjunga from Tiger Hill, ride the famous Darjeeling Himalayan Railway, and visit the serene monasteries of Gangtok. Explore the high-altitude Tsomgo Lake and the vibrant markets filled with local handicrafts. This tour offers a perfect blend of nature, culture, and adventure.",
                                "NATURE,CULTURE,FAMILY",
                                "SUMMER,SPRING",
                                null,
                                Arrays.asList(
                                                createDay(1, "Arrival in Bagdogra", "Transfer to Darjeeling.",
                                                                "https://images.unsplash.com/photo-1588416936097-41850ab3d86d?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Tiger Hill", "Sunrise view of Kanchenjunga.",
                                                                "https://images.unsplash.com/photo-1545652985-5edd366b465b?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Darjeeling to Gangtok", "Scenic drive.",
                                                                "https://images.unsplash.com/photo-1589041127168-9b1915731839?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Tsomgo Lake", "High altitude lake visit.",
                                                                "https://images.unsplash.com/photo-1628065327221-55e13d100c4e?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Gangtok Sightseeing", "Monasteries and Ropeway.",
                                                                "https://images.unsplash.com/photo-1589041127168-9b1915731839?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1588416936097-41850ab3d86d?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-mountain", "Himalayas"),
                                                createFeature("fa-leaf", "Tea Gardens")),
                                Arrays.asList("https://images.unsplash.com/photo-1588416936097-41850ab3d86d?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1545652985-5edd366b465b?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1589041127168-9b1915731839?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1628065327221-55e13d100c4e?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "IndiGo", 5000.0, "1h 15m", "09:00 AM",
                                                                "10:15 AM"),
                                                createTransport("FLIGHT", "SpiceJet", 4500.0, "1h 20m", "01:00 PM",
                                                                "02:20 PM"),
                                                createTransport("TRAIN", "Darjeeling Mail", 1500.0, "12h 00m",
                                                                "10:00 PM", "10:00 AM"),
                                                createTransport("TRAIN", "Padatik Express", 1200.0, "13h 00m",
                                                                "11:00 PM", "12:00 PM"),
                                                createTransport("BUS", "NBSTC Rocket", 800.0, "14h 00m", "05:00 PM",
                                                                "07:00 AM"),
                                                createTransport("BUS", "Private Volvo", 1000.0, "13h 00m", "06:00 PM",
                                                                "07:00 AM")));
        }

        private void initializeInternationalPackages() {
                // 1. Paris Romance
                createPackage(
                                "Paris Romance",
                                "Paris, France",
                                "INTERNATIONAL",
                                "/images/place-1.jpg",
                                6,
                                1500.0,
                                "$",
                                "The City of Light awaits.",
                                "Experience the romance of Paris with visits to the Eiffel Tower, Louvre Museum, and a Seine River cruise. Paris, the City of Light, is a dream destination for lovers and art enthusiasts. Marvel at the iconic Eiffel Tower, admire the masterpieces at the Louvre, and stroll along the charming Champs-Élysées. Take a romantic cruise on the Seine River and indulge in exquisite French cuisine. This package captures the magic and elegance of one of the world's most beautiful cities.",
                                "COUPLE,HONEYMOON,CITY",
                                "SPRING,SUMMER,AUTUMN",
                                "Schengen Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival in Paris", "Private transfer to hotel.",
                                                                "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Eiffel Tower & Cruise",
                                                                "Skip-the-line Eiffel Tower access and river cruise.",
                                                                "https://images.unsplash.com/photo-1431274172761-fca41d930114?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Louvre Museum", "Guided tour of the Louvre.",
                                                                "https://images.unsplash.com/photo-1499856871940-a09627c6dcf6?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Versailles Day Trip", "Visit the Palace of Versailles.",
                                                                "https://images.unsplash.com/photo-1566321635850-27227092e071?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Montmartre & Shopping",
                                                                "Explore Montmartre and Champs-Élysées.",
                                                                "https://images.unsplash.com/photo-1503917988258-f87a78e3c99d?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to CDG Airport.",
                                                                "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-wine-glass", "Romantic Dinner"),
                                                createFeature("fa-landmark", "Museum Pass")),
                                Arrays.asList("https://images.unsplash.com/photo-1502602898657-3e91760cbb34?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1431274172761-fca41d930114?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1499856871940-a09627c6dcf6?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1566321635850-27227092e071?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Air France", 65000.0, "9h 30m", "02:00 AM",
                                                                "08:00 AM"),
                                                createTransport("FLIGHT", "Emirates", 70000.0, "12h 00m", "04:00 AM",
                                                                "01:00 PM")));

                // 2. Dubai Extravaganza
                createPackage(
                                "Dubai Extravaganza",
                                "Dubai, UAE",
                                "INTERNATIONAL",
                                "/images/place-2.jpg",
                                5,
                                1200.0,
                                "$",
                                "Luxury and adventure in the desert.",
                                "Visit the Burj Khalifa, enjoy a desert safari, and shop at the Dubai Mall. Dubai is a city of superlatives, where traditional Arabian culture meets futuristic innovation. Stand atop the world's tallest building, the Burj Khalifa, experience the thrill of a desert safari with dune bashing, and shop till you drop at the massive Dubai Mall. Visit the stunning Sheikh Zayed Mosque in Abu Dhabi and enjoy the vibrant nightlife. This tour offers a taste of luxury and adventure in the desert.",
                                "FAMILY,LUXURY,SHOPPING",
                                "WINTER",
                                "UAE Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival", "Transfer to hotel.",
                                                                "https://images.unsplash.com/photo-1512453979798-5ea904ac6605?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "City Tour & Burj Khalifa",
                                                                "Modern Dubai tour and At The Top.",
                                                                "https://images.unsplash.com/photo-1546412414-e1885259563a?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Desert Safari", "Dune bashing and BBQ dinner.",
                                                                "https://images.unsplash.com/photo-1451337516015-6b6e9a44a8a3?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Abu Dhabi Day Trip", "Visit Sheikh Zayed Mosque.",
                                                                "https://images.unsplash.com/photo-1512453979798-5ea904ac6605?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1512453979798-5ea904ac6605?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-building", "Burj Khalifa"),
                                                createFeature("fa-sun", "Desert Safari")),
                                Arrays.asList("https://images.unsplash.com/photo-1512453979798-5ea904ac6605?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1546412414-e1885259563a?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1451337516015-6b6e9a44a8a3?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1578895101408-1a36b834405b?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Emirates", 25000.0, "4h 00m", "10:00 AM",
                                                                "02:00 PM"),
                                                createTransport("FLIGHT", "FlyDubai", 20000.0, "4h 15m", "06:00 PM",
                                                                "10:15 PM")));

                // 3. Swiss Alps Adventure
                createPackage(
                                "Swiss Alps Adventure",
                                "Zurich, Switzerland",
                                "INTERNATIONAL",
                                "/images/place-3.jpg",
                                7,
                                2500.0,
                                "$",
                                "Breathtaking mountains and lakes.",
                                "Explore Zurich, Lucerne, and Interlaken. Ride the scenic trains and enjoy the alpine beauty. Switzerland is a land of postcard-perfect landscapes, with snow-capped mountains, pristine lakes, and charming villages. Ride the scenic Glacier Express, visit the top of Europe at Jungfraujoch, and explore the medieval streets of Lucerne. This package offers a journey through the heart of the Swiss Alps, promising breathtaking views and unforgettable memories.",
                                "NATURE,COUPLE,ADVENTURE",
                                "SUMMER,WINTER",
                                "Schengen Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival in Zurich", "Train to Lucerne.",
                                                                "https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Mt. Pilatus", "Excursion to Mt. Pilatus.",
                                                                "https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Interlaken", "Train to Interlaken.",
                                                                "https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Jungfraujoch", "Top of Europe excursion.",
                                                                "https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Glacier Express", "Scenic train ride.",
                                                                "https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Zurich", "City tour.",
                                                                "https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(7, "Departure", "Train to airport.",
                                                                "https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-train", "Swiss Travel Pass"),
                                                createFeature("fa-mountain", "Mountain Excursions")),
                                Arrays.asList("https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1527668752968-14dc70a27c95?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1589820296156-2454bb8a6d54?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1558271736-cd042a92d834?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Swiss Air", 60000.0, "8h 30m", "01:00 AM",
                                                                "07:30 AM"),
                                                createTransport("FLIGHT", "Lufthansa", 58000.0, "10h 00m", "03:00 AM",
                                                                "11:00 AM")));

                // 4. Thailand Beaches
                createPackage(
                                "Thailand Beaches",
                                "Phuket, Thailand",
                                "INTERNATIONAL",
                                "/images/place-4.jpg",
                                6,
                                800.0,
                                "$",
                                "Land of Smiles.",
                                "Explore the stunning beaches of Phuket and Krabi, and enjoy the vibrant nightlife. Thailand, the Land of Smiles, offers a perfect tropical getaway. Relax on the white sandy beaches of Phuket, go island hopping in the turquoise waters of Krabi, and experience the legendary nightlife. Visit the iconic Big Buddha, explore the limestone cliffs, and indulge in delicious Thai street food. This package is a mix of relaxation, adventure, and fun.",
                                "FRIENDS,BEACH,PARTY",
                                "WINTER,SPRING",
                                "Visa on Arrival",
                                Arrays.asList(
                                                createDay(1, "Arrival in Phuket", "Transfer to hotel.",
                                                                "https://images.unsplash.com/photo-1552465011-b4e21bf6e79a?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Phi Phi Islands", "Speedboat tour.",
                                                                "https://images.unsplash.com/photo-1537956965359-7573183d1f57?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Phuket City Tour", "Big Buddha and Old Town.",
                                                                "https://images.unsplash.com/photo-1589394815804-964ed0be2eb5?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Transfer to Krabi", "Ferry ride.",
                                                                "https://images.unsplash.com/photo-1552465011-b4e21bf6e79a?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Four Islands Tour", "Island hopping in Krabi.",
                                                                "https://images.unsplash.com/photo-1518509562904-e7ef99cdcc86?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1552465011-b4e21bf6e79a?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-umbrella-beach", "Island Hopping"),
                                                createFeature("fa-cocktail", "Nightlife")),
                                Arrays.asList("https://images.unsplash.com/photo-1552465011-b4e21bf6e79a?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1537956965359-7573183d1f57?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1589394815804-964ed0be2eb5?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1518509562904-e7ef99cdcc86?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Thai Airways", 25000.0, "4h 30m", "11:00 PM",
                                                                "04:30 AM"),
                                                createTransport("FLIGHT", "AirAsia", 20000.0, "5h 00m", "10:00 PM",
                                                                "04:00 AM")));

                // 5. Bali Tropical Paradise
                createPackage(
                                "Bali Tropical Paradise",
                                "Bali, Indonesia",
                                "INTERNATIONAL",
                                "/images/place-5.jpg",
                                7,
                                1000.0,
                                "$",
                                "Island of the Gods.",
                                "Experience the spiritual culture of Ubud, the rice terraces, and the beaches of Seminyak. Bali, the Island of the Gods, is a paradise of lush landscapes and spiritual tranquility. Visit the sacred Monkey Forest in Ubud, marvel at the Tegalalang Rice Terraces, and watch the sunset at Uluwatu Temple. Relax on the beaches of Seminyak and enjoy a traditional Balinese massage. This tour offers a deep dive into the unique culture and natural beauty of Bali.",
                                "COUPLE,CULTURE,NATURE",
                                "SUMMER,AUTUMN",
                                "Visa on Arrival",
                                Arrays.asList(
                                                createDay(1, "Arrival in Bali", "Transfer to Ubud.",
                                                                "https://images.unsplash.com/photo-1537996194471-e657df975ab4?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Ubud Tour", "Monkey Forest and Rice Terraces.",
                                                                "https://images.unsplash.com/photo-1518548419970-58e3b4079ab2?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Kintamani Volcano", "View of Mt. Batur.",
                                                                "https://images.unsplash.com/photo-1552733407-5d5c46c3bb3b?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Transfer to Seminyak", "Beach leisure.",
                                                                "https://images.unsplash.com/photo-1573790387438-4da905039392?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Nusa Penida", "Day trip to island.",
                                                                "https://images.unsplash.com/photo-1598324789736-4861f89564a0?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Uluwatu Temple", "Sunset Kecak Dance.",
                                                                "https://images.unsplash.com/photo-1555400038-63f5ba517a47?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(7, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1537996194471-e657df975ab4?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-spa", "Balinese Massage"),
                                                createFeature("fa-water", "Beaches")),
                                Arrays.asList("https://images.unsplash.com/photo-1537996194471-e657df975ab4?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1518548419970-58e3b4079ab2?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1552733407-5d5c46c3bb3b?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1573790387438-4da905039392?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Garuda Indonesia", 35000.0, "7h 00m",
                                                                "08:00 AM", "04:00 PM"),
                                                createTransport("FLIGHT", "Singapore Airlines", 40000.0, "8h 00m",
                                                                "06:00 AM", "03:00 PM")));

                // 6. Singapore City Tour
                createPackage(
                                "Singapore City Tour",
                                "Singapore",
                                "INTERNATIONAL",
                                "/images/image_1.jpg",
                                5,
                                1300.0,
                                "$",
                                "The Lion City.",
                                "Explore the futuristic Gardens by the Bay, Sentosa Island, and Universal Studios. Singapore is a vibrant city-state that seamlessly blends nature and modernity. Marvel at the Supertree Grove in Gardens by the Bay, have a fun-filled day at Universal Studios on Sentosa Island, and shop on Orchard Road. Experience the diverse culinary scene and the efficient public transport. This package is perfect for families and city lovers.",
                                "FAMILY,CITY,SHOPPING",
                                "ALL_YEAR",
                                "Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival", "Night Safari.",
                                                                "https://images.unsplash.com/photo-1525625293386-3f8f99389edd?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "City Tour", "Merlion and Marina Bay Sands.",
                                                                "https://images.unsplash.com/photo-1506318137071-a8bcbf6755dd?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Sentosa Island", "Cable car and beaches.",
                                                                "https://images.unsplash.com/photo-1542931287-023b922fa89b?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Universal Studios", "Full day fun.",
                                                                "https://images.unsplash.com/photo-1535916707213-3846a482e87f?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Departure", "Jewel Changi visit.",
                                                                "https://images.unsplash.com/photo-1525625293386-3f8f99389edd?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-city", "City Views"),
                                                createFeature("fa-ticket-alt", "Theme Parks")),
                                Arrays.asList("https://images.unsplash.com/photo-1525625293386-3f8f99389edd?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1506318137071-a8bcbf6755dd?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1542931287-023b922fa89b?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1535916707213-3846a482e87f?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Singapore Airlines", 30000.0, "5h 30m",
                                                                "11:00 PM", "04:30 AM"),
                                                createTransport("FLIGHT", "IndiGo", 15000.0, "6h 00m", "10:00 PM",
                                                                "04:00 AM")));

                // 7. London Heritage
                createPackage(
                                "London Heritage",
                                "London, UK",
                                "INTERNATIONAL",
                                "/images/image_2.jpg",
                                6,
                                1800.0,
                                "$",
                                "Royal London.",
                                "Visit Buckingham Palace, Tower of London, and enjoy a Thames River cruise. London, a city steeped in history and culture, offers a royal experience like no other. Witness the Changing of the Guard at Buckingham Palace, explore the gruesome history of the Tower of London, and see the Crown Jewels. Take a cruise on the River Thames to see iconic landmarks like the London Eye and Big Ben. Visit world-class museums and enjoy a West End show. This tour is a journey through the heart of the British monarchy.",
                                "HISTORY,CULTURE,CITY",
                                "SUMMER,SPRING",
                                "UK Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival", "Transfer to hotel.",
                                                                "https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Hop-on Hop-off", "City sightseeing.",
                                                                "https://images.unsplash.com/photo-1486299267070-83823f5448dd?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Tower of London", "Crown Jewels.",
                                                                "https://images.unsplash.com/photo-1532423622396-10a3f979251a?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "British Museum", "History tour.",
                                                                "https://images.unsplash.com/photo-1564959130747-897fb406b9af?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Windsor Castle", "Day trip.",
                                                                "https://images.unsplash.com/photo-1590664095641-7fa0526c02b9?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to Heathrow.",
                                                                "https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-crown", "Royal Palaces"),
                                                createFeature("fa-bus", "City Tour")),
                                Arrays.asList("https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1486299267070-83823f5448dd?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1532423622396-10a3f979251a?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1564959130747-897fb406b9af?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "British Airways", 65000.0, "9h 30m",
                                                                "02:00 AM", "07:30 AM"),
                                                createTransport("FLIGHT", "Air India", 60000.0, "10h 00m", "06:00 AM",
                                                                "04:00 PM")));

                // 8. New York City Lights
                createPackage(
                                "New York City Lights",
                                "New York, USA",
                                "INTERNATIONAL",
                                "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?q=80&w=2000&auto=format&fit=crop",
                                6,
                                2200.0,
                                "$",
                                "The Big Apple.",
                                "Times Square, Statue of Liberty, and Central Park. Experience the energy of the Big Apple, the city that never sleeps. Stand in the neon glow of Times Square, take a ferry to the Statue of Liberty, and stroll through the expansive Central Park. Visit world-renowned museums like the Met and MoMA, catch a Broadway show, and enjoy the panoramic views from the Empire State Building. This package offers the ultimate New York City experience.",
                                "CITY,SHOPPING,CULTURE",
                                "SPRING,AUTUMN,SUMMER",
                                "US Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival", "Transfer to Manhattan.",
                                                                "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Statue of Liberty", "Ferry ride.",
                                                                "https://images.unsplash.com/photo-1550664890-c5e34a6cad31?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Central Park & Museums", "Met Museum.",
                                                                "https://images.unsplash.com/photo-1534270804882-6b5048b1c1fc?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Empire State Building", "City views.",
                                                                "https://images.unsplash.com/photo-1555109307-f7d9da25c244?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Broadway Show", "Evening entertainment.",
                                                                "https://images.unsplash.com/photo-1508717272800-9fff97da7e8f?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to JFK.",
                                                                "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-building", "Skyscrapers"),
                                                createFeature("fa-theater-masks", "Broadway")),
                                Arrays.asList("https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1550664890-c5e34a6cad31?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1534270804882-6b5048b1c1fc?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1555109307-f7d9da25c244?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "United Airlines", 80000.0, "15h 00m",
                                                                "11:00 PM", "08:00 AM"),
                                                createTransport("FLIGHT", "Air India", 75000.0, "16h 00m", "02:00 AM",
                                                                "08:00 AM")));

                // 9. Maldives Honeymoon
                createPackage(
                                "Maldives Honeymoon",
                                "Male, Maldives",
                                "INTERNATIONAL",
                                "https://images.unsplash.com/photo-1514282401047-d79a71a590e8?q=80&w=2000&auto=format&fit=crop",
                                5,
                                2500.0,
                                "$",
                                "Luxury Water Villas.",
                                "Stay in overwater villas, enjoy snorkeling, and romantic dinners on the beach. Escape to the Maldives, a tropical paradise of turquoise lagoons and white sandy beaches. Stay in a luxurious overwater villa, wake up to the sound of the ocean, and snorkel in the vibrant coral reefs right from your doorstep. Enjoy a romantic sunset cruise, indulge in a spa treatment, and dine under the stars on a private beach. This is the ultimate honeymoon destination.",
                                "HONEYMOON,LUXURY,BEACH",
                                "WINTER,SPRING",
                                "Visa on Arrival",
                                Arrays.asList(
                                                createDay(1, "Arrival", "Speedboat to resort.",
                                                                "https://images.unsplash.com/photo-1514282401047-d79a71a590e8?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Water Villa Stay", "Relaxation.",
                                                                "https://images.unsplash.com/photo-1573843981267-be1999ff37cd?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Snorkeling", "Coral reef exploration.",
                                                                "https://images.unsplash.com/photo-1544551763-46a013bb70d5?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Sunset Cruise", "Dolphin watching.",
                                                                "https://images.unsplash.com/photo-1540206395-688085723adb?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Departure", "Speedboat to airport.",
                                                                "https://images.unsplash.com/photo-1514282401047-d79a71a590e8?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-water", "Overwater Villa"),
                                                createFeature("fa-heart", "Honeymoon Special")),
                                Arrays.asList("https://images.unsplash.com/photo-1514282401047-d79a71a590e8?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1573843981267-be1999ff37cd?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1544551763-46a013bb70d5?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1540206395-688085723adb?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "IndiGo", 20000.0, "4h 00m", "10:00 AM",
                                                                "02:00 PM"),
                                                createTransport("FLIGHT", "Maldivian", 25000.0, "4h 30m", "02:00 PM",
                                                                "06:30 PM")));

                // 10. Japan Cherry Blossom
                createPackage(
                                "Japan Cherry Blossom",
                                "Tokyo, Japan",
                                "INTERNATIONAL",
                                "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?q=80&w=2000&auto=format&fit=crop",
                                7,
                                2000.0,
                                "$",
                                "Land of the Rising Sun.",
                                "Experience Tokyo's neon lights, Kyoto's temples, and the beauty of cherry blossoms. Japan is a land of contrasts, where ancient traditions meet futuristic technology. Explore the bustling streets of Tokyo, ride the Shinkansen bullet train, and visit the serene temples of Kyoto. Witness the breathtaking beauty of cherry blossoms in spring and see the majestic Mount Fuji. This tour offers a deep dive into Japanese culture, cuisine, and history.",
                                "CULTURE,NATURE,CITY",
                                "SPRING",
                                "Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival in Tokyo", "Shinjuku exploration.",
                                                                "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Tokyo Sightseeing", "Senso-ji and Shibuya.",
                                                                "https://images.unsplash.com/photo-1536098561742-ca998e48cbcc?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Mt. Fuji", "Day trip.",
                                                                "https://images.unsplash.com/photo-1490806843957-31f4c9a91c65?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Bullet Train to Kyoto", "Experience Shinkansen.",
                                                                "https://images.unsplash.com/photo-1554797589-7241bb691973?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Kyoto Temples", "Fushimi Inari and Kinkaku-ji.",
                                                                "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Osaka", "Day trip and food tour.",
                                                                "https://images.unsplash.com/photo-1590559899731-a382839e5549?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(7, "Departure", "Train to Narita/Haneda.",
                                                                "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-train", "Bullet Train"),
                                                createFeature("fa-fan", "Culture")),
                                Arrays.asList("https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1536098561742-ca998e48cbcc?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1490806843957-31f4c9a91c65?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "JAL", 55000.0, "9h 00m", "08:00 PM",
                                                                "08:00 AM"),
                                                createTransport("FLIGHT", "ANA", 60000.0, "9h 30m", "09:00 PM",
                                                                "09:30 AM")));

                // 11. Vietnam Ha Long Bay
                createPackage(
                                "Vietnam Ha Long Bay",
                                "Hanoi, Vietnam",
                                "INTERNATIONAL",
                                "https://images.unsplash.com/photo-1528127269322-539801943592?q=80&w=2000&auto=format&fit=crop",
                                6,
                                900.0,
                                "$",
                                "Timeless Charm.",
                                "Explore Hanoi's Old Quarter and cruise through the limestone karsts of Ha Long Bay. Vietnam is a country of timeless charm and natural beauty. Wander through the chaotic yet captivating streets of Hanoi's Old Quarter, cruise through the emerald waters of Ha Long Bay, and explore the stunning caves. Visit the ancient capital of Ninh Binh and taste the delicious street food. This package offers an authentic Vietnamese experience.",
                                "NATURE,CULTURE,BUDGET",
                                "SPRING,AUTUMN",
                                "Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival in Hanoi", "Old Quarter tour.",
                                                                "https://images.unsplash.com/photo-1555921015-5532091f6026?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Ha Long Bay", "Cruise and overnight on boat.",
                                                                "https://images.unsplash.com/photo-1528127269322-539801943592?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Ha Long to Hanoi", "Cave visit and return.",
                                                                "https://images.unsplash.com/photo-1504457047772-27faf1c00561?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Ninh Binh", "Day trip to 'Ha Long on Land'.",
                                                                "https://images.unsplash.com/photo-1583417319070-4a69db38a482?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Hanoi City", "Ho Chi Minh Mausoleum.",
                                                                "https://images.unsplash.com/photo-1555921015-5532091f6026?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1555921015-5532091f6026?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-ship", "Overnight Cruise"),
                                                createFeature("fa-utensils", "Street Food")),
                                Arrays.asList("https://images.unsplash.com/photo-1528127269322-539801943592?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1555921015-5532091f6026?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1504457047772-27faf1c00561?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1583417319070-4a69db38a482?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Vietnam Airlines", 25000.0, "4h 30m",
                                                                "08:00 AM", "12:30 PM"),
                                                createTransport("FLIGHT", "VietJet Air", 20000.0, "5h 00m", "02:00 PM",
                                                                "07:00 PM")));

                // 12. Sri Lanka Emerald
                createPackage(
                                "Sri Lanka Emerald",
                                "Colombo, Sri Lanka",
                                "INTERNATIONAL",
                                "https://images.unsplash.com/photo-1586861635167-e5223aeb4227?q=80&w=2000&auto=format&fit=crop",
                                6,
                                950.0,
                                "$",
                                "Pearl of the Indian Ocean.",
                                "Tea plantations, beaches, and wildlife safaris in Yala National Park. Sri Lanka, the Pearl of the Indian Ocean, packs a punch with its diverse landscapes. Visit the Temple of the Tooth in Kandy, take a scenic train ride through the tea plantations of Nuwara Eliya, and go on a leopard safari in Yala National Park. Relax on the golden beaches of Bentota and explore the colonial architecture of Galle. This tour offers a mix of culture, nature, and wildlife.",
                                "NATURE,WILDLIFE,BEACH",
                                "WINTER,SPRING",
                                "Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival in Colombo", "Transfer to Kandy.",
                                                                "https://images.unsplash.com/photo-1588258524675-55d6563e4d8e?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Kandy", "Temple of the Tooth.",
                                                                "https://images.unsplash.com/photo-1580910532849-0d196885808d?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Nuwara Eliya", "Tea gardens and train ride.",
                                                                "https://images.unsplash.com/photo-1546708773-e57fa6497339?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Yala National Park", "Leopard safari.",
                                                                "https://images.unsplash.com/photo-1535338454770-8be927b5a00b?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Bentota", "Beach relaxation.",
                                                                "https://images.unsplash.com/photo-1586861635167-e5223aeb4227?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1586861635167-e5223aeb4227?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-paw", "Wildlife Safari"),
                                                createFeature("fa-train", "Scenic Train")),
                                Arrays.asList("https://images.unsplash.com/photo-1586861635167-e5223aeb4227?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1588258524675-55d6563e4d8e?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1580910532849-0d196885808d?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1546708773-e57fa6497339?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "SriLankan Airlines", 15000.0, "3h 30m",
                                                                "10:00 AM", "01:30 PM"),
                                                createTransport("FLIGHT", "IndiGo", 12000.0, "4h 00m", "06:00 PM",
                                                                "10:00 PM")));

                // 13. Italy (Rome & Venice)
                createPackage(
                                "Italy Highlights",
                                "Rome, Italy",
                                "INTERNATIONAL",
                                "https://images.unsplash.com/photo-1529260830199-42c42dda5f3d?q=80&w=2000&auto=format&fit=crop",
                                7,
                                2000.0,
                                "$",
                                "La Dolce Vita.",
                                "Explore the Colosseum in Rome and ride a gondola in Venice. Italy is a feast for the senses, offering art, history, and culinary delights. Walk in the footsteps of gladiators at the Colosseum, marvel at the art in the Vatican Museums, and throw a coin in the Trevi Fountain. In Venice, glide through the canals on a gondola and get lost in the maze of narrow streets. This package captures the essence of La Dolce Vita.",
                                "HISTORY,CULTURE,ROMANCE",
                                "SPRING,SUMMER,AUTUMN",
                                "Schengen Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival in Rome", "Transfer to hotel.",
                                                                "https://images.unsplash.com/photo-1552832230-c0197dd311b5?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Ancient Rome", "Colosseum and Forum.",
                                                                "https://images.unsplash.com/photo-1552832230-c0197dd311b5?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Vatican City", "St. Peter's Basilica.",
                                                                "https://images.unsplash.com/photo-1531572753322-ad063cecc140?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Train to Venice", "High-speed train.",
                                                                "https://images.unsplash.com/photo-1514890547357-a9ee288728e0?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Venice Canals", "Gondola ride.",
                                                                "https://images.unsplash.com/photo-1523906834658-6e24ef2386f9?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Murano & Burano", "Island tour.",
                                                                "https://images.unsplash.com/photo-1516483638261-f4dbaf036963?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(7, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1529260830199-42c42dda5f3d?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-pizza-slice", "Italian Food"),
                                                createFeature("fa-landmark", "History")),
                                Arrays.asList("https://images.unsplash.com/photo-1529260830199-42c42dda5f3d?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1552832230-c0197dd311b5?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1523906834658-6e24ef2386f9?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1516483638261-f4dbaf036963?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "ITA Airways", 55000.0, "9h 00m", "09:00 AM",
                                                                "06:00 PM"),
                                                createTransport("FLIGHT", "Lufthansa", 58000.0, "10h 00m", "11:00 AM",
                                                                "09:00 PM")));

                // 14. Greece (Santorini)
                createPackage(
                                "Greece Santorini",
                                "Athens, Greece",
                                "INTERNATIONAL",
                                "https://images.unsplash.com/photo-1570077188670-e3a8d69ac5ff?q=80&w=2000&auto=format&fit=crop",
                                6,
                                1800.0,
                                "$",
                                "Blue and White Paradise.",
                                "Acropolis in Athens and the stunning sunsets of Santorini. Greece is the cradle of Western civilization and a land of stunning islands. Visit the ancient Acropolis in Athens and marvel at the Parthenon. Then, travel to Santorini, famous for its blue-domed churches and breathtaking sunsets. Relax on the black sand beaches, explore the volcanic caldera, and enjoy delicious Greek cuisine. This tour is a perfect blend of history and island relaxation.",
                                "HONEYMOON,HISTORY,BEACH",
                                "SUMMER,SPRING",
                                "Schengen Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival in Athens", "Acropolis view.",
                                                                "https://images.unsplash.com/photo-1560703650-ef3e0f254ae0?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Athens Tour", "Parthenon and Plaka.",
                                                                "https://images.unsplash.com/photo-1555993539-1732b0258235?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Ferry to Santorini", "Aegean Sea cruise.",
                                                                "https://images.unsplash.com/photo-1570077188670-e3a8d69ac5ff?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Oia Sunset", "Famous sunset view.",
                                                                "https://images.unsplash.com/photo-1533105079780-92b9be482077?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Volcano Tour", "Hot springs and caldera.",
                                                                "https://images.unsplash.com/photo-1601581875309-fafbf2d3ed92?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1570077188670-e3a8d69ac5ff?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-sun", "Sunsets"),
                                                createFeature("fa-ship", "Island Ferry")),
                                Arrays.asList("https://images.unsplash.com/photo-1570077188670-e3a8d69ac5ff?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1560703650-ef3e0f254ae0?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1533105079780-92b9be482077?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1601581875309-fafbf2d3ed92?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Aegean Airlines", 50000.0, "10h 00m",
                                                                "08:00 AM", "06:00 PM"),
                                                createTransport("FLIGHT", "Turkish Airlines", 48000.0, "11h 00m",
                                                                "10:00 AM", "09:00 PM")));

                // 15. Australia (Sydney)
                createPackage(
                                "Australia Down Under",
                                "Sydney, Australia",
                                "INTERNATIONAL",
                                "https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9?q=80&w=2000&auto=format&fit=crop",
                                7,
                                2800.0,
                                "$",
                                "Sydney and the Reef.",
                                "Opera House, Bondi Beach, and the Great Barrier Reef.",
                                "ADVENTURE,NATURE,CITY",
                                "SUMMER,SPRING",
                                "Visa Required",
                                Arrays.asList(
                                                createDay(1, "Arrival in Sydney", "Harbour Bridge view.",
                                                                "https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(2, "Sydney Opera House", "Guided tour.",
                                                                "https://images.unsplash.com/photo-1624138784181-dc7f5b75e52e?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(3, "Bondi Beach", "Surf and sun.",
                                                                "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(4, "Blue Mountains", "Day trip.",
                                                                "https://images.unsplash.com/photo-1540448051910-09cfadd5df61?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(5, "Fly to Cairns", "Gateway to Reef.",
                                                                "https://images.unsplash.com/photo-1583248369069-9d91f1640fe6?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(6, "Great Barrier Reef", "Snorkeling trip.",
                                                                "https://images.unsplash.com/photo-1582967788606-a171f1080cae?q=80&w=2000&auto=format&fit=crop"),
                                                createDay(7, "Departure", "Transfer to airport.",
                                                                "https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9?q=80&w=2000&auto=format&fit=crop")),
                                Arrays.asList(createFeature("fa-water", "Great Barrier Reef"),
                                                createFeature("fa-landmark", "Opera House")),
                                Arrays.asList("https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1624138784181-dc7f5b75e52e?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=2000&auto=format&fit=crop",
                                                "https://images.unsplash.com/photo-1582967788606-a171f1080cae?q=80&w=2000&auto=format&fit=crop"),
                                Arrays.asList(
                                                createTransport("FLIGHT", "Qantas", 90000.0, "14h 00m", "10:00 PM",
                                                                "12:00 PM"),
                                                createTransport("FLIGHT", "Singapore Airlines", 85000.0, "15h 00m",
                                                                "11:00 PM", "02:00 PM")));
        }

        private void initializeHotels() {
                // --- DOMESTIC HOTELS ---

                // Rajasthan (Jaipur)
                createHotel("Rambagh Palace", "Jaipur, Rajasthan", "DOMESTIC", "India", "Jaipur",
                                "https://images.unsplash.com/photo-1585146617306-66f966248e84?q=80&w=2000&auto=format&fit=crop",
                                30000.0, "₹", 4.9, 5, "Royal Suite", "The Jewel of Jaipur.", "Pool,Spa,Fine Dining");
                createHotel("Hotel Kalyan", "Jaipur, Rajasthan", "DOMESTIC", "India", "Jaipur",
                                "https://images.unsplash.com/photo-1566073771259-6a8506099945?q=80&w=2000&auto=format&fit=crop",
                                3000.0, "₹", 4.0, 3, "Deluxe Room", "Comfortable stay near railway station.",
                                "Wifi,Restaurant,Rooftop");

                // Kerala (Munnar)
                createHotel("Blanket Hotel & Spa", "Munnar, Kerala", "DOMESTIC", "India", "Munnar",
                                "https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?q=80&w=2000&auto=format&fit=crop",
                                12000.0, "₹", 4.8, 5, "Valley View", "Luxury amidst tea gardens.", "Spa,Pool,Trekking");
                createHotel("Munnar Inn", "Munnar, Kerala", "DOMESTIC", "India", "Munnar",
                                "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?q=80&w=2000&auto=format&fit=crop",
                                2500.0, "₹", 3.8, 3, "Standard Room", "Heart of the town.", "Wifi,Parking");

                // Goa
                createHotel("Taj Exotica", "Benaulim, Goa", "DOMESTIC", "India", "Goa",
                                "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?q=80&w=2000&auto=format&fit=crop",
                                20000.0, "₹", 4.9, 5, "Villa with Pool", "Mediterranean-style resort.",
                                "Beach Access,Spa,Golf");
                createHotel("Santa Monica Resort", "Calangute, Goa", "DOMESTIC", "India", "Goa",
                                "https://images.unsplash.com/photo-1571896349842-68c2531b2602?q=80&w=2000&auto=format&fit=crop",
                                3500.0, "₹", 4.0, 3, "Pool View", "Near Calangute Beach.", "Pool,Bar,Wifi");

                // Manali
                createHotel("The Himalayan", "Manali, Himachal", "DOMESTIC", "India", "Manali",
                                "https://images.unsplash.com/photo-1542718610-a1d656d77143?q=80&w=2000&auto=format&fit=crop",
                                15000.0, "₹", 4.7, 5, "Castle Room", "Victorian Gothic style castle.",
                                "Pool,Garden,Fireplace");
                createHotel("Hotel Snow View", "Manali, Himachal", "DOMESTIC", "India", "Manali",
                                "https://images.unsplash.com/photo-1586795467534-038f5e98421e?q=80&w=2000&auto=format&fit=crop",
                                2000.0, "₹", 3.9, 3, "Standard Room", "Great views of snow peaks.", "Wifi,Heater");

                // Ladakh (Leh)
                createHotel("The Grand Dragon", "Leh, Ladakh", "DOMESTIC", "India", "Ladakh",
                                "https://images.unsplash.com/photo-1518733057094-95b53143d2a7?q=80&w=2000&auto=format&fit=crop",
                                18000.0, "₹", 4.8, 5, "Premier Room", "Eco-friendly luxury.", "Oxygen,Heating,Garden");
                createHotel("Hotel Shambhala", "Leh, Ladakh", "DOMESTIC", "India", "Ladakh",
                                "https://images.unsplash.com/photo-1590059390492-d5495eb83089?q=80&w=2000&auto=format&fit=crop",
                                4000.0, "₹", 4.1, 3, "Deluxe Room", "Traditional Ladakhi hospitality.",
                                "Garden,Restaurant");

                // Andaman (Port Blair)
                createHotel("Taj Exotica Andaman", "Havelock, Andaman", "DOMESTIC", "India", "Andaman",
                                "https://images.unsplash.com/photo-1573843981267-be1999ff37cd?q=80&w=2000&auto=format&fit=crop",
                                35000.0, "₹", 4.9, 5, "Pool Villa", "Luxury on Radhanagar Beach.", "Beach,Spa,Diving");
                createHotel("SeaShell", "Port Blair, Andaman", "DOMESTIC", "India", "Andaman",
                                "https://images.unsplash.com/photo-1544124499-58912cbddaad?q=80&w=2000&auto=format&fit=crop",
                                6000.0, "₹", 4.2, 4, "Sea View", "Modern amenities with sea view.", "Rooftop,Bar");

                // Kashmir (Srinagar)
                createHotel("The Lalit Grand Palace", "Srinagar, Kashmir", "DOMESTIC", "India", "Srinagar",
                                "https://images.unsplash.com/photo-1566665797739-1674de7a421a?q=80&w=2000&auto=format&fit=crop",
                                22000.0, "₹", 4.8, 5, "Palace Suite", "Heritage property overlooking Dal Lake.",
                                "Gardens,Spa,Heating");
                createHotel("Hotel Heevan", "Srinagar, Kashmir", "DOMESTIC", "India", "Srinagar",
                                "https://images.unsplash.com/photo-1596436889106-be35e843f974?q=80&w=2000&auto=format&fit=crop",
                                5000.0, "₹", 4.0, 3, "Deluxe Room", "Near Dal Lake.", "Wifi,Heating");

                // Delhi
                createHotel("The Imperial", "Janpath, Delhi", "DOMESTIC", "India", "Delhi",
                                "https://images.unsplash.com/photo-1590381105924-c72589b9ef3f?q=80&w=2000&auto=format&fit=crop",
                                18000.0, "₹", 4.7, 5, "Heritage Room", "Colonial elegance.", "Spa,Pool,Fine Dining");
                createHotel("Hotel City Star", "Paharganj, Delhi", "DOMESTIC", "India", "Delhi",
                                "https://images.unsplash.com/photo-1582719508461-905c673771fd?q=80&w=2000&auto=format&fit=crop",
                                2500.0, "₹", 3.8, 3, "Standard Room", "Budget friendly near station.", "Wifi,Gym");

                // Varanasi
                createHotel("Taj Ganges", "Varanasi, UP", "DOMESTIC", "India", "Varanasi",
                                "https://images.unsplash.com/photo-1560185127-6ed189bf02f4?q=80&w=2000&auto=format&fit=crop",
                                12000.0, "₹", 4.6, 5, "Gateway Room", "Oasis of peace.", "Pool,Gardens,Dining");
                createHotel("Hotel Alka", "Varanasi, UP", "DOMESTIC", "India", "Varanasi",
                                "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?q=80&w=2000&auto=format&fit=crop",
                                3000.0, "₹", 4.1, 3, "River View", "Right on the Ghats.", "Terrace,Yoga");

                // Rishikesh
                createHotel("Ananda in the Himalayas", "Rishikesh, Uttarakhand", "DOMESTIC", "India", "Rishikesh",
                                "https://images.unsplash.com/photo-1544161515-4ab6ce6db874?q=80&w=2000&auto=format&fit=crop",
                                45000.0, "₹", 4.9, 5, "Palace View", "World class wellness resort.",
                                "Spa,Yoga,Ayurveda");
                createHotel("Aloha on the Ganges", "Rishikesh, Uttarakhand", "DOMESTIC", "India", "Rishikesh",
                                "https://images.unsplash.com/photo-1571003123894-1f0594d2b5d9?q=80&w=2000&auto=format&fit=crop",
                                6000.0, "₹", 4.3, 4, "Ganges View", "Riverside resort.", "Pool,Spa,Rafting");

                // Ooty
                createHotel("Savoy", "Ooty, Tamil Nadu", "DOMESTIC", "India", "Ooty",
                                "https://images.unsplash.com/photo-1580835845971-a393b73bf378?q=80&w=2000&auto=format&fit=crop",
                                15000.0, "₹", 4.7, 5, "Garden Room", "Colonial charm.", "Gardens,Fireplace,Bar");
                createHotel("Hotel Lakeview", "Ooty, Tamil Nadu", "DOMESTIC", "India", "Ooty",
                                "https://images.unsplash.com/photo-1596436889106-be35e843f974?q=80&w=2000&auto=format&fit=crop",
                                3000.0, "₹", 3.9, 3, "Cottage", "Overlooking the lake.", "Garden,Parking");

                // Darjeeling
                createHotel("Mayfair Darjeeling", "Darjeeling, West Bengal", "DOMESTIC", "India", "Darjeeling",
                                "https://images.unsplash.com/photo-1585146617306-66f966248e84?q=80&w=2000&auto=format&fit=crop",
                                14000.0, "₹", 4.6, 5, "Heritage Room", "Old world charm.", "Spa,Library,Tea Lounge");
                createHotel("Hotel Shangri-La", "Darjeeling, West Bengal", "DOMESTIC", "India", "Darjeeling",
                                "https://images.unsplash.com/photo-1586795467534-038f5e98421e?q=80&w=2000&auto=format&fit=crop",
                                3500.0, "₹", 4.0, 3, "View Room", "Near Mall Road.", "Wifi,Restaurant");

                // --- INTERNATIONAL HOTELS ---

                // Paris
                createHotel("Shangri-La Paris", "Paris, France", "INTERNATIONAL", "France", "Paris",
                                "https://images.unsplash.com/photo-1590490360182-c33d57733427?q=80&w=2000&auto=format&fit=crop",
                                1200.0, "€", 4.9, 5, "Eiffel View", "Palace hotel with Eiffel views.",
                                "Spa,Michelin Star");
                createHotel("Hotel ibis Paris", "Paris, France", "INTERNATIONAL", "France", "Paris",
                                "https://images.unsplash.com/photo-1568495248636-6432b916d4e6?q=80&w=2000&auto=format&fit=crop",
                                150.0, "€", 4.0, 3, "Standard Room", "Central and convenient.", "Wifi,Breakfast");

                // Dubai
                createHotel("Burj Al Arab", "Dubai, UAE", "INTERNATIONAL", "UAE", "Dubai",
                                "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?q=80&w=2000&auto=format&fit=crop",
                                2000.0, "$", 5.0, 7, "Deluxe Suite", "The world's most luxurious hotel.",
                                "Private Beach,Butler,Helipad");
                createHotel("Rove Downtown", "Dubai, UAE", "INTERNATIONAL", "UAE", "Dubai",
                                "https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?q=80&w=2000&auto=format&fit=crop",
                                100.0, "$", 4.5, 3, "Rover Room", "Views of Burj Khalifa.", "Pool,Cinema,Wifi");

                // Zurich
                createHotel("The Dolder Grand", "Zurich, Switzerland", "INTERNATIONAL", "Switzerland", "Zurich",
                                "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?q=80&w=2000&auto=format&fit=crop",
                                800.0, "CHF", 4.8, 5, "Junior Suite", "City resort with art collection.",
                                "Spa,Golf,Fine Dining");
                createHotel("Hotel Adler", "Zurich, Switzerland", "INTERNATIONAL", "Switzerland", "Zurich",
                                "https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?q=80&w=2000&auto=format&fit=crop",
                                200.0, "CHF", 4.2, 3, "Standard Room", "In the heart of Old Town.", "Restaurant,Wifi");

                // Phuket
                createHotel("Sri Panwa", "Phuket, Thailand", "INTERNATIONAL", "Thailand", "Phuket",
                                "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?q=80&w=2000&auto=format&fit=crop",
                                500.0, "$", 4.7, 5, "Pool Villa", "Luxury pool villas.", "Private Beach,Rooftop Bar");
                createHotel("Lub d Phuket", "Phuket, Thailand", "INTERNATIONAL", "Thailand", "Phuket",
                                "https://images.unsplash.com/photo-1571896349842-68c2531b2602?q=80&w=2000&auto=format&fit=crop",
                                50.0, "$", 4.4, 3, "Private Room", "Social hotel on Patong Beach.",
                                "Pool,Bar,Co-working");

                // Bali
                createHotel("Four Seasons Sayan", "Ubud, Bali", "INTERNATIONAL", "Indonesia", "Bali",
                                "https://images.unsplash.com/photo-1537996194471-e657df975ab4?q=80&w=2000&auto=format&fit=crop",
                                800.0, "$", 4.9, 5, "Riverfront Villa", "Immersed in the jungle.", "Yoga,Spa,Rafting");
                createHotel("Kuta Beach Hotel", "Kuta, Bali", "INTERNATIONAL", "Indonesia", "Bali",
                                "https://images.unsplash.com/photo-1571003123894-1f0594d2b5d9?q=80&w=2000&auto=format&fit=crop",
                                60.0, "$", 4.0, 3, "Deluxe Room", "Sunset views.", "Rooftop Pool,Bar");

                // Singapore
                createHotel("Marina Bay Sands", "Singapore", "INTERNATIONAL", "Singapore", "Singapore",
                                "https://images.unsplash.com/photo-1565031491318-a55cc8330c51?q=80&w=2000&auto=format&fit=crop",
                                600.0, "SGD", 4.7, 5, "Club Room", "Iconic infinity pool.", "Casino,Shopping,Views");
                createHotel("Hotel Boss", "Singapore", "INTERNATIONAL", "Singapore", "Singapore",
                                "https://images.unsplash.com/photo-1582719508461-905c673771fd?q=80&w=2000&auto=format&fit=crop",
                                120.0, "SGD", 3.9, 3, "Superior Room", "Good value in city.", "Pool,Gym,Wifi");

                // London
                createHotel("The Ritz London", "London, UK", "INTERNATIONAL", "UK", "London",
                                "https://images.unsplash.com/photo-1566665797739-1674de7a421a?q=80&w=2000&auto=format&fit=crop",
                                800.0, "£", 4.8, 5, "Executive Room", "Quintessential British luxury.",
                                "Afternoon Tea,Concierge");
                createHotel("Premier Inn Waterloo", "London, UK", "INTERNATIONAL", "UK", "London",
                                "https://images.unsplash.com/photo-1596436889106-be35e843f974?q=80&w=2000&auto=format&fit=crop",
                                100.0, "£", 4.3, 3, "Standard Room", "Near London Eye.", "Wifi,Breakfast");

                // New York
                createHotel("The Plaza", "New York, USA", "INTERNATIONAL", "USA", "New York",
                                "https://images.unsplash.com/photo-1590490360182-c33d57733427?q=80&w=2000&auto=format&fit=crop",
                                1000.0, "$", 4.7, 5, "Grand Luxe", "Iconic Central Park hotel.", "Champagne Bar,Spa");
                createHotel("Pod 51", "New York, USA", "INTERNATIONAL", "USA", "New York",
                                "https://images.unsplash.com/photo-1590059390492-d5495eb83089?q=80&w=2000&auto=format&fit=crop",
                                150.0, "$", 4.0, 3, "Queen Pod", "Trendy and affordable.", "Rooftop,Wifi");

                // Maldives
                createHotel("Soneva Jani", "Noonu Atoll, Maldives", "INTERNATIONAL", "Maldives", "Male",
                                "https://images.unsplash.com/photo-1573843981267-be1999ff37cd?q=80&w=2000&auto=format&fit=crop",
                                3000.0, "$", 5.0, 5, "Water Retreat", "Slides into the ocean.",
                                "Cinema,Observatory,Spa");
                createHotel("Arena Beach Hotel", "Maafushi, Maldives", "INTERNATIONAL", "Maldives", "Male",
                                "https://images.unsplash.com/photo-1544124499-58912cbddaad?q=80&w=2000&auto=format&fit=crop",
                                150.0, "$", 4.2, 3, "Sea View", "Local island experience.", "Beach,Excursions");

                // Tokyo
                createHotel("Aman Tokyo", "Tokyo, Japan", "INTERNATIONAL", "Japan", "Tokyo",
                                "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?q=80&w=2000&auto=format&fit=crop",
                                1200.0, "$", 4.9, 5, "Deluxe Room", "Urban sanctuary.", "Spa,Pool,Views");
                createHotel("APA Hotel Shinjuku", "Tokyo, Japan", "INTERNATIONAL", "Japan", "Tokyo",
                                "https://images.unsplash.com/photo-1568495248636-6432b916d4e6?q=80&w=2000&auto=format&fit=crop",
                                100.0, "$", 3.8, 3, "Single Room", "Compact and efficient.", "Wifi,Bath");

                // Hanoi
                createHotel("Sofitel Legend Metropole", "Hanoi, Vietnam", "INTERNATIONAL", "Vietnam", "Hanoi",
                                "https://images.unsplash.com/photo-1566665797739-1674de7a421a?q=80&w=2000&auto=format&fit=crop",
                                300.0, "$", 4.8, 5, "Opera Wing", "French colonial history.", "Pool,French Dining");
                createHotel("Hanoi Pearl Hotel", "Hanoi, Vietnam", "INTERNATIONAL", "Vietnam", "Hanoi",
                                "https://images.unsplash.com/photo-1582719508461-905c673771fd?q=80&w=2000&auto=format&fit=crop",
                                60.0, "$", 4.3, 3, "Deluxe Room", "Near Hoan Kiem Lake.", "Gym,Wifi");

                // Colombo
                createHotel("Galle Face Hotel", "Colombo, Sri Lanka", "INTERNATIONAL", "Sri Lanka", "Colombo",
                                "https://images.unsplash.com/photo-1544124499-58912cbddaad?q=80&w=2000&auto=format&fit=crop",
                                200.0, "$", 4.6, 5, "Ocean View", "Historic colonial hotel.", "Sea View,Pool,Bar");
                createHotel("Cinnamon Red", "Colombo, Sri Lanka", "INTERNATIONAL", "Sri Lanka", "Colombo",
                                "https://images.unsplash.com/photo-1582719508461-905c673771fd?q=80&w=2000&auto=format&fit=crop",
                                80.0, "$", 4.2, 3, "Standard Room", "Modern city hotel.", "Rooftop Pool,Wifi");

                // Rome
                createHotel("Hotel Hassler", "Rome, Italy", "INTERNATIONAL", "Italy", "Rome",
                                "https://images.unsplash.com/photo-1566665797739-1674de7a421a?q=80&w=2000&auto=format&fit=crop",
                                800.0, "€", 4.8, 5, "Grand Deluxe", "Atop the Spanish Steps.", "Michelin Star,Views");
                createHotel("Hotel Sonya", "Rome, Italy", "INTERNATIONAL", "Italy", "Rome",
                                "https://images.unsplash.com/photo-1568495248636-6432b916d4e6?q=80&w=2000&auto=format&fit=crop",
                                120.0, "€", 4.1, 3, "Double Room", "Near Termini Station.", "Wifi,Breakfast");

                // Athens
                createHotel("Hotel Grande Bretagne", "Athens, Greece", "INTERNATIONAL", "Greece", "Athens",
                                "https://images.unsplash.com/photo-1590490360182-c33d57733427?q=80&w=2000&auto=format&fit=crop",
                                600.0, "€", 4.8, 5, "Acropolis View", "Historic luxury.", "Rooftop,Spa,Pool");
                createHotel("Plaka Hotel", "Athens, Greece", "INTERNATIONAL", "Greece", "Athens",
                                "https://images.unsplash.com/photo-1568495248636-6432b916d4e6?q=80&w=2000&auto=format&fit=crop",
                                100.0, "€", 4.3, 3, "Standard Room", "Views of Acropolis.", "Rooftop Bar,Wifi");

                // Sydney
                createHotel("Park Hyatt Sydney", "Sydney, Australia", "INTERNATIONAL", "Australia", "Sydney",
                                "https://images.unsplash.com/photo-1565031491318-a55cc8330c51?q=80&w=2000&auto=format&fit=crop",
                                1000.0, "AUD", 4.9, 5, "Opera View", "Best views in Sydney.", "Pool,Spa,Dining");
                createHotel("YHA Sydney Harbour", "Sydney, Australia", "INTERNATIONAL", "Australia", "Sydney",
                                "https://images.unsplash.com/photo-1590059390492-d5495eb83089?q=80&w=2000&auto=format&fit=crop",
                                80.0, "AUD", 4.5, 3, "Private Room", "Rooftop views of Opera House.",
                                "Rooftop,Wifi,Kitchen");
        }

        private void createPackage(String name, String location, String type, String heroImage, int days, double price,
                        String currency, String shortDesc, String aboutDesc, String travelTypes, String seasons,
                        String visa,
                        List<PackageItineraryDay> itinerary, List<PackageFeature> features, List<String> galleryUrls,
                        List<TransportOption> transportOptions) {
                TravelPackage pkg = new TravelPackage();
                pkg.setName(name);
                pkg.setLocation(location);
                pkg.setPackageType(type);
                pkg.setHeroImageUrl(heroImage);
                pkg.setDurationDays(days);
                pkg.setPrice(price);
                pkg.setStandardPrice(price);
                pkg.setLuxuryPrice(price * 1.5);
                pkg.setPriceCurrency(currency);
                pkg.setShortDescription(shortDesc);
                pkg.setAboutDescription(aboutDesc);
                pkg.setTravelTypes(travelTypes);
                pkg.setSeasons(seasons);
                pkg.setVisaRequirement(visa);

                // Set flags based on travel types
                if (travelTypes.contains("FAMILY"))
                        pkg.setIsFamilyFriendly(true);
                if (travelTypes.contains("COUPLE") || travelTypes.contains("HONEYMOON"))
                        pkg.setIsCoupleFriendly(true);
                if (travelTypes.contains("ADVENTURE"))
                        pkg.setIsAdventure(true);
                if (travelTypes.contains("SPIRITUAL"))
                        pkg.setIsSpiritual(true);
                if (travelTypes.contains("CORPORATE"))
                        pkg.setIsCorporate(true);

                // Save package first to get ID
                TravelPackage savedPkg = packageRepository.save(pkg);

                // Add relationships
                if (itinerary != null) {
                        for (PackageItineraryDay day : itinerary) {
                                day.setTravelPackage(savedPkg);
                        }
                        savedPkg.setItineraryDays(new ArrayList<>(itinerary));
                }

                if (features != null) {
                        for (PackageFeature feature : features) {
                                feature.setTravelPackage(savedPkg);
                        }
                        savedPkg.setFeatures(new ArrayList<>(features));
                }

                if (galleryUrls != null) {
                        List<PackageImage> images = new ArrayList<>();
                        for (int i = 0; i < galleryUrls.size(); i++) {
                                PackageImage img = new PackageImage();
                                img.setTravelPackage(savedPkg);
                                img.setImageUrl(galleryUrls.get(i));
                                img.setDisplayOrder(i);
                                images.add(img);
                        }
                        savedPkg.setGalleryImages(images);
                }

                if (transportOptions != null) {
                        for (TransportOption option : transportOptions) {
                                option.setTravelPackage(savedPkg);

                                // Also create legacy entities for frontend compatibility
                                try {
                                        if ("FLIGHT".equalsIgnoreCase(option.getType())) {
                                                Flight flight = new Flight();
                                                flight.setAirline(option.getProvider());
                                                // Use timestamp to ensure uniqueness
                                                flight.setFlightNumber("FL" + System.currentTimeMillis() + "-"
                                                                + (int) (Math.random() * 1000));
                                                flight.setOrigin("New Delhi"); // Default origin
                                                flight.setDestination(location);
                                                flight.setDepartureTime(option.getDepartureTime());
                                                flight.setArrivalTime(option.getArrivalTime());

                                                // Base Economy Price (Distance based proxy)
                                                double basePrice = option.getPrice();
                                                flight.setPrice(basePrice);
                                                flight.setPriceCurrency("₹");

                                                // Assign Business and First Class prices based on Economy price
                                                // (Distance proxy)
                                                flight.setBusinessPrice(basePrice * 2.5);
                                                flight.setFirstClassPrice(basePrice * 4.0);

                                                // Set Seat Capacities
                                                flight.setTotalSeats(180);
                                                flight.setEconomySeats(140);
                                                flight.setBusinessSeats(30);
                                                flight.setFirstClassSeats(10);

                                                // Set Available Seats (Mock data)
                                                flight.setAvailableSeats("A1,A2,B1,B2,C1,C2"); // Economy available
                                                flight.setBusinessAvailableSeats("J1,J2,K1,K2"); // Business available
                                                flight.setFirstClassAvailableSeats("F1,F2"); // First Class available

                                                flightRepository.save(flight);
                                        } else if ("BUS".equalsIgnoreCase(option.getType())) {
                                                Bus bus = new Bus();
                                                bus.setBusName(option.getProvider());
                                                bus.setBusType("AC Sleeper");
                                                bus.setSource("New Delhi");
                                                bus.setDestination(location);
                                                bus.setDepartureTime(option.getDepartureTime());
                                                bus.setArrivalTime(option.getArrivalTime());
                                                bus.setTicketPrice(option.getPrice());
                                                bus.setPriceCurrency("₹");
                                                bus.setTotalSeats(40);
                                                busRepository.save(bus);
                                        } else if ("TRAIN".equalsIgnoreCase(option.getType())) {
                                                Train train = new Train();
                                                train.setTrainName(option.getProvider());
                                                train.setTrainNumber("TR" + System.currentTimeMillis() + "-"
                                                                + (int) (Math.random() * 1000));
                                                train.setSource("New Delhi");
                                                train.setDestination(location);
                                                train.setDepartureTime(option.getDepartureTime());
                                                train.setArrivalTime(option.getArrivalTime());
                                                train.setSleeperPrice(option.getPrice());
                                                train.setAcPrice(option.getPrice() * 1.5);
                                                train.setPriceCurrency("₹");
                                                trainRepository.save(train);
                                        }
                                } catch (Exception e) {
                                        System.err.println("Error creating legacy transport: " + e.getMessage());
                                }
                        }
                        savedPkg.setTransportOptions(new ArrayList<>(transportOptions));
                }

                packageRepository.save(savedPkg);
        }

        private PackageItineraryDay createDay(int dayNum, String title, String desc, String imgUrl) {
                PackageItineraryDay day = new PackageItineraryDay();
                day.setDayNumber(dayNum);
                day.setTitle(title);
                day.setDescription(desc);
                day.setImageUrl(imgUrl);
                return day;
        }

        private PackageFeature createFeature(String icon, String text) {
                PackageFeature feature = new PackageFeature();
                feature.setIcon(icon);
                feature.setText(text);
                return feature;
        }

        private void createHotel(String name, String location, String type, String country, String city,
                        String heroImage,
                        double price, String currency, double rating, int starRating, String roomType, String desc,
                        String baseAmenities) {
                Hotel hotel = new Hotel();
                hotel.setName(name);
                hotel.setLocation(location);
                hotel.setHotelType(type);
                hotel.setCountry(country);
                hotel.setCity(city);
                hotel.setHeroImageUrl(heroImage);
                hotel.setPricePerNight(price);
                hotel.setPriceCurrency(currency);
                hotel.setRating(rating);
                hotel.setStarRating(starRating);
                hotel.setRoomType(roomType);
                hotel.setDescription(desc);

                // Generate diverse amenities based on star rating
                List<String> amenitiesList = new ArrayList<>();
                if (baseAmenities != null && !baseAmenities.isEmpty()) {
                        String[] base = baseAmenities.split(",");
                        for (String s : base)
                                amenitiesList.add(s.trim());
                }

                // Add extra amenities based on rating
                if (starRating >= 5) {
                        amenitiesList.add("Valet Parking");
                        amenitiesList.add("Concierge Service");
                        amenitiesList.add("24/7 Room Service");
                        if (Math.random() > 0.5)
                                amenitiesList.add("Rooftop Bar");
                        if (Math.random() > 0.5)
                                amenitiesList.add("Jacuzzi");
                        if (Math.random() > 0.5)
                                amenitiesList.add("Infinity Pool");
                } else if (starRating == 4) {
                        amenitiesList.add("Fitness Center");
                        amenitiesList.add("Coffee Shop");
                        if (Math.random() > 0.5)
                                amenitiesList.add("Airport Shuttle");
                        if (Math.random() > 0.5)
                                amenitiesList.add("Lounge");
                } else {
                        amenitiesList.add("Daily Housekeeping");
                        amenitiesList.add("Luggage Storage");
                        if (Math.random() > 0.5)
                                amenitiesList.add("Wake-up Service");
                }
                // Deduplicate
                List<String> uniqueAmenities = new ArrayList<>(new HashSet<>(amenitiesList));
                hotel.setAmenities(String.join(",", uniqueAmenities));

                // Add Gallery Images
                List<HotelImage> gallery = new ArrayList<>();

                // 1. Add Hero Image
                HotelImage heroImg = new HotelImage(heroImage, 1);
                heroImg.setHotel(hotel);
                gallery.add(heroImg);

                // 2. Add Random Extra Images
                String[] extraImages = {
                                "https://images.unsplash.com/photo-1611892440504-42a792e24d32?q=80&w=2000&auto=format&fit=crop", // Bedroom
                                "https://images.unsplash.com/photo-1584622650111-993a426fbf0a?q=80&w=2000&auto=format&fit=crop", // Bathroom
                                "https://images.unsplash.com/photo-1576013551627-0cc20b96c2a7?q=80&w=2000&auto=format&fit=crop", // Pool
                                "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?q=80&w=2000&auto=format&fit=crop", // Dining
                                "https://images.unsplash.com/photo-1566665797739-1674de7a421a?q=80&w=2000&auto=format&fit=crop", // Bedroom
                                                                                                                                 // 2
                                "https://images.unsplash.com/photo-1552321554-5fefe8c9ef14?q=80&w=2000&auto=format&fit=crop", // Bathroom
                                                                                                                              // 2
                                "https://images.unsplash.com/photo-1572331165267-854da2b00cc6?q=80&w=2000&auto=format&fit=crop", // Pool
                                                                                                                                 // 2
                                "https://images.unsplash.com/photo-1559339352-11d035aa65de?q=80&w=2000&auto=format&fit=crop" // Dining
                                                                                                                             // 2
                };

                int numExtras = 3 + (int) (Math.random() * 3); // 3 to 5 extra images
                for (int i = 0; i < numExtras; i++) {
                        String img = extraImages[(int) (Math.random() * extraImages.length)];
                        // Simple check to avoid adding hero image again if it happens to match
                        // (unlikely but good practice)
                        if (!img.equals(heroImage)) {
                                // Check if already added to gallery
                                boolean exists = false;
                                for (HotelImage hi : gallery) {
                                        if (hi.getImageUrl().equals(img)) {
                                                exists = true;
                                                break;
                                        }
                                }
                                if (!exists) {
                                        HotelImage hi = new HotelImage(img, i + 2);
                                        hi.setHotel(hotel);
                                        gallery.add(hi);
                                }
                        }
                }
                hotel.setGalleryImages(gallery);

                hotel.setCheckInTime("12:00 PM");
                hotel.setCheckOutTime("11:00 AM");
                hotel.setContactPhone("7337250724");
                hotel.setContactEmail("ravindrareddy.t1234@gmil.com");
                hotel.setAddress(name + ", " + location);
                hotelRepository.save(hotel);
        }

        private TransportOption createTransport(String type, String provider, double price, String duration,
                        String depTime, String arrTime) {
                return new TransportOption(type, provider, price, duration, depTime, arrTime);
        }

        private void fixExistingFlightPrices() {
                List<Flight> flights = flightRepository.findAll();
                for (Flight flight : flights) {
                        boolean updated = false;
                        if (flight.getPrice() != null) {
                                if (flight.getBusinessPrice() == null || flight.getBusinessPrice() == 0) {
                                        flight.setBusinessPrice(flight.getPrice() * 2.5);
                                        updated = true;
                                }
                                if (flight.getFirstClassPrice() == null || flight.getFirstClassPrice() == 0) {
                                        flight.setFirstClassPrice(flight.getPrice() * 4.0);
                                        updated = true;
                                }
                                // Also fix seats if missing
                                if (flight.getEconomySeats() == null || flight.getEconomySeats() == 0) {
                                        flight.setEconomySeats(140);
                                        flight.setTotalSeats(180);
                                        updated = true;
                                }
                                if (flight.getBusinessSeats() == null || flight.getBusinessSeats() == 0)
                                        flight.setBusinessSeats(30);
                                if (flight.getFirstClassSeats() == null || flight.getFirstClassSeats() == 0)
                                        flight.setFirstClassSeats(10);

                                if (updated) {
                                        flightRepository.save(flight);
                                }
                        }
                }
                System.out.println("Fixed prices for existing flights.");
        }
}
