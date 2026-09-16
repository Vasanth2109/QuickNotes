package com.quicknotes.dto;

import lombok.Data;

@Data
public class NoteRequest {
    private String title;
    private String content;
}