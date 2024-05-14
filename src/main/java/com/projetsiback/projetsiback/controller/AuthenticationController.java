package com.projetsiback.projetsiback.controller;

import com.projetsiback.projetsiback.message.Message;
import com.projetsiback.projetsiback.models.AuthResponse;
import com.projetsiback.projetsiback.models.requests.LoginRequest;
import com.projetsiback.projetsiback.models.requests.RegisterRequest;
import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.service.JwtService;
import com.projetsiback.projetsiback.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/authentification")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.validateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            String token = jwtService.generateToken(user.getMail());
            String refreshToken = generateRefreshToken();
            return ResponseEntity.ok().body(new AuthResponse(token, refreshToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/creer-compte")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = new User();
            user.setMail(registerRequest.getEmail());
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setAddress(registerRequest.getAddress());
            user.setPassword(registerRequest.getPassword());
            userService.addUser(user);

            String token = jwtService.generateToken(user.getMail());
            String refreshToken = generateRefreshToken();
            return ResponseEntity.ok().body(new AuthResponse(token, refreshToken));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new Message(e.getMessage()));
        }
    }

    private String generateRefreshToken() {
        String refreshToken = UUID.randomUUID().toString();
        return refreshToken;
    }
}