package com.example.gestore_prenotazioni.room;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// endpoint per ottenere tutte le stanze
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    //devo creare una nuova stanza
    @PostMapping("/room")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Crea una nuova stanza", description = "Crea una nuova stanza con i dati forniti.")
    @ApiResponse(responseCode = "201", description = "Stanza creata con successo")
    @ApiResponse(responseCode = "400", description = "Parametri non validi")
    public ResponseEntity<Room> createRoom(
            @Parameter(description = "Dati della stanza", required = true)
            @Valid @RequestBody RoomRequestDTO roomDTO
    ) {
        Room newRoom = roomService.createRoom(roomDTO);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }


    @GetMapping("/search")
    public Page<Room> searchRooms(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean isAvailable,
            Pageable pageable
    ) {
        return roomService.searchRooms(type, minPrice, maxPrice, isAvailable, pageable);
    }

    @GetMapping
    public Page<Room> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return roomRepository.findAll(PageRequest.of(page, size));
    }
}