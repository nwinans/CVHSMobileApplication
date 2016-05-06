package com.benrcarvergmail.cvhsmobileapplication;

import android.widget.CheckBox;

import java.text.SimpleDateFormat;

/**
 * Created by Benjamin on 5/4/2016.
 */
public class ScheduledEvent {

    private String mTitle;                      // The title of the event
    private String mDesc;                       // The description of the event

    private SimpleDateFormat mDate;             // The date of the event
    private SimpleDateFormat mDateCreated;      // The date the event was created

    private boolean mIsHomework;         // Indicates whether the event is a homework assignment
    private boolean mIsTest;             // Indicates whether the event is a test
    private boolean mIsProject;          // Indicates whether the event is a project
    private boolean mIsQuiz;             // Indicates whether the event is a quiz
    private boolean mIsBirthday;         // Indicates whether the event is a birthday
    private boolean mIsOther;            // Indicates whether the event is something else entirely.

    public ScheduledEvent(String title, String desc, SimpleDateFormat date1,
                          SimpleDateFormat date2, boolean bool1, boolean bool2,
                          boolean bool3, boolean bool4, boolean bool5, boolean bool6) {
        mTitle = title;
        mDesc = desc;
        mDate = date1;
        mDateCreated = date2;
        mIsHomework = bool1;
        mIsTest = bool2;
        mIsProject = bool3;
        mIsQuiz = bool4;
        mIsBirthday = bool5;
        mIsOther = bool6;
    }

    /**
     * Changes the event's title
     * @param title the new title for the event
     * @return the old title for the event
     */
    public String editTitle(String title) {
        String oldTitle = mTitle;
        mTitle = title;
        return oldTitle;
    }

    /**
     * Changes the event's description
     * @param desc the new description for the event
     * @return the old description for the event
     */
    public String editDescription(String desc) {
        String oldDesc = mDesc;
        mDesc = desc;
        return oldDesc;
    }

    /**
     * Changes the event's date
     * @param date the new date for the event
     * @return the old date for the date
     */
    public SimpleDateFormat editDate(SimpleDateFormat date) {
        SimpleDateFormat oldDate = mDate;
        mDate = date;
        return oldDate;
    }

    // Getter methods for all the variables...

    public SimpleDateFormat getDate() {
        return mDate;
    }

    public SimpleDateFormat getDateCreated() {
        return mDateCreated;
    }

    public String getDesc() {
        return mDesc;
    }

    public boolean getIsBirthday() {
        return mIsBirthday;
    }

    public boolean getIsHomework() {
        return mIsHomework;
    }

    public boolean getIsProject() {
        return mIsProject;
    }

    public boolean getIsQuiz() {
        return mIsQuiz;
    }

    public boolean getIsTest() {
        return mIsTest;
    }

    public boolean getIsOther() { return mIsOther; }

    public String getTitle() {
        return mTitle;
    }
}
