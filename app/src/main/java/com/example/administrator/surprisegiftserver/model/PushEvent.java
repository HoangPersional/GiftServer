package com.example.administrator.surprisegiftserver.model;

import android.content.Context;
import android.util.Log;

import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.support.ConnectServer;

import java.util.HashMap;

/**
 * Created by Administrator on 23/5/2017.
 */

public class PushEvent implements ConnectServer.ConnectComplete {
    private Event mEvent;
    private ConnectServer mConnectServer;
    private Context mContext;
    ConnectServer.ConnectComplete mConnectComplete;

    public PushEvent(Context context, Event event) {
        mContext = context;
        mEvent = event;
        mConnectServer = new ConnectServer(mContext, this);
        mConnectServer.setUrl(Config.PUSH_EVENT);


    }

    private void setPara() {
        HashMap<String, String> map = new HashMap<>();
        map.put("idUser", String.valueOf(mEvent.getId()));
        map.put("eventName", mEvent.getName());
        map.put("date", String.valueOf(mEvent.getDate()));
        map.put("month", String.valueOf(mEvent.getMonth()));
        map.put("year", String.valueOf(mEvent.getYear()));
        map.put("hour", String.valueOf(mEvent.getHour()));
        map.put("minute", String.valueOf(mEvent.getMinute()));
        map.put("repeat", mEvent.isRepeat() ? "1" : "0");
        map.put("description", mEvent.getDescription());
        map.put("notification", mEvent.isNotification() ? "1" : "0");
        map.put("client", mEvent.isClient() ? "1" : "0");
        if (mEvent.isClient()) {
            map.put("message", mEvent.getMessage());
            map.put("image", mEvent.getImage());
            map.put("music", mEvent.getMp3());
            map.put("video", mEvent.getVideo());
            map.put("idClient", String.valueOf(mEvent.getIdClient()));
        }
        mConnectServer.setPara(map);
//        Log.v("TRUOC KHI PUSH",mEvent.isClient()+" "+mEvent.isNotification());
    }

    public void connect() {
        setPara();
        mConnectServer.connect();
    }

    @Override
    public void response(String response) {
        mConnectComplete.response(response);
    }

    public void setOnComplete(ConnectServer.ConnectComplete complete) {
        mConnectComplete = complete;
    }
    public Context getContext()
    {
        return mContext;
    }
}
