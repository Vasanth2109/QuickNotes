package com.quicknotes.service;

import com.quicknotes.dto.NoteRequest;
import com.quicknotes.dto.NoteResponse;
import com.quicknotes.model.Note;
import com.quicknotes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteResponse createNote(NoteRequest request) {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        Note saved = noteRepository.save(note);
        return NoteResponse.from(saved);
    }

    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAll()
                .stream()
                .map(NoteResponse::from)
                .collect(Collectors.toList());
    }

    public NoteResponse getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
        return NoteResponse.from(note);
    }

    public NoteResponse updateNote(Long id, NoteRequest request) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        Note updated = noteRepository.save(note);
        return NoteResponse.from(updated);
    }

    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new RuntimeException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }

    public NoteResponse markAsDone(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
        note.setStatus(Note.Status.DONE);
        Note updated = noteRepository.save(note);
        return NoteResponse.from(updated);
    }
}