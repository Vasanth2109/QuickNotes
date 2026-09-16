package com.quicknotes.service;

import com.quicknotes.dto.NoteRequest;
import com.quicknotes.dto.NoteResponse;
import com.quicknotes.model.Note;
import com.quicknotes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note sampleNote;
    private NoteRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleNote = new Note();
        sampleNote.setId(1L);
        sampleNote.setTitle("Buy groceries");
        sampleNote.setContent("Milk, eggs, bread");
        sampleNote.setStatus(Note.Status.PENDING);

        sampleRequest = new NoteRequest();
        sampleRequest.setTitle("Buy groceries");
        sampleRequest.setContent("Milk, eggs, bread");
    }

    @Test
    void createNote_savesAndReturnsNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(sampleNote);

        NoteResponse response = noteService.createNote(sampleRequest);

        assertEquals("Buy groceries", response.getTitle());
        assertEquals(Note.Status.PENDING, response.getStatus());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void getAllNotes_returnsListOfNotes() {
        when(noteRepository.findAll()).thenReturn(List.of(sampleNote));

        List<NoteResponse> responses = noteService.getAllNotes();

        assertEquals(1, responses.size());
        assertEquals("Buy groceries", responses.get(0).getTitle());
    }

    @Test
    void getNoteById_whenFound_returnsNote() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(sampleNote));

        NoteResponse response = noteService.getNoteById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Buy groceries", response.getTitle());
    }

    @Test
    void getNoteById_whenNotFound_throwsException() {
        when(noteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> noteService.getNoteById(99L));
    }

    @Test
    void updateNote_updatesAndReturnsNote() {
        NoteRequest updateRequest = new NoteRequest();
        updateRequest.setTitle("Buy groceries - updated");
        updateRequest.setContent("Milk, eggs, bread, butter");

        when(noteRepository.findById(1L)).thenReturn(Optional.of(sampleNote));
        when(noteRepository.save(any(Note.class))).thenReturn(sampleNote);

        NoteResponse response = noteService.updateNote(1L, updateRequest);

        assertNotNull(response);
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void deleteNote_whenExists_deletesSuccessfully() {
        when(noteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(noteRepository).deleteById(1L);

        noteService.deleteNote(1L);

        verify(noteRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNote_whenNotExists_throwsException() {
        when(noteRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> noteService.deleteNote(99L));
        verify(noteRepository, never()).deleteById(any());
    }

    @Test
    void markAsDone_updatesStatusToDone() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(sampleNote));
        when(noteRepository.save(any(Note.class))).thenReturn(sampleNote);

        NoteResponse response = noteService.markAsDone(1L);

        assertEquals(Note.Status.DONE, response.getStatus());
    }
}