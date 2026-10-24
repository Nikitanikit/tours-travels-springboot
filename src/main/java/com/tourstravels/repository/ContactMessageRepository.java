package com.tourstravels.repository;
import com.tourstravels.model.ContactMessage; import org.springframework.data.jpa.repository.JpaRepository;
public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long>{ long countByHandledFalse(); }
