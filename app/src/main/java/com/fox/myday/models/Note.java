package com.fox.myday.models;

public class Note {

    public int NOTE_ID;
    public String NOTE_TITLE;
    public String NOTE_CONTENT;
    public String NOTE_DATE;

    public Note(){}

    public Note(String NOTE_TITLE, String NOTE_CONTENT, String NOTE_DATE) {
        this.NOTE_TITLE = NOTE_TITLE;
        this.NOTE_CONTENT = NOTE_CONTENT;
        this.NOTE_DATE = NOTE_DATE;
    }

    public Note(int NOTE_ID, String NOTE_TITLE, String NOTE_CONTENT, String NOTE_DATE) {
        this.NOTE_ID = NOTE_ID;
        this.NOTE_TITLE = NOTE_TITLE;
        this.NOTE_CONTENT = NOTE_CONTENT;
        this.NOTE_DATE = NOTE_DATE;
    }

}
