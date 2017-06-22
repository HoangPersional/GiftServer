package com.example.administrator.surprisegiftserver.service;

import android.content.Intent;
import android.os.Bundle;

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
        Map<String, String> map = remoteMessage.getData();
        HashMap<String, String> hashMap = new HashMap<>(map);
        if (hashMap.get("key").equals("is_online")) {
            Intent intent = new Intent("online");
            sendBroadcast(intent);
        } else if (hashMap.get("key").equals("image")) {
            Intent intent = new Intent("image");
            intent.putExtra("data", hashMap);
            sendBroadcast(intent);
        }
        else if (hashMap.get("key").equals("location")) {
            Intent intent = new Intent("location");
            intent.putExtra("data", hashMap);
            sendBroadcast(intent);
        }

    }
}
