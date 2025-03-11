package com.example.gestore_prenotazioni.booking;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByAppUserId(Long appUserId);

    Page<Booking> findByAppUserId(Long appUserId, Pageable pageable);

    //Filtro di date per check-in e check-out
    @Query("SELECT b FROM Booking b WHERE b.appUser.id = :appUserId AND " +
            "(:checkInDate IS NULL OR b.checkInDate >= :checkInDate) AND " +
            "(:checkOutDate IS NULL OR b.checkOutDate <= :checkOutDate)")
    Page<Booking> findByAppUserIdAndDates(
            @Param("appUserId") Long appUserId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            Pageable pageable
    );
}
