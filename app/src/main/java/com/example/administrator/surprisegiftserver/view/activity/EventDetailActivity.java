package com.example.administrator.surprisegiftserver.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.model.Event;
import com.example.administrator.surprisegiftserver.model.FirebaseResult;
import com.example.administrator.surprisegiftserver.model.TestConnectClient;
import com.example.administrator.surprisegiftserver.presenter.EventDetailPresenter;
import com.example.administrator.surprisegiftserver.support.CustomView;
import com.example.administrator.surprisegiftserver.view.EventDetailView;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Administrator on 27/5/2017.
 */

public class EventDetailActivity extends AppCompatActivity implements EventDetailView, View.OnClickListener {
    private CustomView customView;
    private TextView eventName, userName, clientName, date, time, message, image, music, video, note1, note2;
    private Typeface typeface;
    private Event event;
    private TableLayout tableLayout;
    private EventDetailPresenter eventDetailPresenter;
    TestConnectClient testConnectClient;
    private Button btTestConnect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        customView = (CustomView) findViewById(R.id.cv);
        eventName = (TextView) findViewById(R.id.tv_event_name);
        userName = (TextView) findViewById(R.id.tv_user_name);
        clientName = (TextView) findViewById(R.id.tv_to_client);
        date = (TextView) findViewById(R.id.tv_date);
        time = (TextView) findViewById(R.id.tv_time);
        message = (TextView) findViewById(R.id.tv_message);
        image = (TextView) findViewById(R.id.tv_image_link);
        music = (TextView) findViewById(R.id.tv_music_link);
        video = (TextView) findViewById(R.id.tv_video_link);
        note1 = (TextView) findViewById(R.id.tv_note1);
        note2 = (TextView) findViewById(R.id.tv_note2);
        tableLayout = (TableLayout) findViewById(R.id.tl_message);
        btTestConnect = (Button) findViewById(R.id.bt_test_connect_to_client);
        btTestConnect.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }

    private void initData() {
        eventDetailPresenter = new EventDetailPresenter(this);
        event = getIntent().getBundleExtra("data").getParcelable("event");
        typeface = Typeface.createFromAsset(getAssets(), "IndieFlower.ttf");
        eventName.setTypeface(typeface);
        eventName.setText(event.getName());
        if (event == null)
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        else {
            Log.v("EVENT", event.toString());
        }
        userName.setText(getUserName());
        if (event.getIdClient() == 0)
            clientName.setText(getString(R.string.no_client));
        else
            clientName.setText(String.valueOf(event.getIdClient()));
        date.setText(event.getDate() + "/" + event.getMonth() + "/" + event.getYear());
        time.setText(event.getHour() + "h" + event.getMinute() + "'");
        if (event.getIdClient() > 0) {
            message.setText(event.getMessage());
            image.setText(event.getImage());
            music.setText(event.getMp3());
            video.setText(event.getVideo());
            note1.setVisibility(View.GONE);
        } else {
            tableLayout.setVisibility(View.GONE);
            note1.setText("- " + getString(R.string.no_client));
        }
        note2.setText("- " + getString(R.string.notification_before) + " " + event.getiNotificationBeforeMinute() + " " + getString(R.string.munite));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
        return sharedPreferences.getString("userName", "");
    }

    @Override
    public void onTest() {
        if(event.getIdClient()!=0)
        {
            testConnectClient = new TestConnectClient(this, event.getIdClient());
            eventDetailPresenter.setTestConnectClient(testConnectClient);
            eventDetailPresenter.connect();
        }
        else {
            Toast.makeText(this,getString(R.string.add_client_before_check),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTestComplete(String response) {
        Gson gson=new Gson();
//        FirebaseResult firebaseResult=gson.fromJson(response,FirebaseResult.class);
//        Log.v("CONNECT", String.valueOf(firebaseResult.getSuccess()));
//        if(firebaseResult.getSuccess()==1)
//        {
//            Toast.makeText(this,getString(R.string.connect_success),Toast.LENGTH_SHORT).show();
//        }
//        else
//            Toast.makeText(this,getString(R.string.connect_fail),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_test_connect_to_client)
        {
            onTest();
        }
    }

    BroadcastReceiver CheckClientOnline =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"Client have id is "+event.getIdClient()+" online",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(CheckClientOnline,new IntentFilter("online"));
        registerReceiver(getImage,new IntentFilter("image"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(CheckClientOnline);
        unregisterReceiver(getImage);
    }
    BroadcastReceiver getImage =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            HashMap<String,String> hashMap= (HashMap<String, String>) intent.getSerializableExtra("data");
            Log.v("IMAGE",hashMap.toString());
            Intent i=new Intent(EventDetailActivity.this,ImageActicity.class);
            i.putExtra("data",hashMap);
            startActivity(i);
        }
    };
}
