package com.benrcarvergmail.cvhsmobileapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.AlertDialog;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;
import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Benjamin Carver on 5/3/2016.
 */
public class ScheduleActivity extends FragmentActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener {
    private ScheduleEventDatabase EventDB = new ScheduleEventDatabase(this);

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

    private ListView mClassListView;
    private ListView mEventListView;
    private int mPeriod;
    // Used for the creation of an event. Updated each time an event is created.
    private String mNewestDateString = "";//AUSTIN: MEMBER VARIABLES THAT ARE BASED ON OTHER VARIBALES SHOULD BE METHODS NOT VARIABLES
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
        mNewestDateString = getString(R.string.no_date_selected);

        final Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        mCalendarView.refreshCalendar(currentCalendar);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view_event_tools_container);

        mClassListView = (ListView) findViewById(R.id.class_list_view);
        mPeriod = 0;
        initClassesListView();
        mEventListView = (ListView) findViewById(R.id.ExpandableList_CalendarList);
        initScheduleListView();

        //Handling custom calendar events
        mCalendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                mNewestDateDate = date;
                initScheduleListView();
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

                String newestDateString = getNewestDateString();

                Log.i(TAG, "[CreateEvent] Newest Date: " + newestDateString);

                mTextViewCurrentDate.setText("Current Date: " + newestDateString);

       //         mEventListView.setVisibility(View.GONE);
            }
        });

/*        // Edit Event onClickListener
        mButtonEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.setVisibility(View.VISIBLE);
                mTextViewCreateEvent.setVisibility(View.GONE);
                mTextViewEditEvent.setVisibility(View.VISIBLE);

                runFadeInAnimationOn(ScheduleActivity.this, mScrollView);
                mTextViewCurrentDate.setText("Current Date: " + getNewestDateString());

                mButtonEditEvent.setVisibility(View.GONE);
                mButtonDeleteEvent.setVisibility(View.GONE);

                // Toast.makeText(getApplicationContext(), "Press 'confirm' to confirm your changes. Press 'cancel' to discard them.",
                        // Toast.LENGTH_SHORT).show();
            }
        });*/

