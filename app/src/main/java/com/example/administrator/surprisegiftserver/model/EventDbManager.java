package com.example.administrator.surprisegiftserver.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.administrator.surprisegiftserver.config.Config;

import java.util.ArrayList;

/**
 * Created by Administrator on 3/6/2017.
 */

public class EventDbManager {
    public static String INTEGER_PRIMARY_KEY_TYPE = " integer primary key autoincrement";
    public static String TEXT_TYPE = " text";
    public static String INTEGER_TYPE = " integer";
    private static EventDbManager eventDbManager;
    private ManagerEventHelper managerEventHelper;
    private static SQLiteDatabase mSqLiteDatabase;
    private static int VERSION = 1;
    private static Context mContext;
    private static String DATA_BASE_NAME = Config.DATA_BASE_NAME;
    private static String EVENT_TABLE_NAME = "event_table";
    private static String DROP_TABLE = "drop table if exists" + EVENT_TABLE_NAME;
    private static String CREATE_TABLE = "create table "
            + EVENT_TABLE_NAME + " ("
            + Column.ID_DB + INTEGER_PRIMARY_KEY_TYPE + ","
            + Column.ID + INTEGER_TYPE + ","
            + Column.NAME + TEXT_TYPE + ","
            + Column.ID_USER + INTEGER_TYPE + ","
            + Column.ID_CLIENT + INTEGER_TYPE + ","
            + Column.DATE + INTEGER_TYPE + ","
            + Column.MONTH + INTEGER_TYPE + ","
            + Column.YEAR + INTEGER_TYPE + ","
            + Column.HOUR + INTEGER_TYPE + ","
            + Column.MINUTE + INTEGER_TYPE + ","
            + Column.REPEAT + INTEGER_TYPE + ","
            + Column.TYPE + INTEGER_TYPE + ","
            + Column.STATUS + INTEGER_TYPE + ","
            + Column.D_CREATE + TEXT_TYPE + ","
            + Column.DESCRIPTION + TEXT_TYPE + ","
            + Column.IS_CLIENT + INTEGER_TYPE + ","
            + Column.IS_NOTIFICATION + INTEGER_TYPE + ","
            + Column.NOTIFICATION_BEFORE + INTEGER_TYPE + ","
            + Column.MESSAGE + TEXT_TYPE + ","
            + Column.IMAGE + TEXT_TYPE + ","
            + Column.MUSIC + TEXT_TYPE + ","
            + Column.VIDEO + TEXT_TYPE
            + ")";
    private static String[] columns = {
            Column.ID_DB,
            Column.ID,
            Column.NAME,
            Column.ID_USER,
            Column.ID_CLIENT,
            Column.DATE,
            Column.MONTH,
            Column.YEAR,
            Column.HOUR,
            Column.MINUTE,
            Column.REPEAT,
            Column.TYPE,
            Column.STATUS,
            Column.D_CREATE,
            Column.DESCRIPTION,
            Column.IS_CLIENT,
            Column.IS_NOTIFICATION,
            Column.NOTIFICATION_BEFORE,
            Column.MESSAGE,
            Column.IMAGE,
            Column.MUSIC,
            Column.VIDEO
    };

    private EventDbManager() {
        managerEventHelper = new ManagerEventHelper(mContext, DATA_BASE_NAME, null, VERSION);
        mSqLiteDatabase = managerEventHelper.getWritableDatabase();
    }

