package com.example.gestore_prenotazioni.booking;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Booking Controller", description = "Gestione delle prenotazioni dell'hotel")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @Operation(
            summary = "Crea una nuova prenotazione",
            description = "Crea una prenotazione per un utente e una camera specifici."
    )
    @ApiResponse(responseCode = "200", description = "Prenotazione creata con successo")
    @ApiResponse(responseCode = "400", description = "Parametri non validi")
    @ApiResponse(responseCode = "404", description = "Utente o camera non trovata")
    public Booking createBooking(
            @Parameter(description = "ID dell'utente", required = true) @RequestParam Long appUserId,
            @Parameter(description = "ID della camera", required = true) @RequestParam Long roomId,
            @Parameter(description = "Data di check-in (formato: yyyy-MM-dd)", required = true) @RequestParam String checkInDate,
            @Parameter(description = "Data di check-out (formato: yyyy-MM-dd)", required = true) @RequestParam String checkOutDate
    ) {
        return bookingService.createBooking(appUserId, roomId, LocalDate.parse(checkInDate), LocalDate.parse(checkOutDate));
    }

    @GetMapping("/user/{appUserId}")
    @Operation(
            summary = "Ottieni le prenotazioni di un utente",
            description = "Restituisce tutte le prenotazioni di un utente specifico."
    )
    @ApiResponse(responseCode = "200", description = "Prenotazioni trovate")
    @ApiResponse(responseCode = "404", description = "Utente non trovato")
    public List<Booking> getBookingsByAppUser(
            @Parameter(description = "ID dell'utente", required = true) @PathVariable Long appUserId
    ) {
        return bookingService.getBookingsByAppUserId(appUserId);
    }

    @GetMapping("/user/{appUserId}/page")
    @Operation(
            summary = "Ottieni le prenotazioni di un utente con paginazione",
            description = "Restituisce le prenotazioni di un utente con paginazione e filtri opzionali."
    )
    @ApiResponse(responseCode = "200", description = "Prenotazioni trovate")
    @ApiResponse(responseCode = "404", description = "Utente non trovato")
    public Page<Booking> getBookingByAppUser(
            @Parameter(description = "ID dell'utente", required = true) @PathVariable Long appUserId,
            @Parameter(description = "Numero della pagina (default: 0)", required = false) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Numero di elementi per pagina (default: 10)", required = false) @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Data di check-in (opzionale, formato: yyyy-MM-dd)", required = false) @RequestParam(required = false) String checkInDate,
            @Parameter(description = "Data di check-out (opzionale, formato: yyyy-MM-dd)", required = false) @RequestParam(required = false) String checkOutDate,
            @Parameter(description = "Ordinamento (default: checkInDate,asc)", required = false) @RequestParam(defaultValue = "checkInDate,asc") String sort
    ) {
        LocalDate parsedCheckInDate = checkInDate != null ? LocalDate.parse(checkInDate) : null;
        LocalDate parsedCheckOutDate = checkOutDate != null ? LocalDate.parse(checkOutDate) : null;

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        String sortDirection = sortParams.length > 1 ? sortParams[1] : "asc";

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortField)
        );

        return bookingService.getBookingByAppUserIdAndDates(
                appUserId,
                parsedCheckInDate,
                parsedCheckOutDate,
                pageable
        );
    }

    @DeleteMapping("/{bookingId}")
    @Operation(
            summary = "Cancella una prenotazione",
            description = "Cancella una prenotazione specifica."
    )
    @ApiResponse(responseCode = "200", description = "Prenotazione cancellata con successo")
    @ApiResponse(responseCode = "404", description = "Prenotazione non trovata")
    public void cancelBooking(
            @Parameter(description = "ID della prenotazione", required = true) @PathVariable Long bookingId
    ) {
        bookingService.cancelBooking(bookingId);
    }
}
