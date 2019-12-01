package com.fox.myday.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fox.myday.Constants;
import com.fox.myday.database.DBHelper;
import com.fox.myday.models.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagDAO extends Constants {

    private DBHelper dbHelper;

    public TagDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public TagDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insertTagWithId(Tag tag) {
        long result = -1;
        ContentValues cv = new ContentValues();
        cv.put(TAG_ID,tag.TAG_ID);
        cv.put(TAG_NAME,tag.TAG_NAME);
        cv.put(TAG_DESCRIPTION,tag.TAG_DESCRIPTION);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.insert(TAG_TABLE, null, cv);
        sqLiteDatabase.close();
        return result;
    }

    public long insertTag(Tag tag) {
        long result = -1;
        ContentValues cv = new ContentValues();
        //cv.put(NOTE_ID,note.NOTE_ID);
        cv.put(TAG_NAME,tag.TAG_NAME);
        cv.put(TAG_DESCRIPTION,tag.TAG_DESCRIPTION);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.insert(TAG_TABLE, null, cv);
        sqLiteDatabase.close();
        return result;
    }

    public long updateTag(Tag tag) {
        long result = -1;
        ContentValues cv = new ContentValues();
        cv.put(TAG_ID,tag.TAG_ID);
        cv.put(TAG_NAME,tag.TAG_NAME);
        cv.put(TAG_DESCRIPTION,tag.TAG_DESCRIPTION);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.update(TAG_TABLE, cv, TAG_ID + "= ?", new String[]{String.valueOf(tag.TAG_ID)});
        sqLiteDatabase.close();
        return result;
    }

    public long deleteTag(int id) {
        long result = -1;
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        result = sqLiteDatabase.delete(TAG_TABLE, TAG_ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();

        return result;

    }

    public List<Tag> getAllTag() {
        List<Tag> tags = new ArrayList<>();
        String QUERY = "SELECT * FROM " + TAG_TABLE;
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    int TAG_ID_ = cursor.getInt(cursor.getColumnIndex(TAG_ID));
                    String TAG_NAME_ = cursor.getString(cursor.getColumnIndex(TAG_NAME));
                    String TAG_DESCRIPTION_ = cursor.getString(cursor.getColumnIndex(TAG_DESCRIPTION));
                    Tag tag = new Tag(TAG_ID_,TAG_NAME_,TAG_DESCRIPTION_);
                    tags.add(tag);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return tags;
    }

}
