package com.example.administrator.surprisegiftserver.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.support.ConnectServer;

import java.util.HashMap;

/**
 * Created by Administrator on 31/5/2017.
 */

public class TestConnectClient implements ConnectServer.ConnectComplete {
    ConnectServer connectServer;
    ConnectServer.ConnectComplete connectComplete;

    public TestConnectClient(Context context, int idClient) {
        connectServer = new ConnectServer(context, this);
        connectServer.setUrl(Config.TEST_CLIENT);
        HashMap<String, String> map = new HashMap<>();
        map.put("idClient", String.valueOf(idClient));
        map.put("from", getToken(context));
        connectServer.setPara(map);
    }

    public void connect() {
        connectServer.connect();
    }

    @Override
    public void response(String response) {
        connectComplete.response(response);
    }

    public void setConnectComplete(ConnectServer.ConnectComplete s) {
        this.connectComplete = s;
    }

    public String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.PREF, 0);
        return sharedPreferences.getString("token", "");
    }
}
