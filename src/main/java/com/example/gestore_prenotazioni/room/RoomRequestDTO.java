package com.example.gestore_prenotazioni.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomRequestDTO {

    @NotBlank(message = "Il numero della stanza è obbligatorio")
    private String roomNumber;

    @NotBlank(message = "Il tipo della stanza è obbligatorio")
    private String type;

    @NotNull(message = "Il prezzo è obbligatorio")
    private Double price;

    private boolean isAvailable = true;

    private String imageUrl;
}
