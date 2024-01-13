package org.example.note;

import org.example.note.request.CreateNoteRequest;
import org.example.note.request.UpdateNoteRequest;
import org.example.note.response.*;

public interface NoteService {
    CreateNoteResponse create(String username, CreateNoteRequest request);
    UpdateNoteResponse update(String username, UpdateNoteRequest request);
    DeleteNoteResponse delete(String username, Long id);
    GetNoteByIdResponse getNoteById(Long id);
    GetAllUserNotesResponse getUserNotes(String username);

}
