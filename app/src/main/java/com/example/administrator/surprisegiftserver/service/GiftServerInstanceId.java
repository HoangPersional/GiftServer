package com.example.administrator.surprisegiftserver.service;

import android.content.SharedPreferences;

import com.example.administrator.surprisegiftserver.config.Config;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.zzd;

/**
 * Created by Administrator on 30/5/2017.
 */

public class GiftServerInstanceId extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        storeToken(token);

    }

    private void storeToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token).commit();
    }
}
