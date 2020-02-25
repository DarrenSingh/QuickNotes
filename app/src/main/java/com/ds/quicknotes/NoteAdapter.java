package com.ds.quicknotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate view to pass to viewholder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = notes.get(position);
        StringBuilder importance = new StringBuilder();

        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());

        for (int i = 0; i < note.getImportance(); i++) {
            importance.append('!');
        }

        holder.textViewImportance.setText(importance);

    }

    @Override
    public int getItemCount() {
        //return the size of notes to be viewed in the recycler view
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        //item view components
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewImportance;

        //default constructor
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewImportance = itemView.findViewById(R.id.text_view_importance);
        }
    }
}
