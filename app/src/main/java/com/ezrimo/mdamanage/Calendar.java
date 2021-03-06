package com.ezrimo.mdamanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar extends AppCompatActivity {
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            /**
             * when a date is chosen you go to shift page there you can assign a user
             * @param view this
             * @param year chosen year
             * @param month chosen month
             * @param dayOfMonth chosen day of month
             */
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Date myDate = new Date(year, month, dayOfMonth);
                Intent go = new Intent(Calendar.this, ShiftActivity.class);
                go.putExtra("date", myDate.getTime());//sending the date to the next Activity
                startActivity(go);
            }
        });
    }
}