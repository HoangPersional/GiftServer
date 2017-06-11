package com.example.administrator.surprisegiftserver.model;

import android.content.Context;
import android.util.Log;

import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.support.ConnectServer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 27/5/2017.
 */

public class LoadEvents implements ConnectServer.ConnectComplete {
    private ArrayList<Event> events;
    private ConnectServer.ConnectComplete connectComplete;
    private Context context;
    private int id;
    private ConnectServer connectServer;

    public LoadEvents(Context context, int id) {
        this.context = context;
        this.id = id;
        connectServer = new ConnectServer(context, this);
        connectServer.setUrl(Config.GET_ALL_EVENT);
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        connectServer.setPara(map);
    }

    @Override
    public void response(String response) {
        connectComplete.response(response);
    }
    public void connect()
    {
        connectServer.connect();
    }
    public void setConnectComplete(ConnectServer.ConnectComplete connectComplete) {
        this.connectComplete = connectComplete;
    }
    public Context getContext()
    {
        return  context;
    }
}
