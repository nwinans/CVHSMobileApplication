package com.benrcarvergmail.cvhsmobileapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.app.AlertDialog;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;
import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Benjamin on 5/3/2016.
 */
public class ScheduleActivity extends FragmentActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener {

    private Button mButtonEditEvent;
    private Button mButtonDeleteEvent;

    // References to check boxes
    private CheckBox mCheckBoxHomework;         // The checkbox for homework
    private CheckBox mCheckBoxProject;          // The checkbox for project
    private CheckBox mCheckBoxBirthday;         // The checkbox for birthday
    private CheckBox mCheckBoxTest;             // The checkbox for test
    private CheckBox mCheckBoxQuiz;             // The checkbox for quiz
    private CheckBox mCheckBoxOther;            // The checkbox for other

    // Reference to input text boxes
    private EditText mEditTextTitle;
    private EditText mEditTextDesc;

    // Reference to the headers for the create/edit event box
    private TextView mTextViewCreateEvent;
    private TextView mTextViewEditEvent;
    private TextView mTextViewCurrentDate;

    // Reference to the custom calendar view itself
    private CustomCalendarView mCalendarView;

    // Reference to the ScrollView that holds the tools for creating/editing an event
    private ScrollView mScrollView;

    // Used for the creation of an event. Updated each time an event is created.
    private String mNewestDateString = "";
    private Date mNewestDateDate = new Date();

    private final static String TAG = "ScheduleActivity";
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call the super onCreate method, passing savedInstanceState
        super.onCreate(savedInstanceState);
        // Set the content view (XML file to render what the user sees) to activity_main.xml
        setContentView(R.layout.activity_schedule);

        Button mButtonCreateEvent = (Button) findViewById(R.id.button_create_calendar_event);
        mButtonEditEvent = (Button) findViewById(R.id.button_edit_calendar_event);
        mButtonDeleteEvent = (Button) findViewById(R.id.button_delete_calendar_event);
        Button mButtonSelectDate = (Button) findViewById(R.id.button_event_tools_pick_date);
        Button mButtonConfirm = (Button) findViewById(R.id.button_event_tools_confirm);
        Button mButtonCancel = (Button) findViewById(R.id.button_event_tools_cancel);

        mCheckBoxHomework = (CheckBox) findViewById(R.id.checkbox_homework);
        mCheckBoxBirthday = (CheckBox) findViewById(R.id.checkbox_birthday);
        mCheckBoxOther = (CheckBox) findViewById(R.id.checkbox_other);
        mCheckBoxProject = (CheckBox) findViewById(R.id.checkbox_project);
        mCheckBoxQuiz = (CheckBox) findViewById(R.id.checkbox_quiz);
        mCheckBoxTest = (CheckBox) findViewById(R.id.checkbox_test);

        mEditTextTitle = (EditText) findViewById(R.id.edit_text_event_tools_title);
        mEditTextDesc = (EditText) findViewById(R.id.edit_text_event_tools_desc);

        mTextViewCreateEvent = (TextView) findViewById(R.id.text_view_event_tools_title_create);
        mTextViewEditEvent = (TextView) findViewById(R.id.text_view_event_tools_title_edit);
        mTextViewCurrentDate = (TextView) findViewById(R.id.text_view_event_tools_current_date);

