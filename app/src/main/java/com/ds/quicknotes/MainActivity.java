package com.ds.quicknotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Note ViewModel instance
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        //lifecycle aware so get all notes will be updated only on livedata change
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Update UI
                Toast.makeText(MainActivity.this, "observer onChanged", Toast.LENGTH_LONG).show();
            }
        });

    }
}
