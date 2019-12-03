package com.fox.myday.models;

public class Note {

    public int NOTE_ID;
    public String NOTE_TITLE;
    public String NOTE_CONTENT;
    public int NOTE_IMAGE_URI;
    public String NOTE_BACKGROUND_COLOR;
    public int NOTE_TAG;
    public String NOTE_CREATED_DATE;
    public String NOTE_MODIFIED_DATE;

    public Note(){}

    //Create
    public Note(String NOTE_TITLE, String NOTE_CONTENT, String NOTE_BACKGROUND_COLOR, String NOTE_CREATED_DATE) {
        this.NOTE_TITLE = NOTE_TITLE;
        this.NOTE_CONTENT = NOTE_CONTENT;
        this.NOTE_BACKGROUND_COLOR = NOTE_BACKGROUND_COLOR;
        this.NOTE_CREATED_DATE = NOTE_CREATED_DATE;
    }

    //Update
    public Note(int NOTE_ID, String NOTE_TITLE, String NOTE_CONTENT, String NOTE_BACKGROUND_COLOR, String NOTE_CREATED_DATE, String NOTE_MODIFIED_DATE) {
        this.NOTE_ID = NOTE_ID;
        this.NOTE_TITLE = NOTE_TITLE;
        this.NOTE_CONTENT = NOTE_CONTENT;
        this.NOTE_BACKGROUND_COLOR = NOTE_BACKGROUND_COLOR;
        this.NOTE_CREATED_DATE = NOTE_CREATED_DATE;
        this.NOTE_MODIFIED_DATE = NOTE_MODIFIED_DATE;
    }
}
