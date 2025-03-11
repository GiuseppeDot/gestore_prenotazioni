package com.example.gestore_prenotazioni.booking;

import com.example.gestore_prenotazioni.auth.AppUser;
import com.example.gestore_prenotazioni.auth.AppUserRepository;
import com.example.gestore_prenotazioni.booking.Booking;
import com.example.gestore_prenotazioni.booking.BookingRepository;
import com.example.gestore_prenotazioni.room.Room;
import com.example.gestore_prenotazioni.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceTest {

    // autowired serve per iniettare le dipendenze
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    // metodo per creare una prenotazione
    public Booking createBooking(
            Long userId,
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate
    ) {
        AppUser AppUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Camera non trovata"));
        // Controlla se la camera è disponibile
        if (!room.isAvailable()) {
            throw new RuntimeException("Camera non disponibile");
        }

        Booking booking = new Booking();
        booking.setAppUser(AppUser);
        booking.setRoom(room);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);

        room.setAvailable(false); // Imposta la camera come non disponibile
        roomRepository.save(room);

        return bookingRepository.save(booking);
    }

    // metodo per ottenere le prenotazioni di un utente
    public List<Booking> getBookingsByAppUserId(Long appUserId) {
        return bookingRepository.findByAppUserId(appUserId);
    }

    // metodo per ottenere le prenotazioni di un utente con paginazione
    public Page<Booking> getBookingsByAppUserId(Long appUserId, Pageable pageable) {
        return bookingRepository.findByAppUserId(appUserId, pageable);
    }

    public Page<Booking> getBookingByAppUserIdAndDates(
            Long appUserId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            Pageable pageable
    ) {
        return bookingRepository.findByAppUserIdAndDates(appUserId, checkInDate, checkOutDate, pageable);
    }

    // metodo per cancellare una prenotazione
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));

        Room room = booking.getRoom();
        room.setAvailable(true); // Rendi la camera nuovamente disponibile
        roomRepository.save(room);

        bookingRepository.delete(booking);
    }
}
