package org.example.web;

import lombok.RequiredArgsConstructor;
import org.example.restapi.note.Note;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class WebNoteController {

    private final NoteService noteService;

    @GetMapping("/list")
    public String getAllNotes(Model model, Principal principal) {
        List<Note> noteList = noteService.findAllUserNotes(principal.getName());
        model.addAttribute("notes", noteList);
        return "note/list";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String getEditNoteForm(@RequestParam Long id, Model model) {
        Note note = noteService.findNoteById(id);
        if (note == null) {
            return "error";
        }
        model.addAttribute("editNote", note);
        return "note/edit";
    }

    @PostMapping("/edit")
    public String editNote(@ModelAttribute Note note, Principal principal) {
        noteService.updateNote(note, principal.getName());
        return "redirect:/note/list";
    }

    @GetMapping("/create")
    public String createNotePage(Model model) {
        model.addAttribute("newNote", new Note());
        return "note/create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute Note newNote, Principal principal) {
        noteService.createNote(newNote, principal.getName());
        return "redirect:/note/list";
    }

}