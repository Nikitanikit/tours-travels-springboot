package com.tourstravels.repository;
import com.tourstravels.model.*; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface BookingRepository extends JpaRepository<Booking,Long>{ List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId); List<Booking> findByStatusOrderByCreatedAtDesc(BookingStatus status); long countByStatus(BookingStatus status); }
