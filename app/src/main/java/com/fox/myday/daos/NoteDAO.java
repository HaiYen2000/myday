package com.fox.myday.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fox.myday.Constants;
import com.fox.myday.database.DBHelper;
import com.fox.myday.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDAO extends Constants {

    private DBHelper dbHelper;

    public NoteDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public NoteDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insertNote(Note note) {

        long result = -1;
        ContentValues cv = new ContentValues();
        //cv.put(NOTE_ID,note.NOTE_ID);
        cv.put(NOTE_TITLE, note.NOTE_TITLE);
        cv.put(NOTE_CONTENT, note.NOTE_CONTENT);
        cv.put(NOTE_BACKGROUND_COLOR, note.NOTE_BACKGROUND_COLOR);
        cv.put(NOTE_CREATED_DATE, note.NOTE_CREATED_DATE);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.insert(NOTE_TABLE, null, cv);
        sqLiteDatabase.close();
        return result;

    }

    public long updateNote(Note note) {
        long result = -1;
        ContentValues cv = new ContentValues();
        cv.put(NOTE_ID, note.NOTE_ID);
        cv.put(NOTE_TITLE, note.NOTE_TITLE);
        cv.put(NOTE_CONTENT, note.NOTE_CONTENT);
        cv.put(NOTE_BACKGROUND_COLOR, note.NOTE_BACKGROUND_COLOR);
        cv.put(NOTE_CREATED_DATE, note.NOTE_CREATED_DATE);
        cv.put(NOTE_MODIFIED_DATE, note.NOTE_MODIFIED_DATE);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.update(NOTE_TABLE, cv, NOTE_ID + "= ?", new String[]{String.valueOf(note.NOTE_ID)});
        sqLiteDatabase.close();
        return result;
    }

    public long deleteNote(int id) {

        long result = -1;

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.delete(NOTE_TABLE, NOTE_ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();

        return result;

    }

    public List<Note> getAllNote() {
        List<Note> users = new ArrayList<>();
        String QUERY = "SELECT * FROM " + NOTE_TABLE;
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    int NOTE_ID_ = cursor.getInt(cursor.getColumnIndex(NOTE_ID));
                    String NOTE_TITLE_ = cursor.getString(cursor.getColumnIndex(NOTE_TITLE));
                    String NOTE_CONTENT_ = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT));
                    String NOTE_BACKGROUND_COLOR_ = cursor.getString(cursor.getColumnIndex(NOTE_BACKGROUND_COLOR));
                    String NOTE_CREATED_DATE_ = cursor.getString(cursor.getColumnIndex(NOTE_CREATED_DATE));
                    String NOTE_MODIFIED_DATE_ = cursor.getString(cursor.getColumnIndex(NOTE_MODIFIED_DATE));
                    Note note = new Note(NOTE_ID_, NOTE_TITLE_, NOTE_CONTENT_, NOTE_BACKGROUND_COLOR_, NOTE_CREATED_DATE_, NOTE_MODIFIED_DATE_);
                    users.add(note);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return users;
    }

    public String getCurrentTime(){
        String current_time = null;
        String QUERY = "SELECT DATETIME('NOW','LOCALTIME')";
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while ((!cursor.isAfterLast())){
                    current_time = cursor.getString(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();

            }
        }
        return current_time;
    }

    public String getCreatedDayOfWeek(){
        String day_of_week = null;
        String QUERY = "SELECT CASE CAST (STRFTIME('%w'," + NOTE_CREATED_DATE  + ") AS INTEGER)"
                + " WHEN 0 THEN 'Sunday'"
                + " WHEN 1 THEN 'Monday'"
                + " WHEN 2 THEN 'Tuesday'"
                + " WHEN 3 THEN 'Wednesday'"
                + " WHEN 4 THEN 'Thursday'"
                + " WHEN 5 THEN 'Friday'"
                + " WHEN 6 THEN 'Saturday'"
                + " END AS 'DAYOFWEEK' FROM " + NOTE_TABLE ;
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while ((!cursor.isAfterLast())){
                    day_of_week = cursor.getString(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();

            }
        }
        return day_of_week;
    }

    public String getModifiedDayOfWeek(){
        String day_of_week = null;
        String QUERY = "SELECT CASE CAST (STRFTIME('%w'," + NOTE_MODIFIED_DATE  +") AS INTEGER)"
                + " WHEN 0 THEN 'Sunday'"
                + " WHEN 1 THEN 'Monday'"
                + " WHEN 2 THEN 'Tuesday'"
                + " WHEN 3 THEN 'Wednesday'"
                + " WHEN 4 THEN 'Thursday'"
                + " WHEN 5 THEN 'Friday'"
                + " WHEN 6 THEN 'Saturday'"
                + " END AS 'DAYOFWEEK' FROM " + NOTE_TABLE;
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while ((!cursor.isAfterLast())){
                    day_of_week = cursor.getString(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();

            }
        }
        return day_of_week;
    }

    public Note getNote(int pos) {
        Note note = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(NOTE_TABLE, new String[]{NOTE_ID, NOTE_TITLE, NOTE_CONTENT, NOTE_CREATED_DATE, NOTE_MODIFIED_DATE}, NOTE_ID + " = ? ", new String[]{String.valueOf(getId(pos))}, null, null, null, null);
        if (cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    int id_ = cursor.getInt(cursor.getColumnIndex(NOTE_ID));
                    String title_ = cursor.getString(cursor.getColumnIndex(NOTE_TITLE));
                    String content_ = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT));
                    String background_color_ = cursor.getString(cursor.getColumnIndex(NOTE_BACKGROUND_COLOR));
                    String created_date_ = cursor.getString(cursor.getColumnIndex(NOTE_CREATED_DATE));
                    String modified_date_ = cursor.getString(cursor.getColumnIndex(NOTE_MODIFIED_DATE));
                    note = new Note(id_, title_, content_, background_color_, created_date_,modified_date_);
                }
                cursor.close();
                //db.close();
                return note;
            }
        }
        return null;
    }

    public int getId(int pos) {
        return new NoteDAO(dbHelper).getAllNote().get(pos).NOTE_ID;
    }

}
