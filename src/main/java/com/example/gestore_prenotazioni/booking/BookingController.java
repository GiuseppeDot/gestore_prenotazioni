package com.example.gestore_prenotazioni.booking;

import com.example.gestore_prenotazioni.auth.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking createBooking(
            @RequestParam Long appUserId,
            @RequestParam Long roomId,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate
    ) {
        return bookingService.createBooking(appUserId, roomId, LocalDate.parse(checkInDate), LocalDate.parse(checkOutDate));
    }

    @GetMapping("/user/{appUserId}")
    public List<Booking> getBookingsByAppUser(@PathVariable Long appUserId) {
        return bookingService.getBookingsByAppUserId(appUserId);
    }

    @DeleteMapping("/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }
}
