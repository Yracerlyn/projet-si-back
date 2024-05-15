package com.projetsiback.projetsiback.controller;

import com.projetsiback.projetsiback.message.Message;
import com.projetsiback.projetsiback.models.dtos.UserDto;
import com.projetsiback.projetsiback.models.requests.NewPasswordRequest;
import com.projetsiback.projetsiback.models.requests.ResetPasswordRequest;
import com.projetsiback.projetsiback.service.user.UserDtoMapper;
import com.projetsiback.projetsiback.service.user.UserService;
import com.projetsiback.projetsiback.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Boolean> resetPassword(@RequestBody NewPasswordRequest newPasswordRequest) {
        User currentUser = userService.getCurrentUser();
        boolean motDePasseChange = userService.resetPassword(currentUser.getMail(), newPasswordRequest.getCurrentPassword(), newPasswordRequest.getNewPassword());
        return ResponseEntity.ok().body(motDePasseChange);
    }

    @PostMapping("/reinitialiser-mot-de-passe-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Boolean> resetUserPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        boolean motDePasseChange = userService.resetUserPassword(resetPasswordRequest.getId(), resetPasswordRequest.getNewPassword());
        return ResponseEntity.ok().body(motDePasseChange);
    }

    @PostMapping("/valider-compte/{userId}")
    public ResponseEntity<?> validateAccount(@PathVariable int userId) {
        return ResponseEntity.ok(userService.validateAccount(userId));
    }

    @PostMapping("/radier-compte/{userId}")
    public ResponseEntity<?> banAccount(@PathVariable int userId) {
        return ResponseEntity.ok(userService.banAccount(userId));
    }
}