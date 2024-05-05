package controller;

import lombok.RequiredArgsConstructor;
import models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/user")
    public User getUser() {}
}
