package com.fox.myday;

public class Constants {
    public static final boolean isCreated = true;

    //Config the database
    public static final String DATABASE_NAME = "myday.db";
    public static final int DATABASE_VERSION = 1;

    //Tag table
    public static final String TAG_TABLE = "tag";
    //Column
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_DESCRIPTION = "description";
    //Query create tag(id(integer primary key autoincrement), name text not null, description text)
    public static final String CREATE_TAG_TABLE = "CREATE TABLE " + TAG_TABLE + "(" +
            "" + TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "" + TAG_NAME + " TEXT NOT NULL," +
            "" + TAG_DESCRIPTION + " TEXT" +
            ")";

    //Note Table
    public static final String NOTE_TABLE = "note";
    //Column
    public static final String NOTE_ID = "id";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_CONTENT = "content";
    public static final String NOTE_CREATED_DATE = "created_date";
    public static final String NOTE_MODIFIED_DATE = "modified_date";
    public static final String NOTE_IMAGE_URI = "image_uri";
    public static final String NOTE_BACKGROUND_COLOR = "color";
    public static final String NOTE_TAG_ID = "tag_id";


    //Query create note(id(integer primary key autoincrement), title text, content text not null, created_date text not null, modified_date text)
    public static final String CREATE_NOTE_TABLE = "CREATE TABLE " + NOTE_TABLE + "(" +
            "" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "" + NOTE_TITLE + " TEXT," +
            "" + NOTE_CONTENT + " TEXT NOT NULL," +
            "" + NOTE_IMAGE_URI + " INTEGER," +
            "" + NOTE_BACKGROUND_COLOR + " TEXT," +
            "" + NOTE_CREATED_DATE + " TEXT NOT NULL," +
            "" + NOTE_MODIFIED_DATE + " TEXT," +
            "" + NOTE_TAG_ID + " INTEGER," +
            "" + " FOREIGN KEY (" + NOTE_TAG_ID + ") REFERENCES " + TAG_TABLE + "(" + TAG_ID + ")" +
            ")";

    //Event Table
    public static final String EVENT_TABLE = "events";
    //Column event
    public static final String EVENT = "event";
    public static final String TIME = "time";
    public static final String DATE = "date";
    public static final String MONTH = "month";
    public static final String YEAR = "year";

    public static final String ID = "ID";
    public static final String NOTIFY = "notify";
    //Query create event(id(integer primary key autoincrement), title (nvarchar(255)), content (nvarchar(255) not null), created_location (text not null))
    public static final String CREATE_EVENT_TABLE = "create table " +
            EVENT_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EVENT + " TEXT," + TIME + " TEXT,"
            + DATE + " TEXT," + MONTH + " TEXT,"
            + YEAR + " TEXT," + NOTIFY + " TEXT)";


    //StaggeredLayoutColumn
    public static final int NUM_COLUMNS = 2;

    //Timeout for navigate
    public static int SPLASH_TIME_OUT = 8600;
    public static int FRAGMENT_TIME_OUT = 2000;
    public static int NAVIGATE_TIME_OUT = 1500;

    //Location config for weather function
    public static final String DEFAULT_CITY = "Hanoi";
    public static final String DEFAULT_LAT = "21.0245";
    public static final String DEFAULT_LON = "105.841171";
    public static final String DEFAULT_CITY_ID = "1581130";
    public static final int DEFAULT_ZOOM_LEVEL = 7;

    //Permission to location service
    public static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;

    // Time in milliseconds; only reload weather if last update is longer ago than this value
    public static final int NO_UPDATE_REQUIRED_THRESHOLD = 300000;

    //OpenWeatherMap API
    public static final String API_KEY = BuildConfig.API_KEY;

    //State for toggle login/register
    public static int ORDER_REGISTER_STATE = 0;
    public static int ORDER_LOGIN_STATE = 1;

    //REGEX
    public static String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    //Maximum sound stream
    public static final int MAX_STREAM = 5;
}
