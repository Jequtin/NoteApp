package com.example.aktiviteetit.database;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.aktiviteetit.dao.NoteDao;
import com.example.aktiviteetit.entities.Kirjoitus;


@Database(entities = Kirjoitus.class, version = 1, exportSchema = false)
public abstract class Database1 extends RoomDatabase {

    public static Database1 database1;

    public static synchronized Database1 getDatabase(Context context) {
        if(database1 == null){
            database1 = Room.databaseBuilder(context, Database1.class, "notes_db").build();
        }
        return database1;
    }

    public abstract NoteDao noteDao();
}
