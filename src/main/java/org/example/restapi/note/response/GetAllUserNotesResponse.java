package org.example.restapi.note.response;

import lombok.Data;
import org.example.restapi.note.Note;

import java.util.List;

import lombok.Builder;


@Builder
@Data
public class GetAllUserNotesResponse {
    private Error error;

    private List<Note> userNotes;

    public enum Error {
        ok
    }

    public static GetAllUserNotesResponse success(List<Note> userNotes) {
        return builder().error(Error.ok).userNotes(userNotes).build();
    }

    public static GetAllUserNotesResponse failed(Error error) {
        return builder().error(error).userNotes(null).build();
    }
}