package com.example.administrator.surprisegiftserver.model;

import android.content.Context;

import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.support.ConnectServer;

import java.util.HashMap;

/**
 * Created by Administrator on 31/5/2017.
 */

public class LoadClients implements ConnectServer.ConnectComplete {
    private User user;
    ConnectServer.ConnectComplete connectComplete;
    ConnectServer connectServer;

    public LoadClients(Context context, User user) {
        this.user = user;
        connectServer = new ConnectServer(context, this);
        connectServer.setUrl(Config.GET_CLIENTS);

    }

    public void connect() {
        HashMap<String, String> map = new HashMap<>();
        map.put("iIdUserName", String.valueOf(user.getId()));
        connectServer.setPara(map);
        connectServer.connect();
    }

    @Override
    public void response(String response) {
        connectComplete.response(response);
    }

    public void setConnectComplete(ConnectServer.ConnectComplete connectComplete) {
        this.connectComplete = connectComplete;
    }

}
