package com.cs.coding.repository;

import com.cs.coding.model.EventDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDetailRepository extends JpaRepository<EventDetail, String> {
}
