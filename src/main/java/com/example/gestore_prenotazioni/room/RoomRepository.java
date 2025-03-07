package com.example.gestore_prenotazioni.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByIsAvailableTrue();
    // questo codice serve per la paginazione, cioè per limitare il numero di elementi per pagina
    Page<Room> findAll(Pageable pageable); // Paginazione
}
