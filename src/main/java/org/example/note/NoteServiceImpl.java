package org.example.note;

import lombok.RequiredArgsConstructor;
import org.example.note.request.CreateNoteRequest;
import org.example.note.request.UpdateNoteRequest;
import org.example.note.response.CreateNoteResponse;
import org.example.note.response.*;
import org.example.user.User;
import org.example.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_CONTENT_LENGTH = 1000;

    private final UserService userService;
    private final NoteRepository noteRepository;

    @Override
    public GetAllUserNotesResponse getUserNotes(String username) {
        User user = userService.findByUsername(username);
        List<Note> userNotes = noteRepository.findAllNotesByUserId(user.getId());

        return GetAllUserNotesResponse.success(userNotes);
    }

    @Override
    public GetNoteByIdResponse getNoteById(Long id) {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isEmpty()) {
            return GetNoteByIdResponse.failed(GetNoteByIdResponse.Error.invalidNoteId);
        }
        Note note = optionalNote.get();

        return GetNoteByIdResponse.success(note);
    }

    @Override
    public CreateNoteResponse create(String username, CreateNoteRequest request) {
        Optional<CreateNoteResponse.Error> validationError = validateCreateFields(request);

        if (validationError.isPresent()) {
            return CreateNoteResponse.failed(validationError.get());
        }

        User user = userService.findByUsername(username);

        Note createdNote = noteRepository.save(Note.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build());

        return CreateNoteResponse.success(createdNote.getId());
    }

    @Override
    public UpdateNoteResponse update(String username, UpdateNoteRequest request) {
        Optional<Note> optionalNote = noteRepository.findById(request.getId());

        if (optionalNote.isEmpty()) {
            return UpdateNoteResponse.failed(UpdateNoteResponse.Error.invalidNoteId);
        }

        Note note = optionalNote.get();

        boolean isNotUserNote = isNotUserNote(username, note);

        if (isNotUserNote) {
            return UpdateNoteResponse.failed(UpdateNoteResponse.Error.insufficientPrivileges);
        }
        Optional<UpdateNoteResponse.Error> validationError = validateUpdateFields(request);

        if (validationError.isPresent()) {
            return UpdateNoteResponse.failed(validationError.get());
        }

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        noteRepository.save(note);

        return UpdateNoteResponse.success(note);
    }

    @Override
    public DeleteNoteResponse delete(String username, Long id) {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isEmpty()) {
            return DeleteNoteResponse.failed(DeleteNoteResponse.Error.invalidNoteId);
        }

        Note note = optionalNote.get();

        boolean isNotUserNote = isNotUserNote(username, note);

        if (isNotUserNote) {
            return DeleteNoteResponse.failed(DeleteNoteResponse.Error.insufficientPrivileges);
        }

        noteRepository.delete(note);

        return DeleteNoteResponse.success();
    }

    private Optional<CreateNoteResponse.Error> validateCreateFields(CreateNoteRequest request) {
        if (Objects.isNull(request.getTitle()) || request.getTitle().length() > MAX_TITLE_LENGTH) {
            return Optional.of(CreateNoteResponse.Error.invalidTitle);
        }

        if (Objects.isNull(request.getContent()) || request.getContent().length() > MAX_CONTENT_LENGTH) {
            return Optional.of(CreateNoteResponse.Error.invalidTitle);
        }

        return Optional.empty();
    }

    private Optional<UpdateNoteResponse.Error> validateUpdateFields(UpdateNoteRequest request) {
        if (Objects.nonNull(request.getTitle()) && request.getTitle().length() > MAX_TITLE_LENGTH) {
            return Optional.of(UpdateNoteResponse.Error.invalidTitleLength);
        }

        if (Objects.nonNull(request.getContent()) && request.getContent().length() > MAX_CONTENT_LENGTH) {
            return Optional.of(UpdateNoteResponse.Error.invalidTitleLength);
        }

        return Optional.empty();
    }

    private boolean isNotUserNote(String username, Note note) {
        return !note.getUser().getUsername().equals(username);
    }

}