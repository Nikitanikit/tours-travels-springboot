package com.tourstravels.config;

import com.tourstravels.model.AppUser;
import com.tourstravels.model.Destination;
import com.tourstravels.model.Hotel;
import com.tourstravels.model.Role;
import com.tourstravels.model.TourPackage;
import com.tourstravels.repository.DestinationRepository;
import com.tourstravels.repository.HotelRepository;
import com.tourstravels.repository.TourPackageRepository;
import com.tourstravels.repository.UserRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(
            UserRepository users,
            DestinationRepository destinations,
            TourPackageRepository packages,
            HotelRepository hotels,
            PasswordEncoder encoder,
            @Value("${app.demo-users.enabled:true}") boolean demoUsersEnabled,
            @Value("${travel.admin.bootstrap-email:}") String bootstrapAdminEmail,
            @Value("${travel.admin.bootstrap-password:}") String bootstrapAdminPassword) {
        return args -> {
            if (demoUsersEnabled) {
                createUserIfMissing(users, encoder, "admin@tours.com", "Site Administrator", "ChangeMe123!", Role.ADMIN);
                createUserIfMissing(users, encoder, "traveler@example.com", "Demo Traveler", "Travel123!", Role.USER);
            } else {
                if (bootstrapAdminEmail.isBlank() || bootstrapAdminPassword.isBlank()) {
                    throw new IllegalStateException("Set ADMIN_EMAIL and ADMIN_PASSWORD to create the deployment admin account.");
                }
                createUserIfMissing(users, encoder, bootstrapAdminEmail, "Site Administrator", bootstrapAdminPassword, Role.ADMIN);
            }

            if (destinations.count() == 0) {
                String[][] seedDestinations = {
                    {"Goa", "India", "Beaches, Portuguese heritage and lively coastal evenings.", "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?auto=format&fit=crop&w=1200&q=85", "Beach"},
                    {"Manali", "India", "Pine forests, Himalayan views and mountain adventures.", "https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?auto=format&fit=crop&w=1200&q=85", "Mountain"},
                    {"Kashmir", "India", "Alpine lakes, green valleys and unforgettable scenery.", "https://images.unsplash.com/photo-1595815771614-ade9d652a65d?auto=format&fit=crop&w=1200&q=85", "Nature"},
                    {"Kerala", "India", "Quiet backwaters, tropical greenery and rich culture.", "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?auto=format&fit=crop&w=1200&q=85", "Nature"},
                    {"Jaipur", "India", "Historic forts, colorful markets and royal architecture.", "https://images.unsplash.com/photo-1599661046827-dacff0c0f09a?auto=format&fit=crop&w=1200&q=85", "Culture"},
                    {"Paris", "France", "Iconic landmarks, museums and charming city streets.", "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=1200&q=85", "International"}
                };
                for (String[] item : seedDestinations) {
                    Destination destination = new Destination();
                    destination.setName(item[0]);
                    destination.setCountry(item[1]);
                    destination.setDescription(item[2]);
                    destination.setImageUrl(item[3]);
                    destination.setCategory(item[4]);
                    destinations.save(destination);
                }
            }

            if (packages.count() == 0) {
                addPackage(packages, destinations, "Goa Beach Getaway", "Goa", "Sunset beaches, local cuisine and relaxed coastal days.", "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?auto=format&fit=crop&w=1200&q=85", "12999", 4, 3, "Beach");
                addPackage(packages, destinations, "Manali Mountain Escape", "Manali", "A scenic Himalayan break with guided local sightseeing.", "https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?auto=format&fit=crop&w=1200&q=85", "14999", 5, 4, "Adventure");
                addPackage(packages, destinations, "Kashmir Paradise Tour", "Kashmir", "Lakes, mountain views and classic valley experiences.", "https://images.unsplash.com/photo-1595815771614-ade9d652a65d?auto=format&fit=crop&w=1200&q=85", "16999", 7, 6, "Nature");
                addPackage(packages, destinations, "Kerala Backwater Retreat", "Kerala", "Tropical backwaters, heritage stays and peaceful nature.", "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?auto=format&fit=crop&w=1200&q=85", "13999", 5, 4, "Nature");
                addPackage(packages, destinations, "Jaipur Heritage Trail", "Jaipur", "Discover royal forts, palaces and colorful bazaars.", "https://images.unsplash.com/photo-1599661046827-dacff0c0f09a?auto=format&fit=crop&w=1200&q=85", "11999", 3, 2, "Culture");
            }

            if (hotels.count() == 0) {
                addHotel(hotels, "The Himalayan Resort", "Manali, India", "Comfortable mountain-view stay with warm hospitality.", "https://images.unsplash.com/photo-1518732714860-b62714ce0c59?auto=format&fit=crop&w=1200&q=85", "8999");
                addHotel(hotels, "Palm Shore Retreat", "Goa, India", "A relaxed coastal stay close to the beach.", "https://images.unsplash.com/photo-1571896349842-33c89424de2d?auto=format&fit=crop&w=1200&q=85", "12999");
                addHotel(hotels, "Lakeview Heritage Stay", "Srinagar, India", "A scenic stay with calm lake and mountain views.", "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?auto=format&fit=crop&w=1200&q=85", "10999");
            }
        };
    }

    private static void createUserIfMissing(
            UserRepository users,
            PasswordEncoder encoder,
            String email,
            String fullName,
            String password,
            Role role) {
        if (!users.existsByEmail(email)) {
            AppUser user = new AppUser();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(encoder.encode(password));
            user.setRole(role);
            users.save(user);
        }
    }

    private static void addPackage(
            TourPackageRepository repo,
            DestinationRepository destinationRepo,
            String title,
            String destinationName,
            String description,
            String image,
            String price,
            int days,
            int nights,
            String category) {
        TourPackage tourPackage = new TourPackage();
        tourPackage.setTitle(title);
        tourPackage.setDestination(destinationRepo.findByNameContainingIgnoreCaseOrCountryContainingIgnoreCase(destinationName, destinationName)
                .stream()
                .filter(destination -> destination.getName().equalsIgnoreCase(destinationName))
                .findFirst()
                .orElse(null));
        tourPackage.setDescription(description);
        tourPackage.setImageUrl(image);
        tourPackage.setPrice(new BigDecimal(price));
        tourPackage.setDurationDays(days);
        tourPackage.setDurationNights(nights);
        tourPackage.setCategory(category);
        repo.save(tourPackage);
    }

    private static void addHotel(
            HotelRepository repo,
            String name,
            String location,
            String description,
            String image,
            String price) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setLocation(location);
        hotel.setDescription(description);
        hotel.setImageUrl(image);
        hotel.setPricePerNight(new BigDecimal(price));
        repo.save(hotel);
    }
}
