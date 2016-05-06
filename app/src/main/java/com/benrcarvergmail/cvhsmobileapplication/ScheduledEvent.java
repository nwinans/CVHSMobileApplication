package com.benrcarvergmail.cvhsmobileapplication;

import android.widget.CheckBox;

import java.util.Date;

/**
 * Created by Benjamin on 5/4/2016.
 */
public class ScheduledEvent {

    private String mTitle;                      // The title of the event
    private String mDesc;                       // The description of the event

    private String mDate;             // The date of the event
    private String mDateCreated;      // The date the event was created

    private CheckBox mCheckBoxHomework;         // The checkbox for homework
    private CheckBox mCheckBoxProject;          // The checkbox for project
    private CheckBox mCheckBoxBirthday;         // The checkbox for birthday
    private CheckBox mCheckBoxTest;             // The checkbox for test
    private CheckBox mCheckBoxQuiz;             // The checkbox for quiz
    private CheckBox mCheckBoxOther;            // The checkbox for other

    private boolean mIsHomework;         // Indicates whether the event is a homework assignment
    private boolean mIsTest;             // Indicates whether the event is a test
    private boolean mIsProject;          // Indicates whether the event is a project
    private boolean mIsQuiz;             // Indicates whether the event is a quiz
    private boolean mIsBirthday;         // Indicates whether the event is a birthday
    private boolean mIsOther;            // Indicates whether the event is something else entirely.

    public ScheduledEvent(String title, String desc, String date1,
                          String date2, boolean bool1, boolean bool2,
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
    public String editDate(String date) {
        String oldDate = mDate;
        mDate = date;
        return oldDate;
    }

    // Getter methods for all the variables...

    public String getDate() {
        return mDate;
    }

    public String getDateCreated() {
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