/*        // Delete Event onClickListener
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

                                //removeEvent(getNewestDateString());
                            }

                        })
                        .setNegativeButton("No, nevermind", null)
                        .show();
            }
        });*/

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
                mCalendarView.refreshCalendar(currentCalendar);//PROBLEM HERE: ALL GET COLORED
            }
        });

        // Cancel Edits/Creation of Event onClickListener
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.setVisibility(View.GONE);
                mTextViewCreateEvent.setVisibility(View.GONE);
                mTextViewEditEvent.setVisibility(View.GONE);


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
                    setListViewVisible();

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
                    setListViewVisible();
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
                    setListViewVisible();
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
                    setListViewVisible();
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
                    setListViewVisible();
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
                    setListViewVisible();
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
        mEventListView.setVisibility(View.VISIBLE);

        runFadeOutAnimationOn(ScheduleActivity.this, mScrollView);

        String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));

        // String title, String desc, SimpleDateFormat date, SimpleDateFormat dateCreated,
        // boolean isHomework, boolean isTest, boolean isProj, boolean isQuiz,
        // boolean isBirthday, boolean isOther
        ScheduledEvent newEvent = new ScheduledEvent(mEditTextTitle.getText().toString(),
                mEditTextDesc.getText().toString(),
                getNewestDateString(), currentDate,
                mCheckBoxHomework.isSelected(),
                mCheckBoxTest.isSelected(),
                mCheckBoxProject.isSelected(),
                mCheckBoxQuiz.isSelected(),
                mCheckBoxBirthday.isSelected(),
                mCheckBoxOther.isSelected(),
                mPeriod);

        EventDB.insertEvent(newEvent);

        Toast.makeText(getApplicationContext(), "Event created.",
                Toast.LENGTH_SHORT).show();
        Log.i(TAG, currentDate);
        Log.i(TAG, getNewestDateString());
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
    private void initScheduleListView(){
        ArrayList<ScheduledEvent> events = EventDB.getEventsOnDate(getNewestDateString());
        String[] values = new String[events.size()];
                for(int x = 0; x <values.length;x++){
                    values[x] = events.get(x).getTitle() + "\n" + events.get(x).getDesc();
        }
        Log.i(TAG,events.toString());
        Log.i(TAG,getNewestDateString());
       // ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,values);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.calenderlistlayout,R.id.TextView_CalanderList,values);
        mEventListView.setAdapter(adapter);
    }
    private void initClassesListView(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        final Gson gson = new Gson();

        String[] classes = new String[8];
        classes[0]= "Period 1: "+ sharedPreferences.getString("period_one", "Period 1: ");
        classes[1]= "Period 2: "+sharedPreferences.getString("period_two", "Period 2: ");
        classes[2] = "Period 3: "+sharedPreferences.getString("period_three", "Period 3: ");
        classes[3] = "Period 4: "+sharedPreferences.getString("period_four", "Period 4: ");
        classes[4] = "Period 5: "+sharedPreferences.getString("period_five", "Period 5: ");
        classes[5] = "Period 6: "+sharedPreferences.getString("period_six", "Period 6: ");
        classes[6] = "Period 7: "+sharedPreferences.getString("period_seven", "Period 7: ");
        classes[7] = "cancel";

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,classes);
        mClassListView.setAdapter(adapter);
        mClassListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                switch((int)id){
                    case 0:
                        mPeriod =1;
                        setListViewInvisible();
                        break;
                    case 1:
                        mPeriod =2;
                        setListViewInvisible();
                        break;
                    case 2:
                        mPeriod =3;
                        setListViewInvisible();
                        break;
                    case 3:
                        mPeriod =4;
                        setListViewInvisible();
                        break;
                    case 4:
                        mPeriod = 5;
                        setListViewInvisible();
                        break;
                    case 5:
                        mPeriod = 6;
                        setListViewInvisible();
                        break;
                    case 6:
                        mPeriod = 7;
                        setListViewInvisible();
                        break;
                    case 7: setListViewInvisible();
                        break;
                    default:
                }
            }
        });
        prefsEditor.commit();
    }
    private void setListViewVisible(){
        mScrollView.setVisibility(View.GONE);
        mTextViewCreateEvent.setVisibility(View.GONE);
        mTextViewEditEvent.setVisibility(View.GONE);
        mEventListView.setVisibility(View.GONE);
        mClassListView.setVisibility(View.VISIBLE);
    }
    private void setListViewInvisible(){
        mScrollView.setVisibility(View.VISIBLE);
        mTextViewEditEvent.setVisibility(View.VISIBLE);
        runFadeInAnimationOn(ScheduleActivity.this, mScrollView);
        mClassListView.setVisibility(View.GONE);
        mEventListView.setVisibility(View.VISIBLE);
    }
    private String getNewestDateString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return simpleDateFormat.format(mNewestDateDate);
    }

    public class EventArrayAdapter extends ArrayAdapter<ScheduledEvent>{
        private final List<ScheduledEvent> aEvents;
        private final Activity context;
        private ImageButton aEdit;
        private ImageButton aDelete;


        public EventArrayAdapter(Activity context,List<ScheduledEvent> lis){
            super(context,R.layout.calenderlistlayout,lis);
                aEvents = lis;
            this.context = context;
        }

        public View getView(int position,View convertView,ViewGroup parent){
            View view = null;
            if (convertView == null) {
                LayoutInflater inflator = context.getLayoutInflater();
                view = inflator.inflate(R.layout.calenderlistlayout, null);
                aEdit= (ImageButton) view.findViewById(R.id.EditButton_CalanderList);
                aEdit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        mScrollView.setVisibility(View.VISIBLE);
                        mTextViewCreateEvent.setVisibility(View.GONE);
                        mTextViewEditEvent.setVisibility(View.VISIBLE);

                        runFadeInAnimationOn(ScheduleActivity.this, mScrollView);
                        mTextViewCurrentDate.setText("Current Date: " + getNewestDateString());

                        // Toast.makeText(getApplicationContext(), "Press 'confirm' to confirm your changes. Press 'cancel' to discard them.",
                        // Toast.LENGTH_SHORT).show();
                    }
                });
                aDelete = (ImageButton) view.findViewById(R.id.DeleteButton_CalanderList);
                aDelete.setOnClickListener(new View.OnClickListener(){
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

                                        //removeEvent(getNewestDateString());
                                    }

                                })
                                .setNegativeButton("No, nevermind", null)
                                .show();
                    }
                });
            } else {
                view = convertView;
            }
            return view;
        }
    }
}
