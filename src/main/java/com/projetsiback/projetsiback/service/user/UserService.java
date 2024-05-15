package com.projetsiback.projetsiback.service.user;

import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.repository.UserRepository;
import com.projetsiback.projetsiback.service.JwtService;
import com.projetsiback.projetsiback.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final SequenceGeneratorService sequenceGenerator;


    public User addUser(User user) {
        if (userRepository.existsByMail(user.getMail())) {
            throw new RuntimeException("Email already exists");
        }
        int nextId = sequenceGenerator.generateSequence("userId");
        user.setId(nextId);
        return userRepository.save(user);
    }


    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public String deleteUser(int id){
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return id + " User deleted from database";
        }
        return "No user with this Id";
    }

    public User validateUser(String email, String password) {
        return userRepository.findByMailAndPassword(email, password);
    }

    public boolean changePassword(int userId, String newPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User updateUser(User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(updatedUser.getId());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setAddress(updatedUser.getAddress());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        } else {
            throw new IllegalStateException("Utilisateur non authentifié ou impossible à récupérer.");
        }
    }


    public boolean resetPassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByMailAndPassword(email, currentPassword);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean resetUserPassword(int userId, String newPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean validateAccount(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setAccountValidated(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean deleteAccount(int userId) {
        userRepository.deleteById(userId);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByMail(username);
    }
}
