package com.fox.myday.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fox.myday.R;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class YearlyViewFragment extends Fragment {

    public YearlyViewFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yearly_view, container, false);
        Date today = new Date();
        Calendar nextyear = Calendar.getInstance();
        nextyear.add(Calendar.YEAR, 1);
        CalendarPickerView datePicker = view.findViewById(R.id.yearlyview);
        datePicker.init(today, nextyear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);
        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
                Calendar calseclected = Calendar.getInstance();
                calseclected.setTime(date);
                selectedDate = " " + calseclected.get(Calendar.DAY_OF_MONTH) + " /" + (calseclected.get(Calendar.MONTH) + 1) +
                        " /" + calseclected.get(Calendar.YEAR);
                Toast.makeText(getContext(), selectedDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
        return view;
    }
}
