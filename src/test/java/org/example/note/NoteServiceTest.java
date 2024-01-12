package org.example.note;

import org.example.note.NoteService;
import org.example.note.model.Note;
import org.example.note.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void testFindAllNotes() {
        // Given
        Note note1 = new Note(1L, "Title 1", "Content 1");
        Note note2 = new Note(2L, "Title 2", "Content 2");
        List<Note> expectedNotes = Arrays.asList(note1, note2);

        // When
        Mockito.when(noteRepository.findAll()).thenReturn(expectedNotes);
        List<Note> actualNotes = noteService.findAllNotes();

        // Then
        assertEquals(expectedNotes, actualNotes);
    }

}
