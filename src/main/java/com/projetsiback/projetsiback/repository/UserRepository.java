package com.projetsiback.projetsiback.repository;

import com.projetsiback.projetsiback.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    User findByMailAndPassword(String email, String password);
    User findByMail(String email);
    boolean existsByMail(String mail);
}
