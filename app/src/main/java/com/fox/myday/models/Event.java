package com.fox.myday.models;

public class Event {
    String event,time,date,month,year;

    public Event(String event, String time, String date, String month, String year) {
        this.event = event;
        this.time = time;
        this.date = date;
        this.month = month;
        this.year = year;
    }
    public Event(){}

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Events{" +
                "event='" + event + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
