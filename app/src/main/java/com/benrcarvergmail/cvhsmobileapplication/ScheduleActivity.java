package com.benrcarvergmail.cvhsmobileapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.imanoweb.calendarview.CustomCalendarView;

import java.util.Calendar;

/**
 * Created by Benjamin on 5/3/2016.
 */
public class ScheduleActivity extends Activity {

    // References to buttons
    private Button mButtonCreateEvent;
    private Button mButtonEditEvent;
    private Button mButtonDeleteEvent;

    // Reference to the custom calendar view itself
    private CustomCalendarView calendarView;

    private final static String TAG = "ScheduleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call the super onCreate method, passing savedInstanceState
        super.onCreate(savedInstanceState);
        // Set the content view (XML file to render what the user sees) to activity_main.xml
        setContentView(R.layout.activity_schedule);


    }

}
