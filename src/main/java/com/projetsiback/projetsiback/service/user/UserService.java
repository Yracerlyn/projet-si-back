package com.projetsiback.projetsiback.service.user;

import com.projetsiback.projetsiback.models.AccountStatus;
import com.projetsiback.projetsiback.models.Role;
import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.repository.UserRepository;
import com.projetsiback.projetsiback.service.JwtService;
import com.projetsiback.projetsiback.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

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
            existingUser.setAvatar(updatedUser.getAvatar());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findUsersByRole(Role.USER);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        } else {
            throw new IllegalStateException("Utilisateur non authentifié ou impossible à récupérer.");
        }
    }


    public boolean resetPassword(User user, String newPassword) {
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
            user.setAccountStatus(AccountStatus.VALIDE);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean banAccount(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            user.get().setAccountStatus(AccountStatus.RADIE);
            userRepository.save(user.get());
            return true;
        }return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByMail(username);
    }
}
