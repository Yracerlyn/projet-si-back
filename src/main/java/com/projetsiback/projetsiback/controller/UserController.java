package com.projetsiback.projetsiback.controller;

import com.projetsiback.projetsiback.models.dtos.UserDto;
import com.projetsiback.projetsiback.service.user.UserDtoMapper;
import com.projetsiback.projetsiback.service.user.UserService;
import com.projetsiback.projetsiback.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int userId) {
        UserDto userDto = userDtoMapper.apply(userService.getUserById(userId));
        if (userDto != null) {
            return ResponseEntity.ok().body(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok().body(userDtoMapper.apply(userService.getCurrentUser()));
    }

    @PutMapping("/mise-a-jour")
    public ResponseEntity<UserDto> updateUser(@RequestBody User newUser) {
        UserDto updatedUser = userDtoMapper.apply(userService.updateUser(newUser));
        if (updatedUser != null) {
            return ResponseEntity.ok().body(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reinitialiser-mot-de-passe")
    public ResponseEntity<Boolean> resetPassword(@RequestParam String currentPassword, @RequestParam String newPassword) {
        User currentUser = userService.getCurrentUser();
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