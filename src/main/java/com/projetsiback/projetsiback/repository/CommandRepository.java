package com.projetsiback.projetsiback.repository;

import com.projetsiback.projetsiback.models.Command;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends MongoRepository<Command, Integer> {
    List<Command> findByUserId(int userId);
}