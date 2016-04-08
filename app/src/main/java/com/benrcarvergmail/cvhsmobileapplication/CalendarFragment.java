package com.benrcarvergmail.cvhsmobileapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;


/**
 * Calender implementation.
 * Authors: Ben Carver & Austin Mysinger
 */
public class CalendarFragment extends Fragment {

    CalendarView calendar;

    public CalendarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        initializeCalendar(rootView);
        return rootView;
    }

    // Initialize features of the calendar
    private void initializeCalendar(View v) {
        calendar = (CalendarView) v.findViewById(R.id.calendar);
        calendar.setFirstDayOfWeek(2);          // Set Monday to be the first day of the week
        calendar.setMinDate(1456837813000L);    // 1457356035000 = 3/1/2016

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(view.getContext(),
                        "Year: " + year + "\n" +
                                "Month: " + month + "\n" +
                                "Day of Month: " + dayOfMonth,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
