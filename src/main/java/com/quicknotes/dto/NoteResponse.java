package com.quicknotes.dto;

import com.quicknotes.model.Note;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NoteResponse {
    private Long id;
    private String title;
    private String content;
    private Note.Status status;
    private LocalDateTime createdAt;

    public static NoteResponse from(Note note) {
        NoteResponse response = new NoteResponse();
        response.setId(note.getId());
        response.setTitle(note.getTitle());
        response.setContent(note.getContent());
        response.setStatus(note.getStatus());
        response.setCreatedAt(note.getCreatedAt());
        return response;
    }
}