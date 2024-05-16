package com.projetsiback.projetsiback.controller;

import com.projetsiback.projetsiback.message.Message;
import com.projetsiback.projetsiback.models.AuthResponse;
import com.projetsiback.projetsiback.models.Role;
import com.projetsiback.projetsiback.models.AccountStatus;
import com.projetsiback.projetsiback.models.requests.LoginRequest;
import com.projetsiback.projetsiback.models.requests.RegisterRequest;
import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.service.AuthService;
import com.projetsiback.projetsiback.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentification")
@CrossOrigin("http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            String token = jwtService.generateToken(user.getMail());
            return ResponseEntity.ok().body(new AuthResponse(token, user.getRole()));
        }catch(LockedException e){
            return ResponseEntity.badRequest().body(new Message("Votre compte est suspendu"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Message("Identifiants invalides"));
        }
    }

    @PostMapping("/creer-compte")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {

            User user = new User();
            user.setMail(registerRequest.getEmail());
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setAddress(registerRequest.getAddress());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setRole(Role.USER);
            user.setAccountStatus(AccountStatus.VALIDE);
            authService.register(user);

            String token = jwtService.generateToken(user.getMail());
            return ResponseEntity.ok().body(new AuthResponse(token,user.getRole()));
    }

}