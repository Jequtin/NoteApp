package com.example.aktiviteetit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aktiviteetit.R;
import com.example.aktiviteetit.entities.Kirjoitus;
import com.example.aktiviteetit.listeners.NotesListener;

import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Kirjoitus> kirjoitukset;
    private NotesListener notesListener;



    public NotesAdapter(List<Kirjoitus> kirjoitukset, NotesListener notesListener) {
        this.kirjoitukset = kirjoitukset;
        this.notesListener = notesListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_note,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
    holder.setNote(kirjoitukset.get(position));
    holder.layoutNote.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            notesListener.onNoteClicked(kirjoitukset.get(position), position);
        }
    });
    }

    @Override
    public int getItemCount() {
        return kirjoitukset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textText; //Jos haluat piilottaa tekstin perusnäkymästä, niin poista se täältä.
        LinearLayout layoutNote;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textText = itemView.findViewById(R.id.textText);
            layoutNote = itemView.findViewById(R.id.layoutNote);
        }

        void setNote(Kirjoitus note){
            textTitle.setText(note.getTitle());
            if(note.getText().trim().isEmpty()) {
                textText.setVisibility(View.GONE);

            } else{
                textText.setText(note.getText());
            }

        }
    }
}
