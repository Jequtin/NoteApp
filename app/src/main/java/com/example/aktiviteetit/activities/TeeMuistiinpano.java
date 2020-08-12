package com.example.aktiviteetit.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aktiviteetit.R;
import com.example.aktiviteetit.database.Database1;
import com.example.aktiviteetit.entities.Kirjoitus;

public class TeeMuistiinpano extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    private EditText inputTitle;
    private EditText inputText;
    private Kirjoitus alreadyAvailableNote;
    private AlertDialog dialogDeleteNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tee_muistiinpano);
        ImageView imageBack = findViewById(R.id.goBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        coordinatorLayout = findViewById(R.id.layoutNotes);
        inputTitle = findViewById(R.id.otsikko);
        inputText = findViewById(R.id.teksti);
        ImageView saveIcon = findViewById(R.id.tallenna);
        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tallennaPano();
            }
        });
        if(getIntent().getBooleanExtra("isViewOrUpdate", false)){
            alreadyAvailableNote = (Kirjoitus) getIntent().getSerializableExtra("note");
            setViewOrUpdateNote();


        }
        if(alreadyAvailableNote != null){
            coordinatorLayout.findViewById(R.id.deleteNoteLayout).setVisibility(View.VISIBLE);
            coordinatorLayout.findViewById(R.id.deleteNoteLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeleteNoteDialog();
                }
            });
        }

    }
    private void showDeleteNoteDialog(){
        if(dialogDeleteNote == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(TeeMuistiinpano.this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_delete, (ViewGroup) findViewById(R.id.deleteNoteContainer));
            builder.setView(view);
            dialogDeleteNote = builder.create();
            if(dialogDeleteNote.getWindow() != null){
                dialogDeleteNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.DeleteMsg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    @SuppressLint("StaticFieldLeak")
                    class DeleteNoteTask extends AsyncTask<Void, Void, Void>{

                        @Override
                        protected Void doInBackground(Void... voids) {
                            Database1.getDatabase(getApplicationContext()).noteDao().DeleteNote(alreadyAvailableNote);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Intent intent = new Intent();
                            intent.putExtra("isNoteDeleted", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    new DeleteNoteTask().execute();
                }
            });
            view.findViewById(R.id.Cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogDeleteNote.dismiss();
                }
            });
        }

        dialogDeleteNote.show();
    }
    private void setViewOrUpdateNote(){
        inputTitle.setText(alreadyAvailableNote.getTitle());
        inputText.setText(alreadyAvailableNote.getText());
    }

        private void tallennaPano(){
            if (inputTitle.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Älä tallenna tyhjänä mölö", Toast.LENGTH_SHORT).show();
                return;
            } else if (inputText.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Älä jätä tyhjäksi :(", Toast.LENGTH_SHORT).show();
                return;
            }

            final Kirjoitus note = new Kirjoitus();
            note.setTitle(inputTitle.getText().toString());
            note.setText(inputText.getText().toString());

            if(alreadyAvailableNote != null){
                note.setId(alreadyAvailableNote.getId());
            }

            @SuppressLint("StaticFieldLeak")
            class SaveNoteTask extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                    Database1.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            new SaveNoteTask().execute();

        }


}