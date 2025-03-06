package com.example.gestore_prenotazioni.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Metodo per registrare un nuovo utente
    public User registerUser(User user) {
        // Crittografia la password del nuovo utente
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Salva l'utente
        return userRepository.save(user);
    }
}