package com.projetsiback.projetsiback.service;

import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final SequenceGeneratorService sequenceGenerator;


    public User register(User user) {
        if (userRepository.existsByMail(user.getMail())) {
            throw new RuntimeException("Email already exists");
        }
        int nextId = sequenceGenerator.generateSequence("userId");
        user.setId(nextId);
        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        return userRepository.findByMail(email);
    }
}
