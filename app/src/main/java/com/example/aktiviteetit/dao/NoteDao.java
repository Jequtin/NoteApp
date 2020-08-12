package com.example.aktiviteetit.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.aktiviteetit.entities.Kirjoitus;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM kirjoitukset ORDER BY id DESC")
    List<Kirjoitus> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Kirjoitus note);

    @Delete
    void DeleteNote(Kirjoitus note);
}
