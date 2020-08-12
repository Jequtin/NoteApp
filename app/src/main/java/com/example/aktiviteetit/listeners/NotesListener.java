package com.example.aktiviteetit.listeners;

import com.example.aktiviteetit.entities.Kirjoitus;

public interface NotesListener {
    void onNoteClicked(Kirjoitus note, int position);
}
