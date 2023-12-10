package org.example.note.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.note.repo.NoteRepository;
import org.example.note.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Log4j
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> findAll() {
        log.info("Finding all notes");
        return noteRepository.findAll();
    }

    public Note save(Note note) {
        if (note == null) {
            throw new IllegalArgumentException("Note cannot be null");
        }
        Note savedNote = noteRepository.save(note);
        log.info("Saved note with id: " + savedNote.getId());
        return savedNote;
    }

    public Note update(Long id, Note updatedNote) {
        if (noteRepository.existsById(id)) {
            updatedNote.setId(id);
            Note savedNote = noteRepository.save(updatedNote);
            log.info("Updated note with id: " + savedNote.getId());
            return savedNote;
        } else {
            throw new EntityNotFoundException("Note with id " + id + " not found");
        }
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
        log.info("Deleted note with id: " + id);
    }


    public Note findById(Long id) {
        log.info("Finding note by id: " + id);
        return noteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Note not found with id: " + id);
                    return new EntityNotFoundException("Note not found with id: " + id);
                });
    }
}
