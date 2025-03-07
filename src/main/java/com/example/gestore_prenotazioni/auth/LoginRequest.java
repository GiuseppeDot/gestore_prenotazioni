package com.example.gestore_prenotazioni.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
