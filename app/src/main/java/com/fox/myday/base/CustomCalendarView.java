package com.fox.myday.base;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.myday.R;
import com.fox.myday.adapters.EventRecyclerAdapter;
import com.fox.myday.adapters.MyGridAdapter;
import com.fox.myday.daos.EventDAO;
import com.fox.myday.database.DBHelper;
import com.fox.myday.models.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.fox.myday.Constants.DATE;
import static com.fox.myday.Constants.EVENT;
import static com.fox.myday.Constants.ID;
import static com.fox.myday.Constants.MONTH;
import static com.fox.myday.Constants.TIME;
import static com.fox.myday.Constants.YEAR;


public class CustomCalendarView extends LinearLayout {

    DBHelper dbHelper;
    EventDAO eventDAO;
    ImageButton nextButton, previousButton;
    TextView currentdate;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    //MyGrid Adapter
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    SimpleDateFormat eventDateFormate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    AlertDialog alertDialog;
    MyGridAdapter myGridAdapter;

    List<Date> dates = new ArrayList<>();
    List<Event> eventsList = new ArrayList<>();
    int alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinut;


    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        eventDAO = new EventDAO(context);
        IntializeLayout();
        setupCalendar();
        previousButton.setOnClickListener(view -> {
            calendar.add(Calendar.MONTH, -1);
            setupCalendar();
        });
        nextButton.setOnClickListener(view -> {
            calendar.add(Calendar.MONTH, 1);
            setupCalendar();
        });

        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            final View addView = LayoutInflater.from(adapterView.getContext()).inflate(R.layout.add_newevent_layout, null);
            final EditText eventname = addView.findViewById(R.id.edttypeevent);
            final TextView eventtime = addView.findViewById(R.id.textView);
            ImageButton settime = addView.findViewById(R.id.imbseteventtime);
            final CheckBox alarmMe = addView.findViewById(R.id.alarme);
            Calendar dateCalendar = Calendar.getInstance();
            dateCalendar.setTime(dates.get(i));
            alarmYear = dateCalendar.get(Calendar.YEAR);
            alarmMonth = dateCalendar.get(Calendar.MONTH);
            alarmDay = dateCalendar.get(Calendar.DAY_OF_MONTH);

            Button btnaddevent = addView.findViewById(R.id.btnaddevent);

            settime.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    int hours = calendar.get(Calendar.HOUR_OF_DAY);
                    int minuts = calendar.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new
                            TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.HOUR_OF_DAY, i);
                            c.set(Calendar.MINUTE, i1);
                            c.setTimeZone(TimeZone.getDefault());
                            SimpleDateFormat hformate = new SimpleDateFormat("K:mm a", Locale.ENGLISH);
                            String Eventtime = hformate.format(c.getTime());
                            eventtime.setText(Eventtime);
                            alarmHour = c.get(Calendar.HOUR_OF_DAY);
                            alarmMinut = c.get(Calendar.MINUTE);

                        }
                    }, hours, minuts, false);
                    timePickerDialog.show();
                }
            });
            final String date = eventDateFormate.format(dates.get(i));
            final String month = monthFormat.format(dates.get(i));
            final String year = yearFormat.format(dates.get(i));


            btnaddevent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alarmMe.isChecked()) {
                        SaveEvent(eventname.getText().toString(), eventtime.getText().toString(), date, month, year, "on");
                        setupCalendar();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinut);
                        setAlarm(calendar, eventname.getText().toString(), eventtime.getText().toString(),
                                getRequestCode(date,
                                        eventname.getText().toString(), eventtime.getText().toString()));
                        alertDialog.dismiss();
                    } else
                        SaveEvent(eventname.getText().toString(), eventtime.getText().toString(), date, month, year, "off");
                    setupCalendar();
                    alertDialog.dismiss();
                    {
                    }
                }
            });
            builder.setView(addView);
            alertDialog = builder.create();
            alertDialog.show();
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                String date = eventDateFormate.format(dates.get(i));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View showView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_layout, null);
                RecyclerView recyclerView = showView.findViewById(R.id.EventsRV);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext(),
                        CollectEventByDate(date));
                recyclerView.setAdapter(eventRecyclerAdapter);
                eventRecyclerAdapter.notifyDataSetChanged();
                builder.setView(showView);
                alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        setupCalendar();
                    }
                });

                return true;
            }
        });
    }

    private int getRequestCode(String date, String event, String time) {
        int code = 0;
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = eventDAO.readIDEvents(date, event, time);
        while (cursor.moveToNext()) {
            code = cursor.getInt(cursor.getColumnIndex(ID));

        }
        cursor.close();
        database.close();
        return code;
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

    private ArrayList<Event> CollectEventByDate(String date) {
        ArrayList<Event> arrayList = new ArrayList<>();
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = eventDAO.readEvents(date);
        while (cursor.moveToNext()) {
            String event = cursor.getString(cursor.getColumnIndex(EVENT));
            String time = cursor.getString(cursor.getColumnIndex(TIME));
            String Date = cursor.getString(cursor.getColumnIndex(DATE));
            String month = cursor.getString(cursor.getColumnIndex(MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(YEAR));
            Event events = new Event(event, time, Date, month, Year);
            arrayList.add(events);
        }
        cursor.close();
        database.close();
        return arrayList;
    }


    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void IntializeLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);

        nextButton = view.findViewById(R.id.imbnext);
        currentdate = view.findViewById(R.id.tvcurrent);
        previousButton = view.findViewById(R.id.imbprevious);
        gridView = view.findViewById(R.id.gridView);
    }

    private void SaveEvent(String event, String time, String date, String month, String year, String notify) {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        eventDAO.SaveEvent(event, time, date, month, year, notify);
        database.close();
        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();
    }

    private void setupCalendar() {
        String currentDate = dateFormat.format(calendar.getTime());
        currentdate.setText(currentDate);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstdayofmonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstdayofmonth);
        CollectEventsPerMonth(monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));
        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        myGridAdapter = new MyGridAdapter(context, dates, calendar, eventsList);
        gridView.setAdapter(myGridAdapter);
    }

    private void CollectEventsPerMonth(String Month, String year) {
        eventsList.clear();
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = eventDAO.readEventsperMonth(Month, year);
        while (cursor.moveToNext()) {
            String event = cursor.getString(cursor.getColumnIndex(EVENT));
            String time = cursor.getString(cursor.getColumnIndex(TIME));
            String date = cursor.getString(cursor.getColumnIndex(DATE));
            String month = cursor.getString(cursor.getColumnIndex(MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(YEAR));
            Event events = new Event(event, time, date, month, Year);
            eventsList.add(events);
        }
        cursor.close();
        database.close();
    }
}
