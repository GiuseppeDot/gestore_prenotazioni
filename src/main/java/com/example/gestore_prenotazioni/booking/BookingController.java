package com.example.gestore_prenotazioni.booking;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/user/{appUserId}/page")
    public Page<Booking> getBookingByAppUser(
            @PathVariable Long appUserId,
            @RequestParam(defaultValue = "0") int page, // serve per la paginazione di default
            @RequestParam(defaultValue = "10") int size, // serve per limitare il numero di elementi per pagina
            @RequestParam(required = false) String checkInDate, // parametro opzionale per la data di check-in
            @RequestParam(required = false) String checkOutDate, // parametro opzionale per la data di check-out
            @RequestParam(defaultValue = "checkInDate") String sort
    ) {
        LocalDate parsedCheckIndate = checkInDate != null ? LocalDate.parse(checkInDate) : null;
        LocalDate parsedCheckOutDate = checkOutDate != null ? LocalDate.parse(checkOutDate) : null;

        String[] sortParams = sort.split(",");
        String sortFilled = sortParams[0];
        String sortDirection = sortParams.length > 1 ? sortParams[1] : "asc";

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortFilled)

        );

        return bookingService.getBookingByAppUserIdAndDates(
                appUserId,
                parsedCheckIndate,
                parsedCheckOutDate,
                pageable
        );
    }


    @DeleteMapping("/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }
}
