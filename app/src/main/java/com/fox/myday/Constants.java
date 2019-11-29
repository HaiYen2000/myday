package com.fox.myday;

public class Constants {

    public static final boolean isCreated = true;

    //Config the database
    public static final String DATABASE_NAME = "myday.db";
    public static final int DATABASE_VERSION = 1;

    //Note Table
    public static final String NOTE_TABLE = "note";
    //Column
    public static final String NOTE_ID = "id";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_CONTENT = "content";
    public static final String NOTE_CREATED_DATE = "created_date";
    public static final String NOTE_MODIFIED_DATE = "modified_date";

    //Query create note(id(integer primary key autoincrement), title (nvarchar(255)), content (nvarchar(255) not null), created_date (text not null), modified_date(text))
    public static final String CREATE_NOTE_TABLE = "CREATE TABLE " + NOTE_TABLE + "(" +
            "" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "" + NOTE_TITLE + " TEXT," +
            "" + NOTE_CONTENT + " TEXT NOT NULL," +
            "" + NOTE_CREATED_DATE + " TEXT NOT NULL," +
            "" + NOTE_MODIFIED_DATE + " TEXT" +
            ")";

    public static final int NUM_COLUMNS = 2;

    public static int SPLASH_TIME_OUT = 8600;
    public static int FRAGMENT_TIME_OUT = 2000;
    public static int NAVIGATE_TIME_OUT = 1500;

    public static final String DEFAULT_CITY = "Hanoi";
    public static final String DEFAULT_LAT = "21.0245";
    public static final String DEFAULT_LON = "105.841171";
    public static final String DEFAULT_CITY_ID = "1581130";
    public static final int DEFAULT_ZOOM_LEVEL = 7;

    public static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;

    // Time in milliseconds; only reload weather if last update is longer ago than this value
    public static final int NO_UPDATE_REQUIRED_THRESHOLD = 300000;
    //Event Table
    public static final String EVENT_TABLE = "event";
    //Column event

    public static final String EVENT_ID = "id";
    public static final String EVENT_TITLE = "title";
    public static final String EVENT_CONTENT = "content";
    public static final String EVENT_LOCATION= "created_location";
    //Query create event(id(integer primary key autoincrement), title (nvarchar(255)), content (nvarchar(255) not null), created_location (text not null)

    public static final String CREATE_EVENT_TABLE = "CREATE TABLE " + EVENT_TABLE + "(" +
            "" + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "" + EVENT_TITLE + " TEXT," +
            "" + EVENT_CONTENT + " TEXT NOT NULL," +
            "" + EVENT_LOCATION + " TEXT NOT NULL" +
            ")";

    public static final String API_KEY = BuildConfig.API_KEY;

    public static int ORDER_REGISTER_STATE = 0;
    public static int ORDER_LOGIN_STATE = 1;

    public static String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    //Maximum sound stream
    public static final int MAX_STREAM = 5;

}
