package com.fox.myday.adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.EventDay;
import com.fox.myday.R;
import com.fox.myday.base.AlarmReceiver;
import com.fox.myday.daos.EventDAO;
import com.fox.myday.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.fox.myday.Constants.ID;
import static com.fox.myday.Constants.NOTIFY;


public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Event> arrayList;
    EventDAO eventDAO;

    public EventRecyclerAdapter(Context context, ArrayList<Event> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        eventDAO = new EventDAO(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_event_rowlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Event events = arrayList.get(position);
        holder.Event.setText(events.getEvent());
        holder.DateTxt.setText(events.getDate());
        holder.Time.setText(events.getTime());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCalendarevent(events.getEvent(), events.getDate(), events.getTime());
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
        if (isAlarmed(events.getDate(), events.getEvent(), events.getTime())) {
            holder.setAlarm.setImageResource(R.drawable.ic_action_notification_on);
        } else {
            holder.setAlarm.setImageResource(R.drawable.ic_action_notification_off);
        }
        Calendar datecalendar = Calendar.getInstance();
        datecalendar.setTime(ConvertStringToDate(events.getDate()));
        final int alarmYear = datecalendar.get(Calendar.YEAR);
        final int alarmMonth = datecalendar.get(Calendar.MONTH);
        final int alarmDay = datecalendar.get(Calendar.DAY_OF_MONTH);
        Calendar timecalendar = Calendar.getInstance();
        timecalendar.setTime(ConvertStringToTime(events.getTime()));
        final int alarmHour = timecalendar.get(Calendar.HOUR_OF_DAY);
        final int alarmMinuit = timecalendar.get(Calendar.MINUTE);


        holder.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAlarmed(events.getDate(), events.getEvent(), events.getTime())) {
                    holder.setAlarm.setImageResource(R.drawable.ic_action_notification_off);
                    cancelAlarm(getRequestCode(events.getDate(), events.getEvent(), events.getTime()));
                    updateEvent(events.getDate(), events.getEvent(), events.getTime(), "off");
                    notifyDataSetChanged();
                } else {
                    holder.setAlarm.setImageResource(R.drawable.ic_action_notification_on);
                    Calendar alarmCalendar = Calendar.getInstance();
                    alarmCalendar.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinuit);
                    setAlarm(alarmCalendar, events.getEvent(), events.getTime(), getRequestCode(events.getDate(),
                            events.getTime(), events.getTime()));
                    updateEvent(events.getDate(), events.getEvent(), events.getTime(), "on");
                    notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView DateTxt, Event, Time;
        Button delete;
        ImageButton setAlarm;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            DateTxt = itemView.findViewById(R.id.eventdate);
            Event = itemView.findViewById(R.id.eventname);
            Time = itemView.findViewById(R.id.eventtime);
            delete = itemView.findViewById(R.id.delete);
            setAlarm = itemView.findViewById(R.id.alarmebtn);
        }
    }

    private Date ConvertStringToTime(String eventDate) {
        SimpleDateFormat format = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private Date ConvertStringToDate(String eventDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    private void deleteCalendarevent(String event, String date, String time) {
        eventDAO.deleteEvent(event, date, time);
    }

    private boolean isAlarmed(String date, String event, String time) {
        boolean alarmed = false;
        Cursor cursor = eventDAO.readIDEvents(date, event, time);
        while (cursor.moveToNext()) {
            String notify = cursor.getString(cursor.getColumnIndex(NOTIFY));
            if (notify.equals("on")) {
                alarmed = true;
            } else {
                alarmed = false;
            }
        }
        return alarmed;
    }

    private void cancelAlarm(int RequestCode) {
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RequestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private void setAlarm(Calendar calendar, String event, String time, int RequestCode) {
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("event", event);
        intent.putExtra("time", time);
        intent.putExtra("id", RequestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RequestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    private int getRequestCode(String date, String event, String time) {
        int code = 0;
        Cursor cursor = eventDAO.readIDEvents(date, event, time);
        while (cursor.moveToNext()) {
            code = cursor.getInt(cursor.getColumnIndex(ID));

        }
        return code;
    }

    private void updateEvent(String date, String event, String time, String notify) {
        eventDAO.updateEvent(date, event, time, notify);
    }
}