        mCalendarView = (CustomCalendarView) findViewById(R.id.calendar_view);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view_event_tools_container);
        mNewestDateString = getString(R.string.no_date_selected);

        final Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        mCalendarView.refreshCalendar(currentCalendar);

        //Handling custom calendar events
        mCalendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                mNewestDateDate = date;
                Toast.makeText(getApplicationContext(), df.format(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
                Toast.makeText(getApplicationContext(), df.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        // Create Event onClickListener
        mButtonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScrollView.getVisibility() == View.VISIBLE) {
                    createEvent();
                    return;
                }

                mScrollView.setVisibility(View.VISIBLE);
                mTextViewCreateEvent.setVisibility(View.VISIBLE);
                mTextViewEditEvent.setVisibility(View.GONE);

                runFadeInAnimationOn(ScheduleActivity.this, mScrollView);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

                mNewestDateString = simpleDateFormat.format(mNewestDateDate);

                Log.i(TAG, "[CreateEvent] Newest Date: " + mNewestDateString);

                mTextViewCurrentDate.setText("Current Date: " + mNewestDateString);

                mButtonEditEvent.setVisibility(View.GONE);
                mButtonDeleteEvent.setVisibility(View.GONE);
            }
        });

        // Edit Event onClickListener
        mButtonEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.setVisibility(View.VISIBLE);
                mTextViewCreateEvent.setVisibility(View.GONE);
                mTextViewEditEvent.setVisibility(View.VISIBLE);

                runFadeInAnimationOn(ScheduleActivity.this, mScrollView);

                mButtonEditEvent.setVisibility(View.GONE);
                mButtonDeleteEvent.setVisibility(View.GONE);

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
                createEvent();
                List<DayDecorator> decorators = new ArrayList<>();
                decorators.add(new ColorDecorator());
                mCalendarView.setDecorators(decorators);
                mCalendarView.refreshCalendar(currentCalendar);
            }
        });

        // Cancel Edits/Creation of Event onClickListener
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.setVisibility(View.GONE);
                mTextViewCreateEvent.setVisibility(View.GONE);
                mTextViewEditEvent.setVisibility(View.GONE);

                mButtonEditEvent.setVisibility(View.VISIBLE);
                mButtonDeleteEvent.setVisibility(View.VISIBLE);

                runFadeOutAnimationOn(ScheduleActivity.this, mScrollView);

                Toast.makeText(getApplicationContext(), "Event edit/creation canceled.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Homework Checkbox
        mCheckBoxHomework.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBoxTest.setChecked(false);
                    mCheckBoxQuiz.setChecked(false);
                    mCheckBoxProject.setChecked(false);
                    mCheckBoxBirthday.setChecked(false);
                    mCheckBoxOther.setChecked(false);
                } else {

                }
            }
        });

        // Test Checkbox
        mCheckBoxTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBoxHomework.setChecked(false);
                    mCheckBoxQuiz.setChecked(false);
                    mCheckBoxProject.setChecked(false);
                    mCheckBoxBirthday.setChecked(false);
                    mCheckBoxOther.setChecked(false);
                } else {

                }
            }
        });

        // Quiz Checkbox
        mCheckBoxQuiz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBoxTest.setChecked(false);
                    mCheckBoxHomework.setChecked(false);
                    mCheckBoxProject.setChecked(false);
                    mCheckBoxBirthday.setChecked(false);
                    mCheckBoxOther.setChecked(false);
                } else {

                }
            }
        });

        // Project Checkbox
        mCheckBoxProject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBoxTest.setChecked(false);
                    mCheckBoxQuiz.setChecked(false);
                    mCheckBoxHomework.setChecked(false);
                    mCheckBoxBirthday.setChecked(false);
                    mCheckBoxOther.setChecked(false);
                } else {

                }
            }
        });

        // Birthday Checkbox
        mCheckBoxBirthday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBoxTest.setChecked(false);
                    mCheckBoxQuiz.setChecked(false);
                    mCheckBoxProject.setChecked(false);
                    mCheckBoxHomework.setChecked(false);
                    mCheckBoxOther.setChecked(false);
                } else {

                }
            }
        });

        // Other Checkbox
        mCheckBoxOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBoxTest.setChecked(false);
                    mCheckBoxQuiz.setChecked(false);
                    mCheckBoxProject.setChecked(false);
                    mCheckBoxBirthday.setChecked(false);
                    mCheckBoxHomework.setChecked(false);
                } else {

                }
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

        Log.i(TAG, "onDateSet() called!");

        // Create a new date String formatted using that template
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        GregorianCalendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        mNewestDateString = format.format(calendar.getTime());

        mTextViewCurrentDate.setText("Current Date: " + mNewestDateString);

        Log.i(TAG, "Date Created: " + mNewestDateString);
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

    private static Animation runFadeOutAnimationOn(Activity ctx, View target) {
        Animation animation = AnimationUtils.loadAnimation(ctx,
                android.R.anim.fade_out);
        target.startAnimation(animation);
        return animation;
    }

    private static Animation runFadeInAnimationOn(Activity ctx, View target) {
        Animation animation = AnimationUtils.loadAnimation(ctx,
                android.R.anim.fade_in);
        target.startAnimation(animation);
        return animation;
    }

    private ScheduledEvent createEvent() {
        mScrollView.setVisibility(View.GONE);
        mTextViewCreateEvent.setVisibility(View.GONE);
        mTextViewEditEvent.setVisibility(View.GONE);

        mButtonEditEvent.setVisibility(View.VISIBLE);
        mButtonDeleteEvent.setVisibility(View.VISIBLE);

        runFadeOutAnimationOn(ScheduleActivity.this, mScrollView);

        String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));

        // String title, String desc, SimpleDateFormat date, SimpleDateFormat dateCreated,
        // boolean isHomework, boolean isTest, boolean isProj, boolean isQuiz,
        // boolean isBirthday, boolean isOther
        ScheduledEvent newEvent = new ScheduledEvent(mEditTextTitle.getText().toString(),
                mEditTextDesc.getText().toString(),
                mNewestDateString, currentDate,
                mCheckBoxHomework.isSelected(),
                mCheckBoxTest.isSelected(),
                mCheckBoxProject.isSelected(),
                mCheckBoxQuiz.isSelected(),
                mCheckBoxBirthday.isSelected(),
                mCheckBoxOther.isSelected());



        Toast.makeText(getApplicationContext(), "Event created.",
                Toast.LENGTH_SHORT).show();

        Log.i(TAG, "New Event Created: " + newEvent.toString());

        return newEvent;
    }

    /**
     * This is an override of the onKeyDown method of the Activity class.
     * I am overriding this to provide back button functionality such that the
     * user may use the back button to exit the event creation/editing dialog.
     * @param keyCode the button's key code
     * @param event the specific event the button triggered
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (android.os.Build.VERSION.SDK_INT > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * Here I am overriding the onBackPressed() method to allow the user
     * to use the back button to close the event creation/editing dialog.
     */
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        if (mScrollView.getVisibility() == View.VISIBLE) {
            runFadeOutAnimationOn(this, mScrollView);
            mScrollView.setVisibility(View.GONE);
            mButtonDeleteEvent.setVisibility(View.VISIBLE);
            mButtonEditEvent.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    private class ColorDecorator implements DayDecorator {

        @Override
        public void decorate(DayView cell) {
            Random rnd = new Random();
            int color = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                color = getResources().getColor(R.color.colorPrimary, null);
            } else {
                color = getResources().getColor(R.color.colorPrimary);
            }
            cell.setBackgroundColor(color);
        }
    }
}
