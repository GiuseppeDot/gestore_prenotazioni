package com.example.gestore_prenotazioni.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    public Room createRoom(RoomRequestDTO roomDTO) {
        Room room = roomMapper.toEntity(roomDTO);
        return roomRepository.save(room);
    }
    public Page<Room> searchRooms(
            String type,
            Double minPrice,
            Double maxPrice,
            Boolean isAvailable,
            Pageable pageable
    ) {
        return roomRepository.searchRooms(type, minPrice, maxPrice, isAvailable, pageable);
    }

    private final Path rootLocation = Paths.get("uploads");

    public String saveImage(Long roomId, MultipartFile file) {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            String fileName = roomId + "_" + file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(fileName);
            Files.copy(file.getInputStream(), destinationFile);

            return "/uploads/" + fileName; // URL immagine
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio dell'immagine", e);
        }
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoomImage(Long roomId, String imageUrl) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Camera non trovata"));
        room.setImageUrl(imageUrl);
        return roomRepository.save(room);
    }
}