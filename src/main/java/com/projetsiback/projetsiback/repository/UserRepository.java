package com.projetsiback.projetsiback.repository;

import com.projetsiback.projetsiback.models.Role;
import com.projetsiback.projetsiback.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    User findByMailAndPassword(String email, String password);
    User findByMail(String email);
    boolean existsByMail(String mail);

    List<User> findUsersByRole(Role role);
}
