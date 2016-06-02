package com.benrcarvergmail.cvhsmobileapplication;

import android.widget.CheckBox;

import java.util.Date;

/**
 * Created by Benjamin on 5/4/2016.
 */
public class ScheduledEvent {
    private long id;
    private static long num=0;

    private String mTitle;                      // The title of the event
    private String mDesc;                       // The description of the event

    private String mDate;             // The date of the event
    private String mDateCreated;      // The date the event was created

    private boolean mIsHomework;         // Indicates whether the event is a homework assignment
    private boolean mIsTest;             // Indicates whether the event is a test
    private boolean mIsProject;          // Indicates whether the event is a project
    private boolean mIsQuiz;             // Indicates whether the event is a quiz
    private boolean mIsBirthday;         // Indicates whether the event is a birthday
    private boolean mIsOther;            // Indicates whether the event is something else entirely.

    private int mPeriod; //The period the event was specified for, 0 by default

    public ScheduledEvent(String title, String desc, String date1,
                          String date2, boolean bool1, boolean bool2,
                          boolean bool3, boolean bool4, boolean bool5, boolean bool6,int p) {
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
        mPeriod = p;
        id = num;
        num++;
    }
    public ScheduledEvent() {
        mTitle = null;
        mDesc = null;
        mDate = null;
        mDateCreated = null;
        mIsHomework = false;
        mIsTest = false;
        mIsProject = false;
        mIsQuiz = false;
        mIsBirthday = false;
        mIsOther = false;
        mPeriod = 0;
        id = num;
        num++;
    }
    public ScheduledEvent(String title, String desc, String date1,
                          boolean bool1, boolean bool2,
                          boolean bool3, boolean bool4, boolean bool5, boolean bool6,int period) {
        mTitle = title;
        mDesc = desc;
        mDate = date1;
        mDateCreated = date1;
        mIsHomework = bool1;
        mIsTest = bool2;
        mIsProject = bool3;
        mIsQuiz = bool4;
        mIsBirthday = bool5;
        mIsOther = bool6;
        mPeriod = period;
        id = num;
        num++;
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

    public long getId(){
        return id;
    }
    public void setId(long d){ id = d;}
    public int getPeriod(){return mPeriod;};
    @Override
    public String toString() {
        String type = "";
        if (mIsBirthday) {
            type = "Birthday";
        } else if (mIsHomework) {
            type = "Homework";
        } else if (mIsProject) {
            type = "Project";
        } else if (mIsQuiz) {
            type = "Quiz";
        } else if (mIsTest) {
            type = "Test";
        } else if (mIsOther) {
            type = "Other (misc.)";
        }

        return "Event[Title: " + mTitle + "], [Description: " + mDesc + "], [Date Created: " + mDateCreated
                + "], [Date of Event: " + mDate + "], [Type: " + type + "]";
    }

}
