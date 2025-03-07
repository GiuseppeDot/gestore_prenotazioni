package com.example.gestore_prenotazioni.booking;

import com.example.gestore_prenotazioni.auth.AppUser;
import com.example.gestore_prenotazioni.room.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser AppUser;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}