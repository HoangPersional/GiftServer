package com.example.administrator.surprisegiftserver.service;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 30/5/2017.
 */

public class GiftServerMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String,String> map=remoteMessage.getData();
        HashMap<String,String> hashMap=new HashMap<>(map);
        if(hashMap.get("key").equals("is_online"))
        {
            Intent intent=new Intent("online");
            sendBroadcast(intent);
        }
    }
}
