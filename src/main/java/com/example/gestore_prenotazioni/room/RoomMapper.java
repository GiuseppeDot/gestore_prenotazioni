package com.example.gestore_prenotazioni.room;

import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public Room toEntity(RoomRequestDTO DTO) {
        Room room = new Room();
        room.setRoomNumber(DTO.getRoomNumber());
        room.setType(DTO.getType());
        room.setPrice(DTO.getPrice());
        room.setAvailable(DTO.isAvailable());
        room.setImageUrl(DTO.getImageUrl());
        return room;
    }
}
