package com.example.administrator.surprisegiftserver.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.support.ConnectServer;

import java.util.HashMap;

/**
 * Created by Administrator on 31/5/2017.
 */

public class TestConnectClient extends TestClient {

    public TestConnectClient(Context context, int idClient) {
        super(context, idClient);
        map.put("key","is_online");
    }
}
