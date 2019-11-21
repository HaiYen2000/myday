package com.fox.myday.models;

public class Event {
    public int EVENT_ID;
    public String EVENT_TITLE;
    public String EVENT_CONTENT;
    public String EVENT_LOCATION;

    public Event() {
    }

    public Event(String EVENT_TITLE, String EVENT_CONTENT, String EVENT_LOCATION) {
        this.EVENT_TITLE = EVENT_TITLE;
        this.EVENT_CONTENT = EVENT_CONTENT;
        this.EVENT_LOCATION = EVENT_LOCATION;
    }

    public Event(int EVENT_ID, String EVENT_TITLE, String EVENT_CONTENT, String EVENT_LOCATION) {
        this.EVENT_ID = EVENT_ID;
        this.EVENT_TITLE = EVENT_TITLE;
        this.EVENT_CONTENT = EVENT_CONTENT;
        this.EVENT_LOCATION = EVENT_LOCATION;
    }

    public int getEVENT_ID() {
        return EVENT_ID;
    }

    public void setEVENT_ID(int EVENT_ID) {
        this.EVENT_ID = EVENT_ID;
    }

    public String getEVENT_TITLE() {
        return EVENT_TITLE;
    }

    public void setEVENT_TITLE(String EVENT_TITLE) {
        this.EVENT_TITLE = EVENT_TITLE;
    }

    public String getEVENT_CONTENT() {
        return EVENT_CONTENT;
    }

    public void setEVENT_CONTENT(String EVENT_CONTENT) {
        this.EVENT_CONTENT = EVENT_CONTENT;
    }

    public String getEVENT_LOCATION() {
        return EVENT_LOCATION;
    }

    public void setEVENT_LOCATION(String EVENT_LOCATION) {
        this.EVENT_LOCATION = EVENT_LOCATION;
    }
}
