package com.fox.myday.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fox.myday.Constants;

import static com.fox.myday.Constants.CREATE_EVENTS_TABLE;
import static com.fox.myday.Constants.CREATE_NOTE_TABLE;
import static com.fox.myday.Constants.DATABASE_NAME;
import static com.fox.myday.Constants.DATABASE_VERSION;
import static com.fox.myday.Constants.EVENT_TABLE;
import static com.fox.myday.Constants.NOTE_TABLE;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (Constants.isCreated) {
            Log.i("CREATE_NOTE_TABLE", CREATE_NOTE_TABLE);
<<<<<<< HEAD
            Log.i("CREATE_EVENT_TABLE", CREATE_EVENTS_TABLE);

=======
            Log.i("CREATE_EVENT_TABLE", CREATE_EVENT_TABLE);
>>>>>>> 08fa8e68fcfb27cc93206bdfa24f8dd425ff1308
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
<<<<<<< HEAD
        sqLiteDatabase.execSQL(CREATE_EVENTS_TABLE);

=======
        sqLiteDatabase.execSQL(CREATE_EVENT_TABLE);
>>>>>>> 08fa8e68fcfb27cc93206bdfa24f8dd425ff1308
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
        onCreate(sqLiteDatabase);
    }

}
