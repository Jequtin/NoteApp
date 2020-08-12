package com.example.aktiviteetit.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.aktiviteetit.R;
import com.example.aktiviteetit.activities.TeeMuistiinpano;
import com.example.aktiviteetit.adapters.NotesAdapter;
import com.example.aktiviteetit.database.Database1;
import com.example.aktiviteetit.entities.Kirjoitus;
import com.example.aktiviteetit.listeners.NotesListener;

import java.util.ArrayList;
import java.util.List;

public class Koulu extends AppCompatActivity implements NotesListener {
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    public static final int REQUEST_CODE_SHOW_NOTES = 3;
    private RecyclerView NotesRecycler;
    private List<Kirjoitus> noteList;
    private NotesAdapter notesAdapter;
    private int noteClickedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koulu);

        ImageView imageAdd = findViewById(R.id.AddNote);
        imageAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(new Intent(getApplicationContext(), TeeMuistiinpano.class), REQUEST_CODE_ADD_NOTE);
            }

        });
        NotesRecycler = findViewById(R.id.NotesRecycler);;
        NotesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList, this);
        NotesRecycler.setAdapter(notesAdapter);
        getNotes(REQUEST_CODE_SHOW_NOTES, false);


    }
    @Override
    public void onNoteClicked(Kirjoitus note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), TeeMuistiinpano.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note", note);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
    }
    private void getNotes(final int requestCode, final boolean isNoteDeleted){

        @SuppressLint("StaticFieldLeak")
        class GetNotesT extends AsyncTask<Void, Void, List<Kirjoitus>> {
            @Override
            protected List<Kirjoitus> doInBackground(Void... voids) {
                return Database1.getDatabase(getApplicationContext()).noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Kirjoitus> kirjoitukset) {
                super.onPostExecute(kirjoitukset);
                if(requestCode == REQUEST_CODE_SHOW_NOTES) {
                    noteList.addAll(kirjoitukset);
                    notesAdapter.notifyDataSetChanged();
                }else if(requestCode == REQUEST_CODE_ADD_NOTE){
                    noteList.add(0, kirjoitukset.get(0));
                    notesAdapter.notifyDataSetChanged();
                    NotesRecycler.smoothScrollToPosition(0);
                }else if(requestCode == REQUEST_CODE_UPDATE_NOTE){
                    noteList.remove(noteClickedPosition);
                    if(isNoteDeleted){
                        notesAdapter.notifyItemRemoved(noteClickedPosition);
                    } else{
                        noteList.add(noteClickedPosition, kirjoitukset.get(noteClickedPosition));
                        notesAdapter.notifyItemChanged(noteClickedPosition);
                    }
                }
            }
        }

        new GetNotesT().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK ){
            getNotes(REQUEST_CODE_ADD_NOTE, false);
        } else if(requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK){
            if(data != null){
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted", false));
            }
        }
    }
}
