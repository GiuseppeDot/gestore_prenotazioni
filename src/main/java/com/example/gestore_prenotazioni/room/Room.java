package com.example.gestore_prenotazioni.room;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il numero della stanza è obbligatorio")
    private String roomNumber;

    @NotBlank(message = "Il tipo della stanza è obbligatorio")
    private String type;

    @NotNull(message = "Il prezzo è obbligatorio")
    private Double price;

    private boolean isAvailable = true;

    private String imageUrl;
}
