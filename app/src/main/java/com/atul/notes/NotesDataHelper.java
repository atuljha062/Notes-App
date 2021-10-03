package com.atul.notes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NotesDataHelper extends SQLiteOpenHelper {
    public NotesDataHelper(Context context) {
        super(context, "NotesData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table NotesDetails(id INTEGER primary key autoincrement, title TEXT, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists NotesDetails");
    }

    public Boolean insertNote(String title, String desc){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("title",title);
        contentValues.put("description",desc);

        long result = db.insert("NotesDetails",null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Boolean deleteNotes(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from NotesDetails where id = ?",new String[]{Integer.toString(id)});

        if(cursor.getCount() > 0){
            long result = db.delete("NotesDetails","id = ?",new String[]{Integer.toString(id)});

            if(result == -1){
                return false;
            }
            else{
                return true;
            }
        }
        else {
            return false;
        }
    }

    Boolean updateNotes(int id,String title, String desc){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("description",desc);

        Cursor cursor = db.rawQuery("select * from NotesDetails where id = ?",new String[]{Integer.toString(id)});

        if(cursor.getCount() > 0){
            long result = db.update("NotesDetails",contentValues,"id = ?",new String[]{Integer.toString(id)});

            if(result == -1){
                return false;
            }
            else {
                return true;
            }
        }
        else{
            return false;
        }

    }

    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from NotesDetails", null);
        return cursor;
    }
}
