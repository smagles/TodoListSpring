package org.example.service;

import org.example.entities.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.service.NoteNotFoundException.ERROR_MESSAGE;

@Service
public class NoteService {
    private final List<Note> notes = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public List<Note> listAll() {
        return new ArrayList<>(notes);
    }

    public Note add(Note note) {
        long id = idCounter.incrementAndGet();
        note.setId(id);
        notes.add(note);
        return note;
    }

    public void deleteById(long id) {
        boolean removed = notes.removeIf(note -> note.getId() == id);
        if (!removed) {
            throw new NoteNotFoundException(ERROR_MESSAGE + id);
        }
    }

    public void update(Note note) {
        long id = note.getId();

        Note foundNote = notes.stream()
                .filter(existingNote -> existingNote.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoteNotFoundException(ERROR_MESSAGE + id));

        foundNote.setTitle(note.getTitle());
        foundNote.setContent(note.getContent());
    }

    public Note getById(long id) {
        return notes.stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoteNotFoundException(ERROR_MESSAGE + id));
    }
}
