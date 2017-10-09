package com.example.lance.calenderdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NewCalender.NewCalendarListener{

    private NewCalender newCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        newCalender = (NewCalender) findViewById(R.id.newCalendar);
        newCalender.listener = this;
    }

    @Override
    public void onItemLongPress(Date date) {
        DateFormat df = SimpleDateFormat.getDateInstance();
        Toast.makeText(this,df.format(date),Toast.LENGTH_SHORT).show();
    }
}
