package com.atul.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsActivity extends AppCompatActivity {

    TextView title,description;
    EditText editTitle, editDescription;
    FloatingActionButton editNoteButton;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title = (TextView) findViewById(R.id.titleTextView);
        description = (TextView) findViewById(R.id.descriptionTextView);
        editNoteButton = (FloatingActionButton) findViewById(R.id.editNoteButton);
        updateButton = (Button) findViewById(R.id.updateButton);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editDescription = (EditText) findViewById(R.id.editDescription);

        Intent intent = getIntent();

        final String titleToShow = intent.getStringExtra("title");
        final String descriptionToShow = intent.getStringExtra("description");
        final int id = intent.getIntExtra("id",-1);

        title.setText(titleToShow);
        description.setText(descriptionToShow);

        editNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setVisibility(View.GONE);
                description.setVisibility(View.GONE);
                editNoteButton.setVisibility(View.GONE);
                editTitle.setVisibility(View.VISIBLE);
                editDescription.setVisibility(View.VISIBLE);
                updateButton.setVisibility(View.VISIBLE);

                editTitle.setText(titleToShow);
                editDescription.setText(descriptionToShow);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesDataHelper db = new NotesDataHelper(DetailsActivity.this);
                Boolean checkUpdate = db.updateNotes(id,editTitle.getText().toString(),editDescription.getText().toString());
                if(checkUpdate){
                    Toast.makeText(DetailsActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

                    updateButton.setVisibility(View.GONE);
                    editTitle.setVisibility(View.GONE);
                    editDescription.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                    title.setText(editTitle.getText());
                    description.setVisibility(View.VISIBLE);
                    description.setText(editDescription.getText());
                    editNoteButton.setVisibility(View.VISIBLE);

                    MainActivity.data.clear();
                    MainActivity.storeDataInArrays(DetailsActivity.this);
                    MainActivity.adapter.notifyDataSetChanged();
                }
            }
        });


    }
}