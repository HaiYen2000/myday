package com.fox.myday.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.fox.myday.Constants;
import com.fox.myday.database.DBHelper;
import com.fox.myday.models.Event;

import java.util.ArrayList;
import java.util.List;


public class EventDAO extends Constants {
    private DBHelper dbHelper;

    public EventDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public EventDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insertEvent(Event event){

        long result = -1;
        ContentValues cv = new ContentValues();
        cv.put(EVENT_ID,event.EVENT_ID);
        cv.put(EVENT_TITLE,event.EVENT_TITLE);
        cv.put(EVENT_CONTENT,event.EVENT_CONTENT);
        cv.put(EVENT_LOCATION,event.EVENT_LOCATION);

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.insert(EVENT_TABLE,null,cv);
        sqLiteDatabase.close();

        return result;

    }

    public long updateEvent(Event event){

        long result = -1;
        ContentValues cv = new ContentValues();
        cv.put(EVENT_ID,event.EVENT_ID);
        cv.put(EVENT_TITLE,event.EVENT_TITLE);
        cv.put(EVENT_CONTENT,event.EVENT_CONTENT);
        cv.put(EVENT_LOCATION,event.EVENT_LOCATION);

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.update(EVENT_TABLE, cv, EVENT_ID + "= ?", new String[]{String.valueOf(event.EVENT_ID)});
        sqLiteDatabase.close();

        return result;

    }

    public long deleteEvent(int id){

        long result = -1;

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.delete(EVENT_TABLE,EVENT_ID + " = ?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();

        return result;

    }

    public List<Event> getAllEvent(){

        List<Event> users = new ArrayList<>();

        String QUERY = "SELECT * FROM " + EVENT_TABLE;
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY,null);

        if(cursor != null){

            if(cursor.getCount() > 0){

                cursor.moveToFirst();
                while(!cursor.isAfterLast()){

                    int EVENT_ID_ = cursor.getInt(cursor.getColumnIndex(EVENT_ID));
                    String EVENT_TITLE_ = cursor.getString(cursor.getColumnIndex(EVENT_TITLE));
                    String EVENT_CONTENT_ = cursor.getString(cursor.getColumnIndex(EVENT_CONTENT));
                    String EVENT_LOCATION_ = cursor.getString(cursor.getColumnIndex(EVENT_LOCATION));

                    Event event = new Event(EVENT_ID_,EVENT_TITLE_,EVENT_CONTENT_,EVENT_LOCATION_);

                    users.add(event);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return users;
    }

    public Event getEvent(int id) {

        Event event = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(EVENT_TABLE, new String[]{EVENT_ID, EVENT_TITLE, EVENT_CONTENT, EVENT_LOCATION}, EVENT_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null, null);

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            int id_ = cursor.getInt(cursor.getColumnIndex(EVENT_ID));

            String title_ = cursor.getString(cursor.getColumnIndex(EVENT_TITLE));

            String content_ = cursor.getString(cursor.getColumnIndex(EVENT_CONTENT));

            String location_ = cursor.getString(cursor.getColumnIndex(EVENT_LOCATION));

            // khoi tao user voi cac gia tri lay duoc
            event = new Event(id_, title_, content_, location_);
        }
        cursor.close();
        return event;
    }

    public int getPosition(int id){
        int pos = 0;
        for(int i = 0;i < new EventDAO(dbHelper).getAllEvent().size();i++){
            if(new EventDAO(dbHelper).getAllEvent().get(i).EVENT_ID == id){
                pos = i;
            }
        }
        return pos;
    }
}
