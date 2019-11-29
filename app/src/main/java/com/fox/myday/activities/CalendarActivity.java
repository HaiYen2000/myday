package com.fox.myday.activities;

import androidx.core.view.GravityCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.fox.myday.CustomCalendarView;
import com.fox.myday.R;
import com.fox.myday.base.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CalendarActivity extends BaseActivity {
    AlertDialog alertDialog;
    FloatingActionButton fab;
    CustomCalendarView customCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_calendar, null, false);
        drawerLayout.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_calendar);
        setTitle(R.string.menu_calendar);
        customCalendarView = findViewById(R.id.customcalendaview);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        DailyViewFragment dailyViewFragment = new DailyViewFragment();
//        fragmentTransaction.add(R.id.frlcalendarview, dailyViewFragment);
//        fragmentTransaction.commit();
//        fab = findViewById(R.id.fabadd);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                AddEventFragment addEventFragment = new AddEventFragment();
//                fragmentTransaction.replace(R.id.frlcalendarview, addEventFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                super.onBackPressed();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.calendar_view_group, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.viewcalendar:
//                final AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
//                final View alert = LayoutInflater.from(CalendarActivity.this).inflate(R.layout.dialog_calendar_view_group, null);
//                builder.setView(alert);
//
//                RadioGroup radioGroup = alert.findViewById(R.id.rdgcalendar);
//
//                RadioButton rdoviewday = (RadioButton) alert.findViewById(R.id.rdoviewday);
//                RadioButton rdoviewweek = (RadioButton) alert.findViewById(R.id.rdoviewweek);
//                RadioButton rdoviewmonth = (RadioButton) alert.findViewById(R.id.rdoviewmonth);
//                RadioButton rdoviewyear = (RadioButton) alert.findViewById(R.id.rdoviewyear);
//                RadioButton rdoeventlist = (RadioButton) alert.findViewById(R.id.rdoeventlist);
//
//                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                        doOnDifficultyLevelChanged(radioGroup, i);
//
//                    }
//                });
//
//                rdoviewday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        doOnChangeCalendarView(compoundButton, b);
//
//                    }
//                });
//                rdoviewweek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        doOnChangeCalendarView(compoundButton, b);
//                    }
//                });
//
//                rdoviewmonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        doOnChangeCalendarView(compoundButton, b);
//
//                    }
//                });
//
//                rdoeventlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        doOnChangeCalendarView(compoundButton, b);
//
//                    }
//                });
//                rdoviewyear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        doOnChangeCalendarView(compoundButton, b);
//                    }
//                });
//
//                builder.create().show();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void doOnChangeCalendarView(CompoundButton button, boolean isChecked) {
//        RadioButton radioButton = (RadioButton) button;
//        Log.i("radiobutton", "RadioButton " + radioButton.getText() + " : " + isChecked);
//    }
//
//    private void doOnDifficultyLevelChanged(RadioGroup group, int checkedId) {
//        int checkRadioId = group.getCheckedRadioButtonId();
//
//        if (checkRadioId == R.id.rdoviewday) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            DailyViewFragment dailyViewFragment = new DailyViewFragment();
//            fragmentTransaction.replace(R.id.frlcalendarview, dailyViewFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//
//
//        } else if (checkRadioId == R.id.rdoviewweek) {
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            WeeklyViewFragment weeklyViewFragment = new WeeklyViewFragment();
//            fragmentTransaction.replace(R.id.frlcalendarview, weeklyViewFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        } else if (checkRadioId == R.id.rdoviewmonth) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            MonthlyViewFragment monthlyViewFragment = new MonthlyViewFragment();
//            fragmentTransaction.replace(R.id.frlcalendarview, monthlyViewFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//
//        } else if (checkRadioId == R.id.rdoviewyear) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            YearlyViewFragment yearlyViewFragment = new YearlyViewFragment();
//            fragmentTransaction.replace(R.id.frlcalendarview, yearlyViewFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//
//        } else if (checkRadioId == R.id.rdoeventlist) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            SimpleEventsListFragment simpleEventsListFragment = new SimpleEventsListFragment();
//            fragmentTransaction.replace(R.id.frlcalendarview, simpleEventsListFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        }
//    }

}
