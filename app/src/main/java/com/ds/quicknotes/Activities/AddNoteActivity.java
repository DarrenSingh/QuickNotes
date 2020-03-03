package com.ds.quicknotes.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ds.quicknotes.R;

public class AddNoteActivity extends AppCompatActivity {

    private String noteId = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText editTitle = findViewById(R.id.edit_text_title);
        EditText editDesc = findViewById(R.id.edit_text_description);

        Spinner spinImportance = findViewById(R.id.spinner);
        Button save = findViewById(R.id.button);


        if(getIntent().hasExtra("id")){
            Intent data = getIntent();
            editTitle.setText(data.getStringExtra("title"));
            editDesc.setText(data.getStringExtra("description"));
            noteId = data.getStringExtra("id");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String desc = editDesc.getText().toString();
                String impt = spinImportance.getSelectedItem().toString();

                if(title.length() < 1 && desc.length() < 1){
                    Toast.makeText(AddNoteActivity.this, "Unable to create empty note", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent data = new Intent();

                if(noteId != "-1"){
                    data.putExtra("id",noteId);
                }

                data.putExtra("title",title);
                data.putExtra("description",desc);
                data.putExtra("importance",impt);

                setResult(RESULT_OK,data);
                finish();

            }
        });

    }

}
