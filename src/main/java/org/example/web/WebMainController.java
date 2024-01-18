package org.example.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping ("/note-service")
public class WebMainController {
    private final NoteService noteService;
    @GetMapping
    public String showMainPage (){
        return "main_page";
    }
}
