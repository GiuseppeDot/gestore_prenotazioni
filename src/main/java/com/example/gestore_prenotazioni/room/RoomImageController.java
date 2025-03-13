package com.example.gestore_prenotazioni.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/api/rooms")
public class RoomImageController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/{roomId}/upload-images")
    public Room uploadImage(
            @PathVariable Long roomId,
            @PathVariable("file") MultipartFile file

    ) {
        String imageUrl = roomService.saveImage(roomId, file);
        return roomService.updateRoomImage(roomId, imageUrl);
    }
}
