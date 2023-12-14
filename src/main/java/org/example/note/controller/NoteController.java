package org.example.note.controller;

import lombok.RequiredArgsConstructor;
import org.example.note.model.Note;
import org.example.note.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/list")
    public String getAllNotes(Model model) {
        List<Note> noteList = noteService.findAllNotes();
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
        model.addAttribute("note", note);
        return "note/edit";
    }

    @PostMapping("/edit")
    public String editNote(@ModelAttribute Note note) {
        noteService.createNote(note);
        return "redirect:/note/list";
    }

    @GetMapping("/create")
    public String createNotePage(Model model) {
        model.addAttribute("newNote", new Note());
        return "note/create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute Note newNote) {
        noteService.createNote(newNote);
        return "redirect:/note/list";
    }

}

