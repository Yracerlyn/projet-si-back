package com.projetsiback.projetsiback.controller;

import com.projetsiback.projetsiback.service.UserService;
import com.projetsiback.projetsiback.models.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        User userDto = userService.getUserById(userId);
        if (userDto != null) {
            return ResponseEntity.ok().body(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> getCurrentUser() {
        ///////////////////
        return null;
    }

    @PutMapping("/mise-a-jour")
    public ResponseEntity<User> updateUser(@RequestBody User newUser) {
        User updatedUser = userService.updateUser(newUser);
        if (updatedUser != null) {
            return ResponseEntity.ok().body(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reinitialiser-mot-de-passe")
    public ResponseEntity<Boolean> resetPassword(@RequestParam String currentPassword, @RequestParam String newPassword) {
        User currentUser = getCurrentUser().getBody();
        boolean motDePasseChange = userService.resetPassword(currentUser.getMail(), currentPassword, newPassword);
        return ResponseEntity.ok().body(motDePasseChange);
    }

    @PostMapping("/reinitialiser-mot-de-passe-user")
    public ResponseEntity<Boolean> resetUserPassword(@RequestParam int userId, @RequestParam String newPassword) {
        boolean motDePasseChange = userService.resetUserPassword(userId, newPassword);
        return ResponseEntity.ok().body(motDePasseChange);
    }

    @PostMapping("/valider-compte")
    public ResponseEntity<Boolean> validateAccount(@RequestParam int userId) {
        boolean compteValide = userService.validateAccount(userId);
        return ResponseEntity.ok().body(compteValide);
    }

    @PostMapping("/radier-compte")
    public ResponseEntity<Boolean> deleteAccount(@RequestParam int userId) {
        boolean compteRadie = userService.deleteAccount(userId);
        return ResponseEntity.ok().body(compteRadie);
    }
}