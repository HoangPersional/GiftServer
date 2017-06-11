package com.example.administrator.surprisegiftserver.model;

import android.content.Context;

import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.support.ConnectServer;

import java.util.HashMap;

/**
 * Created by Administrator on 3/6/2017.
 */

public class PushEventToClient implements ConnectServer.ConnectComplete {
    private Context context;
    private Event event;
    private ConnectServer connectServer;
    public PushEventToClient(Context context,Event event)
    {
        this.context=context;
        this.event=event;
        init();
    }
    public PushEventToClient(Context  context)
    {
        this.context=context;
        init();
    }
    private void init()
    {
        connectServer=new ConnectServer(context,this);
        connectServer.setUrl(Config.PUSH_EVENT_CLIENT);
        setPara();
    }
    public void connect()
    {
        connectServer.connect();
    }
    @Override
    public void response(String response) {

    }
    private void setPara()
    {
        HashMap<String,String> map=new HashMap<>();
//        map.put()
    }
}
