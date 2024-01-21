package org.example.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.restapi.note.Note;
import org.example.restapi.note.NoteRepository;
import org.example.restapi.user.User;
import org.example.restapi.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;

    public List<Note> findAllUserNotes(String username) {
        User user = userService.findByUsername(username);
        log.info("Finding all notes");
        return noteRepository.findAllNotesByUserId(user.getId());
    }

    public Note createNote(Note note, String username) {
        User user = userService.findByUsername(username);
        note.setUser(user);
        Note savedNote = noteRepository.save(note);
        log.info("Saved note with id: " + savedNote.getId());
        return savedNote;
    }

    public Note updateNote(Note updatedNote,String username) {
        Optional<Note> optionalNote = noteRepository.findById(updatedNote.getId());
        User user = userService.findByUsername(username);
        if (optionalNote.isPresent()) {
            updatedNote.setUser(user);
            Note savedNote = noteRepository.save(updatedNote);
            log.info("Updated note with id: " + savedNote.getId());
            return savedNote;
        } else {
            throw new EntityNotFoundException("Note with id " + updatedNote.getId() + " not found");
        }
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
        log.info("Deleted note with id: " + id);
    }


    public Note findNoteById(Long id) {
        log.info("Finding note by id: " + id);
        return noteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Note not found with id: " + id);
                    return new EntityNotFoundException("Note not found with id: " + id);
                });
    }
}