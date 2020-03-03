package com.ds.quicknotes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ds.quicknotes.Entities.Note;
import com.ds.quicknotes.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate view to pass to viewholder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = notes.get(position);

        StringBuilder importance = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, YY", Locale.CANADA);
        Timestamp timestamp = new Timestamp(note.getUpdated());

        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());
        holder.textViewDate.setText(dateFormat.format(timestamp));


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

    public Note getNote(int id) {
        return notes.get(id);
    }

    public void sort(String attribute){
        switch (attribute){
            case "Title":
                this.notes.sort(Comparator.comparing(Note::getTitle));
                break;
            case "Date Modified":
                this.notes.sort(Comparator.comparing(Note::getUpdated));
                break;
            case "Importance (Default)":
                this.notes.sort(Comparator.comparing(Note::getImportance).reversed());
                break;
            default:
                break;

        }
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        //item view components
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewImportance;
        private TextView textViewDate;

        //default constructor
        private NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewImportance = itemView.findViewById(R.id.text_view_importance);
            textViewDate = itemView.findViewById(R.id.text_view_date);

            //set onclick listener to this items view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // obtain the position on the view
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        //obtain the user entry form the list using the click position
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    //define an interface with method so we are forced to implement the method anytime a class uses it
    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
