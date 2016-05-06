package com.benrcarvergmail.cvhsmobileapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.imanoweb.calendarview.CustomCalendarView;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by Benjamin on 5/3/2016.
 */
public class ScheduleActivity extends FragmentActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener {

    // References to buttons
    private Button mButtonCreateEvent;
    private Button mButtonEditEvent;
    private Button mButtonDeleteEvent;
    private Button mButtonSelectDate;
    private Button mButtonConfirm;
    private Button mButtonCancel;

    // Reference to input text boxes
    private EditText mEditTextTitle;
    private EditText mEditTextDesc;

    // Reference to the headers for the create/edit event box
    private TextView mTextViewCreateEvent;
    private TextView mTextViewEditEvent;

    // Reference to the custom calendar view itself
    private CustomCalendarView calendarView;

    // Reference to the ScrollView that holds the tools for creating/editing an event
    private ScrollView mScrollView;

    private final static String TAG = "ScheduleActivity";
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call the super onCreate method, passing savedInstanceState
        super.onCreate(savedInstanceState);
        // Set the content view (XML file to render what the user sees) to activity_main.xml
        setContentView(R.layout.activity_schedule);

        mButtonCreateEvent = (Button) findViewById(R.id.button_create_calendar_event);
        mButtonEditEvent = (Button) findViewById(R.id.button_edit_calendar_event);
        mButtonDeleteEvent = (Button) findViewById(R.id.button_delete_calendar_event);
        mButtonSelectDate = (Button) findViewById(R.id.button_event_tools_pick_date);
        mButtonConfirm = (Button) findViewById(R.id.button_event_tools_confirm);
        mButtonCancel = (Button) findViewById(R.id.button_event_tools_cancel);

        mEditTextTitle = (EditText) findViewById(R.id.edit_text_event_tools_title);
        mEditTextDesc = (EditText) findViewById(R.id.edit_text_event_tools_desc);

        mTextViewCreateEvent = (TextView) findViewById(R.id.text_view_event_tools_title_create);
        mTextViewEditEvent = (TextView) findViewById(R.id.text_view_event_tools_title_edit);

        calendarView = (CustomCalendarView) findViewById(R.id.calendar_view);

        mScrollView = (ScrollView) findViewById(R.id.scroll_view_event_tools_container);

        // Create Event onClickListener
        mButtonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.setVisibility(View.VISIBLE);
                mTextViewCreateEvent.setVisibility(View.VISIBLE);
                mTextViewEditEvent.setVisibility(View.GONE);
            }
        });

        // Edit Event onClickListener
        mButtonEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.setVisibility(View.VISIBLE);
                mTextViewCreateEvent.setVisibility(View.GONE);
                mTextViewEditEvent.setVisibility(View.VISIBLE);

                // Toast.makeText(getApplicationContext(), "Press 'confirm' to confirm your changes. Press 'cancel' to discard them.",
                        // Toast.LENGTH_SHORT).show();
            }
        });

        // Delete Event onClickListener
        mButtonDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ScheduleActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Event")
                        .setMessage("Are you sure you want to delete this event?")
                        .setPositiveButton("Yes, delete", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Event deleted",
                                        Toast.LENGTH_SHORT).show();
                            }

                        })
                        .setNegativeButton("No, nevermind", null)
                        .show();
            }
        });

        // Date Select Event onClickListener
        mButtonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Please select the date for your event.",
                        Toast.LENGTH_SHORT).show();

                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(ScheduleActivity.this)
                        .setThemeLight();
                cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });

        // Confirm Edits/Creation of Event onClickListener
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.setVisibility(View.GONE);
                mTextViewCreateEvent.setVisibility(View.GONE);
                mTextViewEditEvent.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "Event created.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Cancel Edits/Creation of Event onClickListener
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.setVisibility(View.GONE);
                mTextViewCreateEvent.setVisibility(View.GONE);
                mTextViewEditEvent.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "Event edit/creation canceled.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear,
                                                                                int dayOfMonth) {
        // We add 1 to monthOfYear when displaying the Toast message because monthOfYear begins at 0
        // for January, meaning we need to add 1 to the value so it represents the month normally
        Toast.makeText(this, "Date Selected: " + (monthOfYear + 1) + "/" + dayOfMonth + "/" + year,
                                                                        Toast.LENGTH_SHORT).show();

        // String title, String desc, SimpleDateFormat date, SimpleDateFormat dateCreated,
        // boolean isHomework, boolean isTest, boolean isProj, boolean isQuiz, boolean isBirthday
        ScheduledEvent newEvent = new ScheduledEvent("Title", "Desc", null, null, false, false, false, false, false);
    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }
    }
}
