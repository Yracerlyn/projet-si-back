package com.projetsiback.projetsiback.service;

import com.projetsiback.projetsiback.models.Note;
import com.projetsiback.projetsiback.repository.NoteRepository;
import com.projetsiback.projetsiback.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;
    private final SequenceGeneratorService sequenceGeneratorService;

    public boolean addNote(int noteValue) {
        if (noteValue < 0 || noteValue > 10) {
            return false;
        }
        if(noteRepository.existsNoteByUserId(String.valueOf(userService.getCurrentUser().getId()))){
            Note note = noteRepository.findByUserId(String.valueOf(userService.getCurrentUser().getId()));
            note.setNoteValue(noteValue);
            noteRepository.save(note);
        }else {
            int noteId = sequenceGeneratorService.generateSequence("noteId");
            Note note = new Note();
            note.setId(noteId);
            note.setUserId(userService.getCurrentUser().getId());
            note.setNoteValue(noteValue);
            noteRepository.save(note);
        }
        return true;
    }
}
