package com.ds.quicknotes.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ds.quicknotes.Adapters.NoteAdapter;
import com.ds.quicknotes.Entities.Note;
import com.ds.quicknotes.ViewModels.NoteViewModel;
import com.ds.quicknotes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static int ADD_NOTE_REQUEST = 1;
    public final static int EDIT_NOTE_REQUEST = 2;

    //Note ViewModel instance
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start Components
        FloatingActionButton floatingActionButton = findViewById(R.id.btn_add_note);

        //End Components

        //  Recycler View Start
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //takes care of viewing items one below the other
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        //  Recycler View End

        //  View Model Start
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        //lifecycle aware so get all notes will be updated only on livedata change
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
            //Update UI
                //set adapter list data to the observed notes data from the NoteViewModel class
                noteAdapter.setNotes(notes);
            }
        });
        //  View Model End

        // Add Note OnClick Listeners
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddNoteActivity.class);
                startActivityForResult(i,ADD_NOTE_REQUEST);
            }
        });

        // Edit Note RecyclerView Item click listener
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent i = new Intent(MainActivity.this,AddNoteActivity.class);
                i.putExtra("id",String.valueOf(note.getId()));
                Log.i("ID CLICKED",String.valueOf(note.getId()));
                i.putExtra("title",note.getTitle());
                i.putExtra("description",note.getDescription());
                i.putExtra("importance",note.getImportance());
                startActivityForResult(i,EDIT_NOTE_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note swipedNote = noteAdapter.getNote(viewHolder.getAdapterPosition());
                noteViewModel.delete(swipedNote);
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            Toast.makeText(this, "Successfully added note", Toast.LENGTH_SHORT).show();
            noteViewModel.insert(new Note(
                    data.getStringExtra("title"),
                    data.getStringExtra("description"),
                    Integer.valueOf(data.getStringExtra("importance"))
            ));

        } else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, "Successfully edited note", Toast.LENGTH_SHORT).show();
            Note updatedNote = new Note(
                    Integer.valueOf(data.getStringExtra("id")),
                    data.getStringExtra("title"),
                    data.getStringExtra("description"),
                    Integer.valueOf(data.getStringExtra("importance"))
            );

            noteViewModel.update(updatedNote);

        }
    }

    //Set top menu for activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_delete_all:
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
//                noteViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
