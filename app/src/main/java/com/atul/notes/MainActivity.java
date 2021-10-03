package com.atul.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.RecyclerClickListner {

    static List<Notes> data;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    static RecyclerAdapter adapter;

    static NotesDataHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreateNotes.class);
                startActivity(intent);
            }
        });

        db = new NotesDataHelper(this);
        data = new ArrayList<>();
        storeDataInArrays(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(data,this,this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
        intent.putExtra("id",data.get(position).getID());
        intent.putExtra("title",data.get(position).getTitle());
        intent.putExtra("description",data.get(position).getDescription());
        startActivity(intent);
    }

    public static void storeDataInArrays(Context context){
        Cursor cursor = db.getAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(context, "No Data Exist", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                data.add(new Notes(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }
        }
    }
}