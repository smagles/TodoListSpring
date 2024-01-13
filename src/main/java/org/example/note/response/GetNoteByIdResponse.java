package org.example.note.response;

import lombok.Builder;
import lombok.Data;
import org.example.note.Note;

@Data
@Builder
public class GetNoteByIdResponse {
    private Error error;
    private Note note;

    public enum Error {
        ok,
        insufficientPrivileges,
        invalidNoteId
    }

    public static GetNoteByIdResponse success(Note note) {
        return builder().error(Error.ok).note(note).build();
    }

    public static GetNoteByIdResponse failed(Error error) {
        return builder().error(error).note(null).build();
    }
}
