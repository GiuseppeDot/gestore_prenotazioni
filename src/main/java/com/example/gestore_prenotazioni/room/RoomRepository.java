package com.example.gestore_prenotazioni.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE " + // r viene sostituito con la classe Room
            "(:type IS NULL OR r.type = :type) AND " + // :type viene sostituito con il valore passato come parametro
            "(:minPrice IS NULL OR r.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR r.price <= :maxPrice) AND " +
            "(:isAvailable IS NULL OR r.isAvailable = :isAvailable)")
    Page<Room> searchRooms(
            @Param("type") String type,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("isAvailable") Boolean isAvailable,
            Pageable pageable
    );
    List<Room> findByIsAvailableTrue();
    // questo codice serve per la paginazione, cioè per limitare il numero di elementi per pagina
    Page<Room> findAll(Pageable pageable); // Paginazione
}
