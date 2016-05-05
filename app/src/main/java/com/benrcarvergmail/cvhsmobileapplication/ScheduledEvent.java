package com.benrcarvergmail.cvhsmobileapplication;

import java.util.GregorianCalendar;

/**
 * Created by Benjamin on 5/4/2016.
 */
public class ScheduledEvent {

    private String mTitle;                      // The title of the event
    private String mDesc;                       // The description of the event

    private GregorianCalendar mDate;            // The date of the event
    private GregorianCalendar mDateCreated;     // The date the event was created

    private boolean mIsHomework;         // Indicates whether the event is a homework assignment
    private boolean mIsTest;             // Indicates whether the event is a test
    private boolean mIsProject;          // Indicates whether the event is a project
    private boolean mIsQuiz;             // Indicates whether the event is a quiz
    private boolean mIsBirthday;         // Indicates whether the event is a birthday

    public ScheduledEvent(String title, String desc, GregorianCalendar date1,
                          GregorianCalendar date2, boolean bool1, boolean bool2,
                          boolean bool3, boolean bool4, boolean bool5) {
        mTitle = title;
        mDesc = desc;
        mDate = date1;
        mDateCreated = date2;
        mIsHomework = bool1;
        mIsTest = bool2;
        mIsProject = bool3;
        mIsQuiz = bool4;
        mIsBirthday = bool5;
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
        String oldDesc = desc;
        mDesc = desc;
        return oldDesc;
    }

    /**
     * Changes the event's date
     * @param date the new date for the event
     * @return the old date for the date
     */
    public GregorianCalendar editDate(GregorianCalendar date) {
        GregorianCalendar oldDate = mDate;
        mDate = date;
        return oldDate;
    }

    // Getter methods for all the variables...

    public GregorianCalendar getDate() {
        return mDate;
    }

    public GregorianCalendar getDateCreated() {
        return mDateCreated;
    }

    public String getDesc() {
        return mDesc;
    }

    public boolean isIsBirthday() {
        return mIsBirthday;
    }

    public boolean isIsHomework() {
        return mIsHomework;
    }

    public boolean isIsProject() {
        return mIsProject;
    }

    public boolean isIsQuiz() {
        return mIsQuiz;
    }

    public boolean isIsTest() {
        return mIsTest;
    }

    public String getTitle() {
        return mTitle;
    }
}