    public static synchronized EventDbManager getInstance(Context context) {
        mContext = context;
        if (eventDbManager == null)
            eventDbManager = new EventDbManager();
        return eventDbManager;
    }
    public void clear()
    {
        mSqLiteDatabase.delete(EVENT_TABLE_NAME,null,null);
    }
    public void insertEvent(Event e) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column.ID, e.getId());
        contentValues.put(Column.NAME, e.getName());
        contentValues.put(Column.ID_USER, e.getIdUser());
        contentValues.put(Column.ID_CLIENT, e.getIdClient());
        contentValues.put(Column.DATE, e.getDate());
        contentValues.put(Column.MONTH, e.getMonth());
        contentValues.put(Column.YEAR, e.getYear());
        contentValues.put(Column.HOUR, e.getHour());
        contentValues.put(Column.MINUTE, e.getMinute());
        contentValues.put(Column.REPEAT, e.isRepeat());
        contentValues.put(Column.TYPE, e.getiType());
        contentValues.put(Column.STATUS, e.getiStatus());
        contentValues.put(Column.D_CREATE, e.getdCreate());
        contentValues.put(Column.DESCRIPTION, e.getDescription());
        contentValues.put(Column.IS_CLIENT, e.isClient());
        contentValues.put(Column.IS_NOTIFICATION, e.isNotification());
        contentValues.put(Column.NOTIFICATION_BEFORE, e.getiNotificationBeforeMinute());
        contentValues.put(Column.MESSAGE, e.getMessage());
        contentValues.put(Column.IMAGE, e.getImage());
        contentValues.put(Column.MUSIC, e.getMp3());
        contentValues.put(Column.VIDEO, e.getVideo());
        if(mSqLiteDatabase.insert(EVENT_TABLE_NAME, null, contentValues)!=-1)
        {
            Log.v("HH_INSERT",e.toString());
        }
    }

    public void insertEvents(ArrayList<Event> list) {
        for (Event e : list
                ) {
            insertEvent(e);
        }
    }

    protected Event getEvent(int id_event) {
        Event event = new Event();
        Cursor cursor = mSqLiteDatabase.query(EVENT_TABLE_NAME, columns, Column.ID_CLIENT + "=?", new String[]{String.valueOf(id_event)}, null, null, null);
        event = CursorToEvent(cursor);
        return event;
    }

    public ArrayList<Event> getEvents() {
        ArrayList<Event> events = new ArrayList<>();
        Cursor cursor = mSqLiteDatabase.query(EVENT_TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Event e = CursorToEvent(cursor);
            events.add(e);
            cursor.moveToNext();
        }
        return events;
    }

    private static Event CursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.ID))));
        event.setName(cursor.getString(cursor.getColumnIndex(Column.NAME)));
        event.setIdUser(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.ID_USER))));
        event.setIdClient(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.ID_CLIENT))));
        event.setDate(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.DATE))));
        event.setMonth(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.MONTH))));
        event.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.YEAR))));
        event.setHour(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.HOUR))));
        event.setMinute(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.MINUTE))));
        event.setRepeat(cursor.getString(cursor.getColumnIndex(Column.REPEAT)).equals("1") ? true : false);

        event.setiType(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.TYPE))));
        event.setiStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.STATUS))));
        event.setdCreate(cursor.getString(cursor.getColumnIndex(Column.D_CREATE)));
        event.setDescription(cursor.getString(cursor.getColumnIndex(Column.DESCRIPTION)));
        event.setClient(cursor.getString(cursor.getColumnIndex(Column.IS_CLIENT)).equals("1") ? true : false);
        event.setNotification(cursor.getString(cursor.getColumnIndex(Column.IS_NOTIFICATION)).equals("1") ? true : false);
        event.setiNotificationBeforeMinute(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Column.NOTIFICATION_BEFORE))));
        event.setMessage(cursor.getString(cursor.getColumnIndex(Column.MESSAGE)));
        event.setImage(cursor.getString(cursor.getColumnIndex(Column.IMAGE)));
        event.setMp3(cursor.getString(cursor.getColumnIndex(Column.MUSIC)));

        event.setVideo(cursor.getString(cursor.getColumnIndex(Column.VIDEO)));
        return event;
    }

    public class ManagerEventHelper extends SQLiteOpenHelper {

        public ManagerEventHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public ManagerEventHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }
    }
}
