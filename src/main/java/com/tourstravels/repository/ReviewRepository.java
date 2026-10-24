package com.tourstravels.repository;
import com.tourstravels.model.Review; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface ReviewRepository extends JpaRepository<Review,Long>{ List<Review> findByApprovedTrueOrderByCreatedAtDesc(); }
