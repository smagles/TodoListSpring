package org.example.service;

public class NoteNotFoundException extends RuntimeException {
    public static final String ERROR_MESSAGE = "Note not found with id ";

    public NoteNotFoundException(String message) {
        super(message);
    }
}
