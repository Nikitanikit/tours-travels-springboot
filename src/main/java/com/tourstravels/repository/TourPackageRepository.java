package com.tourstravels.repository;
import com.tourstravels.model.TourPackage; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface TourPackageRepository extends JpaRepository<TourPackage,Long>{ List<TourPackage> findByActiveTrueOrderByFeaturedDesc(); List<TourPackage> findByFeaturedTrueAndActiveTrue(); List<TourPackage> findByTitleContainingIgnoreCase(String title); }
