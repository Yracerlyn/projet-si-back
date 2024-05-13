package com.projetsiback.projetsiback.controller;


import com.projetsiback.projetsiback.message.Message;
import com.projetsiback.projetsiback.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/noter")
    public ResponseEntity<Message> addNote(@RequestBody int noteValue, @RequestBody int userId, @RequestBody int productId) {
        boolean noteValide = noteService.addNote(noteValue);
        if (noteValide) {
            return ResponseEntity.ok().body(new Message("La note a été ajoutée avec succès."));
        } else {
            return ResponseEntity.badRequest().body(new Message("La note n'a pas pu être ajoutée."));
        }
    }
}
