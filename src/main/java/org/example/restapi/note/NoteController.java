package org.example.restapi.note;

import lombok.RequiredArgsConstructor;
import org.example.restapi.note.request.CreateNoteRequest;
import org.example.restapi.note.request.UpdateNoteRequest;
import org.example.restapi.note.response.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notes")
public class NoteController {
    private final NoteServiceImpl noteService;

    @PostMapping("/create")
    public CreateNoteResponse create(Principal principal, @RequestBody CreateNoteRequest request) {
        return noteService.create(principal.getName(), request);
    }

    @GetMapping("/list")
    public GetAllUserNotesResponse getUserNotes(Principal principal) {
        return noteService.getUserNotes(principal.getName());
    }

    @GetMapping("/{id}")
    public GetNoteByIdResponse getNoteById(@PathVariable(name = "id") Long id) {
        return noteService.getNoteById(id);
    }

    @PatchMapping("/edit")
    public UpdateNoteResponse update(Principal principal, @RequestBody UpdateNoteRequest request) {
        return noteService.update(principal.getName(), request);
    }

    @DeleteMapping("/delete/{id}")
    public DeleteNoteResponse delete(Principal principal, @PathVariable(name = "id") Long id) {
        return noteService.delete(principal.getName(), id);
    }
}