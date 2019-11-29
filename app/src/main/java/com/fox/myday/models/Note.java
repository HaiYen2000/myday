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

    public int getNOTE_ID() {
        return NOTE_ID;
    }

    public void setNOTE_ID(int NOTE_ID) {
        this.NOTE_ID = NOTE_ID;
    }

    public String getNOTE_TITLE() {
        return NOTE_TITLE;
    }

    public void setNOTE_TITLE(String NOTE_TITLE) {
        this.NOTE_TITLE = NOTE_TITLE;
    }

    public String getNOTE_CONTENT() {
        return NOTE_CONTENT;
    }

    public void setNOTE_CONTENT(String NOTE_CONTENT) {
        this.NOTE_CONTENT = NOTE_CONTENT;
    }

    public String getNOTE_DATE() {
        return NOTE_DATE;
    }

    public void setNOTE_DATE(String NOTE_DATE) {
        this.NOTE_DATE = NOTE_DATE;
    }

    @Override
    public String toString() {
        return "Note{" +
                "NOTE_ID=" + NOTE_ID +
                ", NOTE_TITLE='" + NOTE_TITLE + '\'' +
                ", NOTE_CONTENT='" + NOTE_CONTENT + '\'' +
                ", NOTE_DATE='" + NOTE_DATE + '\'' +
                '}';
    }
}
