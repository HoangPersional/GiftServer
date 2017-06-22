package com.example.administrator.surprisegiftserver.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.model.Event;
import com.example.administrator.surprisegiftserver.model.EventDbManager;
import com.example.administrator.surprisegiftserver.support.ConnectServer;
import com.example.administrator.surprisegiftserver.support.NotificationEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by Administrator on 12/6/2017.
 */

public class NotificationService extends Service implements ConnectServer.ConnectComplete {
    public String TAG = NotificationService.class.getSimpleName();
    private ArrayList<Event> events;
    private static int TIME = 2000;
    private EventDbManager eventDbManager;
    private Handler handler;
    private Calendar calendar;
    private int date, month, year, hour, minute;
    private HashMap<Integer, Boolean> booleanHashMap;
    private ConnectServer connectServer;
    static Ringtone ringtone;
    int i = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        registerReceiver(updateDb, new IntentFilter("UPDATE_DB"));
        registerReceiver(stopNotification, new IntentFilter("STOP_NOTIFICATION"));
        Log.v("NotificationService", "onCreate");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(updateDb);
        unregisterReceiver(stopNotification);
        super.onDestroy();

    }

    public void init() {
        eventDbManager = EventDbManager.getInstance(getApplicationContext());
        events = eventDbManager.getEvents();
        if (booleanHashMap == null)
            booleanHashMap = new HashMap<>();
        handler = new Handler();
        for (int i = 0; i < events.size(); ++i) {
            if (booleanHashMap.get(events.get(i).getId()) == null)
                booleanHashMap.put(events.get(i).getId(), false);
        }
        if (connectServer == null) {
            connectServer = new ConnectServer(this, this);
            connectServer.setUrl(Config.PUSH_EVENT_CLIENT);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("NotificationService", "onStartCommand");
        handler.removeCallbacks(runnable);
        handler.post(runnable);
        return START_STICKY;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            kt();
            handler.postDelayed(runnable, TIME);
        }
    };

    private void kt() {
        Event e;
        Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        for (int i = 0; i < events.size(); ++i) {
            e = events.get(i);
            if (date == e.getDate() && month == e.getMonth() && hour == e.getHour() && minute == e.getMinute()) {
                if (e.isRepeat() || (year == e.getYear())) {
                    notification(e);
                }
            }
        }
    }

    private void notification(Event e) {
        if (booleanHashMap.get(e.getId()) == false) {
            booleanHashMap.put(e.getId(), true);
            NotificationEvent notificationEvent = new NotificationEvent(getApplicationContext(), e);
            notificationEvent.show();
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
            if (notification != null) {
                if (ringtone == null)
                    ringtone = RingtoneManager.getRingtone(this, notification);
                else if (ringtone.isPlaying())
                    ringtone.stop();
                ringtone.play();
            } else {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ding);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.start();
            }
            notificationClient(e);
        }
    }

    private void notificationClient(Event e) {
        if (e.isClient()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("idClient", String.valueOf(e.getIdClient()));
            map.put("from", getToken(this));
            map.put("message", e.getMessage());
            map.put("image", e.getImage());
            map.put("music", e.getMp3());
            map.put("video", e.getVideo());
            connectServer.setPara(map);
            connectServer.connect();
            Log.v("HH", e.getName() +" "+e.getId()+ " " + i);
        }
    }

    BroadcastReceiver updateDb = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handler.removeCallbacks(runnable);
            init();
            handler.post(runnable);
        }
    };
    BroadcastReceiver stopNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(NotificationEvent.id);
            if (ringtone != null && ringtone.isPlaying())
                ringtone.stop();
        }
    };

    @Override
    public void response(String response) {
        Log.v(TAG, response);
    }

    public String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.PREF, 0);
        return sharedPreferences.getString("token", "");
    }
}
