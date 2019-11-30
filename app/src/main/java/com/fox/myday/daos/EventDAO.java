package com.fox.myday.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.fox.myday.Constants;
import com.fox.myday.database.DBHelper;


public class EventDAO extends Constants {

    private DBHelper dbHelper;

    public EventDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public EventDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void SaveEvent(String event, String time, String date, String month, String year, String notify) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(TIME, time);
        contentValues.put(MONTH, month);
        contentValues.put(YEAR, year);
        contentValues.put(EVENT, event);
        contentValues.put(NOTIFY, notify);
        database.insert(EVENT_TABLE, null, contentValues);

    }

    public Cursor readIDEvents(String date, String event, String time) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] Projections = {ID, NOTIFY};
        String Selection = DATE + " =? and " + EVENT + " =? and " + TIME + " = ? ";
        String[] selectionArgs = {date, event, time};
        return database.query(EVENT_TABLE, Projections, Selection, selectionArgs, null, null, null);

    }

    public Cursor readEvents(String date) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] Projections = {EVENT, TIME, DATE, MONTH, YEAR};
        String Selection = DATE + "=?";
        String[] selectionArgs = {date};
        return database.query(EVENT_TABLE, Projections, Selection, selectionArgs, null, null, null);

    }

    public Cursor readEventsperMonth(String month, String year) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] Projections = {EVENT, TIME, DATE, MONTH, YEAR};
        String Selection = MONTH + " =? and " + YEAR + " =? ";
        String[] selectionArgs = {month, year};
        return database.query(EVENT_TABLE, Projections, Selection, selectionArgs, null, null, null);

    }

    public void deleteEvent(String event, String date, String time) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String selection = EVENT + " =? and " + DATE + " = ? and " + TIME + " =? ";
        String[] selectionArg = {event, date, time};
        database.delete(EVENT_TABLE, selection, selectionArg);
    }

    public void updateEvent(String date, String event, String time, String notify) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTIFY, notify);
        String Selection = DATE + " =? and " + EVENT + " =? and " + TIME + " =? ";
        String[] selectionArgs = {date, event, time};
        database.update(EVENT_TABLE, contentValues, Selection, selectionArgs);

    }

}
