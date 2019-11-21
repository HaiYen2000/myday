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

    public long insertNote(Note note){

        long result = -1;
        ContentValues cv = new ContentValues();
        cv.put(NOTE_ID,note.NOTE_ID);
        cv.put(NOTE_TITLE,note.NOTE_TITLE);
        cv.put(NOTE_CONTENT,note.NOTE_CONTENT);
        cv.put(NOTE_DATE,note.NOTE_DATE);

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.insert(NOTE_TABLE,null,cv);
        sqLiteDatabase.close();

        return result;

    }

    public long updateNote(Note note){

        long result = -1;
        ContentValues cv = new ContentValues();
        cv.put(NOTE_ID,note.NOTE_ID);
        cv.put(NOTE_TITLE,note.NOTE_TITLE);
        cv.put(NOTE_CONTENT,note.NOTE_CONTENT);
        cv.put(NOTE_DATE,note.NOTE_DATE);

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.update(NOTE_TABLE, cv, NOTE_ID + "= ?", new String[]{String.valueOf(note.NOTE_ID)});
        sqLiteDatabase.close();

        return result;

    }

    public long deleteNote(int id){

        long result = -1;

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.delete(NOTE_TABLE,NOTE_ID + " = ?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();

        return result;

    }

    public List<Note> getAllNote(){

        List<Note> users = new ArrayList<>();

        String QUERY = "SELECT * FROM " + NOTE_TABLE;
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY,null);

        if(cursor != null){

            if(cursor.getCount() > 0){

                cursor.moveToFirst();
                while(!cursor.isAfterLast()){

                    int NOTE_ID_ = cursor.getInt(cursor.getColumnIndex(NOTE_ID));
                    String NOTE_TITLE_ = cursor.getString(cursor.getColumnIndex(NOTE_TITLE));
                    String NOTE_CONTENT_ = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT));
                    String NOTE_DATE_ = cursor.getString(cursor.getColumnIndex(NOTE_DATE));

                    Note note = new Note(NOTE_ID_,NOTE_TITLE_,NOTE_CONTENT_,NOTE_DATE_);

                    users.add(note);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return users;
    }

    public Note getNote(int id) {

        Note note = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(NOTE_TABLE, new String[]{NOTE_ID, NOTE_TITLE, NOTE_CONTENT, NOTE_DATE}, NOTE_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null, null);

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            int id_ = cursor.getInt(cursor.getColumnIndex(NOTE_ID));

            String title_ = cursor.getString(cursor.getColumnIndex(NOTE_TITLE));

            String content_ = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT));

            String date_ = cursor.getString(cursor.getColumnIndex(NOTE_DATE));

            // khoi tao user voi cac gia tri lay duoc
            note = new Note(id_, title_, content_, date_);
        }
        cursor.close();
        return note;
    }

    public int getPosition(int id){
        int pos = 0;
        for(int i = 0;i < new NoteDAO(dbHelper).getAllNote().size();i++){
            if(new NoteDAO(dbHelper).getAllNote().get(i).NOTE_ID == id){
                pos = i;
            }
        }
        return pos;
    }

}
