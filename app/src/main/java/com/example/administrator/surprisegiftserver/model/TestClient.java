package com.example.administrator.surprisegiftserver.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.support.ConnectServer;

import java.util.HashMap;

/**
 * Created by Administrator on 20/6/2017.
 */

public abstract class TestClient implements ConnectServer.ConnectComplete {
    protected ConnectServer connectServer;
    protected ConnectServer.ConnectComplete connectComplete;
    protected Context mContext;
    protected int id;
    protected HashMap<String, String> map;

    public TestClient(Context context, int idClient) {
        mContext=context;
        connectServer = new ConnectServer(context, this);
        connectServer.setUrl(Config.TEST_CLIENT);
        map = new HashMap<>();
        map.put("idClient", String.valueOf(idClient));
        map.put("from", getToken(context));
    }

    public void connect() {
        connectServer.setPara(map);
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
