package com.fox.myday.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fox.myday.Constants;

import static com.fox.myday.Constants.CREATE_EVENTS_TABLE;
import static com.fox.myday.Constants.CREATE_NOTE_TABLE;
import static com.fox.myday.Constants.EVENT_TABLE;
import static com.fox.myday.Constants.NOTE_TABLE;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "myday.sql", null, 1);
        if (Constants.isCreated) {
            Log.i("CREATE_NOTE_TABLE", CREATE_NOTE_TABLE);
            Log.i("CREATE_EVENT_TABLE", CREATE_EVENTS_TABLE);

        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
        sqLiteDatabase.execSQL(CREATE_EVENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);

    }

}
