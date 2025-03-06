package com.example.gestore_prenotazioni.auth_controller;

import com.example.gestore_prenotazioni.user.User;
import com.example.gestore_prenotazioni.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {
        @Autowired
        private UserService userService;

        @PostMapping("/register")
        public User registerUser(User user) {
            return userService.registerUser(user);
        }
    }
