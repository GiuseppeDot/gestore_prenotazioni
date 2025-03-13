package com.example.gestore_prenotazioni.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// endpoint per ottenere tutte le stanze
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

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