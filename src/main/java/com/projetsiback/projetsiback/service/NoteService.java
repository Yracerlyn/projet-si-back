package com.projetsiback.projetsiback.service;

import com.projetsiback.projetsiback.models.Note;
import com.projetsiback.projetsiback.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    private UserService userService;

    public boolean addNote(int noteValue) {
        Note note = new Note();
        note.setUserId(userService.getCurrentUser().getId());
        note.setNoteValue(noteValue);

        noteRepository.save(note);
        return true;
    }
}
