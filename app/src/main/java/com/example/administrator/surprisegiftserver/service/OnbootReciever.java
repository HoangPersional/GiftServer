package com.example.administrator.surprisegiftserver.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 12/6/2017.
 */

public class OnbootReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1=new Intent(context,NotificationService.class);
        context.startService(intent1);
    }
}
