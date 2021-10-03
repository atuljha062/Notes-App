package com.atul.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNotes extends AppCompatActivity {

    EditText title, description;
    Button insertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        title = (EditText) findViewById(R.id.titleEditText);
        description = (EditText) findViewById(R.id.descriptionEditText);
        insertButton = (Button) findViewById(R.id.createNoteButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesDataHelper db = new NotesDataHelper(CreateNotes.this);
                Boolean checkinsert = db.insertNote(title.getText().toString(),description.getText().toString());

                if(checkinsert){
                    Toast.makeText(CreateNotes.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    MainActivity.data.clear();
                    MainActivity.storeDataInArrays(CreateNotes.this);
                    MainActivity.adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(CreateNotes.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}