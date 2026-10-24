package com.tourstravels.repository;
import com.tourstravels.model.Destination; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface DestinationRepository extends JpaRepository<Destination,Long>{ List<Destination> findByActiveTrueOrderByNameAsc(); List<Destination> findByFeaturedTrueAndActiveTrue(); List<Destination> findByNameContainingIgnoreCaseOrCountryContainingIgnoreCase(String name,String country); }
