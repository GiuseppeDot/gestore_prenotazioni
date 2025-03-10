package com.example.gestore_prenotazioni.booking;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
        List<Booking> findByAppUserId(Long AppUserId);
        Page<Booking> findByAppUserId(Long AppUserId, Pageable pageable);
    }
