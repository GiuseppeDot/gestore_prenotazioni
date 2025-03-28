package com.example.gestore_prenotazioni.booking;

import com.example.gestore_prenotazioni.auth.AppUser;
import com.example.gestore_prenotazioni.auth.AppUserRepository;
import com.example.gestore_prenotazioni.room.Room;
import com.example.gestore_prenotazioni.room.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    // Iniezione delle dipendenze
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoomRepository roomRepository;

    // Crea una nuova prenotazione
    public Booking createBooking(Long userId, Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        // Verifica che l'utente esista
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        // Verifica che la camera esista
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Camera non trovata"));

        // Verifica che la camera sia disponibile
        if (!room.isAvailable()) {
            throw new EntityNotFoundException("Camera non disponibile");
        }

        // Crea la prenotazione
        Booking booking = new Booking();
        booking.setAppUser(appUser);
        booking.setRoom(room);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);

        // Imposta la camera come non disponibile
        room.setAvailable(false);
        roomRepository.save(room);

        // Salva la prenotazione nel database
        return bookingRepository.save(booking);
    }

    // Ottiene le prenotazioni di un utente
    public List<Booking> getBookingsByAppUserId(Long appUserId) {
        return bookingRepository.findByAppUserId(appUserId);
    }

  // Ottiene le prenotazioni di un utente con paginazione
    public Page<Booking> getBookingsByAppUserId(Long appUserId, Pageable pageable) {
        return bookingRepository.findByAppUserId(appUserId, pageable);
    }

 // Ottiene le prenotazioni di un utente con paginazione e filtro di date
    public Page<Booking> getBookingByAppUserIdAndDates(
            Long appUserId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            Pageable pageable
    ) {
        return bookingRepository.findByAppUserIdAndDates(appUserId, checkInDate, checkOutDate, pageable);
    }



    // Cancella una prenotazione
    public void cancelBooking(Long bookingId) {
        // Trova la prenotazione
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));

        // Rendi la camera nuovamente disponibile
        Room room = booking.getRoom();
        room.setAvailable(true);
        roomRepository.save(room);

        // Cancella la prenotazione
        bookingRepository.delete(booking);
    }
}