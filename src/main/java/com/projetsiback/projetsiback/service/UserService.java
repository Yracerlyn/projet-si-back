package com.projetsiback.projetsiback.service;

import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    //CRUD CREATE, READ, UPDATE, DELETE

    public User addUser(User user){
        return userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id).get();
    }

    public String deleteUser(int id){
        if (getUserById(id) != null) {
            userRepository.deleteById(id);
            return id + " User deleted from database";
        }
        return "No user with this Id";
    }
}
