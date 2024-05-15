package com.projetsiback.projetsiback.controller;


import com.projetsiback.projetsiback.message.Message;
import com.projetsiback.projetsiback.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/noter/{noteValue}")
    public ResponseEntity<?> addNote(@PathVariable int noteValue) {
        boolean noteValide = noteService.addNote(noteValue);
        if (noteValide) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.badRequest().body(new Message("La note n'a pas pu être ajoutée."));
        }
    }
}
