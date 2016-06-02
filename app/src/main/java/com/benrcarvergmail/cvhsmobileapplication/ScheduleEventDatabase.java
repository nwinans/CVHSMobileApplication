package com.benrcarvergmail.cvhsmobileapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Austin on 5/26/16.
 */
public class ScheduleEventDatabase extends SQLiteOpenHelper{
    public ScheduleEventDatabase(Context context){
        super(context, EventEntry.DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase d){
        String CREATE_EVENTS_TABLE = "CREATE TABLE "  + EventEntry.Table_NAME + "("
                + EventEntry.COLUMN_NAME_ID + " INT,"
                + EventEntry.COlUMN_NAME_TITLE + " TEXT,"
                + EventEntry.COLUMN_NAME_DESCRIPTION + " TEXT,"
               //+ EventEntry._ID + " INTEGER PRIMARY KEY, "
                + EventEntry.COLUMN_NAME_Date + " TEXT,"
                + EventEntry.COLUMN_NAME_HOMEWORK + " INT,"
                + EventEntry.COLUMN_NAME_TEST + " INT,"
                + EventEntry.COLUMN_NAME_PROJECT + " INT,"
                + EventEntry.COLUMN_NAME_QUIZ + " INT,"
                + EventEntry.COLUMN_NAME_BIRTHDAY + " INT,"
                + EventEntry.COLUMN_NAME_OTHER + " INT" + ")";
        d.execSQL(CREATE_EVENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase d, int oldVersion, int newVersion){
        d.execSQL("DROP TABLE IF EXISTS " + EventEntry.Table_NAME);
        onCreate(d);
    }
    public long insertEvent(String date,String title, String desc, boolean homework, boolean test, boolean project, boolean quiz, boolean birthday, boolean other,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EventEntry.COLUMN_NAME_ID,id);
        values.put(EventEntry.COLUMN_NAME_Date, date);
        values.put(EventEntry.COlUMN_NAME_TITLE,title);
        values.put(EventEntry.COLUMN_NAME_DESCRIPTION,desc);
        values.put(EventEntry.COLUMN_NAME_HOMEWORK,homework);
        values.put(EventEntry.COLUMN_NAME_TEST, test);
        values.put(EventEntry.COLUMN_NAME_PROJECT,project);
        values.put(EventEntry.COLUMN_NAME_QUIZ,quiz);
        values.put(EventEntry.COLUMN_NAME_BIRTHDAY,birthday);
        values.put(EventEntry.COLUMN_NAME_OTHER,other);
        Log.i("DatabaseInsert",date);
        long d =db.insert(EventEntry.Table_NAME,null,values);
        db.close();
        return d;
    }
    public long insertEvent(ScheduledEvent s){
        return insertEvent(s.getDate(),s.getTitle(),s.getDesc(),s.getIsHomework(),s.getIsTest(),s.getIsProject(),s.getIsQuiz(),s.getIsBirthday(),s.getIsOther(),(int)s.getId());
    }
    public ScheduledEvent getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Log.i("ScheduleEventDatabase",Integer.toString(numberOfRows()));
        Cursor res = db.rawQuery("select * from "+EventEntry.Table_NAME +" where "+EventEntry.COLUMN_NAME_ID+"=" + id, null);
        for(String i: res.getColumnNames())
        Log.i("ScheduleEventDatabase", i);

        ScheduledEvent d;
        if(res != null){
            res.moveToFirst();
            d = new ScheduledEvent(res.getString(1),res.getString(2),res.getString(3),toBoolean(res.getInt(4)),toBoolean(res.getInt(5)),
                    toBoolean(res.getInt(6)),toBoolean(res.getInt(7)),toBoolean(res.getInt(8)),toBoolean(res.getInt(9)));
        }else{
            return null;
        }
        return d;
    }
    public ArrayList<ScheduledEvent> getEventsOnDate(String date){
        Log.i("Database", date);
        ArrayList<ScheduledEvent> eventList = new ArrayList<ScheduledEvent>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from "+EventEntry.Table_NAME+" where "+EventEntry.COLUMN_NAME_Date+"=" + "\'"+date+"\'";
        Cursor res = db.rawQuery(query,null);
        Log.i("Query", query);
        Log.i("ScheduleEventDatabase",Integer.toString(numberOfRows()));

        if(res.moveToFirst()){
            do{
                ScheduledEvent event = new ScheduledEvent(res.getString(1),res.getString(2),res.getString(3),toBoolean(res.getInt(4)),toBoolean(res.getInt(5)),
                        toBoolean(res.getInt(6)),toBoolean(res.getInt(7)),toBoolean(res.getInt(8)),toBoolean(res.getInt(9)));
                eventList.add(event);
                Log.i("Database list", "Added to list"+event.toString());

            }while(res.moveToNext());
        }
        return eventList;

    }
    public int updateEvent(String date,String title, String desc, boolean homework, boolean test, boolean project, boolean quiz, boolean birthday, boolean other, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(EventEntry.COLUMN_NAME_Date, date);
        values.put(EventEntry.COlUMN_NAME_TITLE,title);
        values.put(EventEntry.COLUMN_NAME_DESCRIPTION,desc);
        values.put(EventEntry.COLUMN_NAME_HOMEWORK,homework);
        values.put(EventEntry.COLUMN_NAME_TEST, test);
        values.put(EventEntry.COLUMN_NAME_PROJECT,project);
        values.put(EventEntry.COLUMN_NAME_QUIZ,quiz);
        values.put(EventEntry.COLUMN_NAME_BIRTHDAY,birthday);
        values.put(EventEntry.COLUMN_NAME_OTHER,other);

        return db.update(EventEntry.Table_NAME,values,EventEntry.COLUMN_NAME_ID + " = ?",new String[]{String.valueOf(id)});
    }
    public int updateEvent(ScheduledEvent s){
        return updateEvent(s.getDate(),s.getTitle(),s.getDesc(),s.getIsHomework(),s.getIsTest(),s.getIsProject(),s.getIsQuiz(),s.getIsBirthday(),s.getIsOther(),(int)s.getId());
    }
    public void deleteEvent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EventEntry.Table_NAME,EventEntry.COLUMN_NAME_ID + " = ?", new String[] {
                String.valueOf(id)
        });
        db.close();
    }
    public void deleteEvent(ScheduledEvent s){
        deleteEvent((int) s.getId());
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows =(int) DatabaseUtils.queryNumEntries(db, EventEntry.Table_NAME);
        return numRows;
    }
    private boolean toBoolean(int d){
        return d == 1;
    }




    public static abstract class EventEntry implements BaseColumns {
        public static final String DATABASE_NAME = "CalanderEvents.db";
        public static final String Table_NAME = "events";
        public static final String COlUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_Date = "date";
        public static final String COLUMN_NAME_HOMEWORK = "homework";
        public static final String COLUMN_NAME_TEST = "test";
        public static final String COLUMN_NAME_PROJECT = "project";
        public static final String COLUMN_NAME_QUIZ = "quiz";
        public static final String COLUMN_NAME_BIRTHDAY = "birthday";
        public static final String COLUMN_NAME_OTHER = "other";
        public static final String COLUMN_NAME_ID = "id";

    }


}
