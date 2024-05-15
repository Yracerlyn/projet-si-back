package com.projetsiback.projetsiback.repository;

import com.projetsiback.projetsiback.models.Note;
import com.projetsiback.projetsiback.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note, Integer> {

    boolean existsNoteByUserId(String userId);

    Note findByUserId(String userId);

}
