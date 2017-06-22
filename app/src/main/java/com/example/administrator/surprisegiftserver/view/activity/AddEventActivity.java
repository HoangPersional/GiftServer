package com.example.administrator.surprisegiftserver.view.activity;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.model.Client;
import com.example.administrator.surprisegiftserver.model.Event;
import com.example.administrator.surprisegiftserver.model.EventDbManager;
import com.example.administrator.surprisegiftserver.model.EventMessage;
import com.example.administrator.surprisegiftserver.model.LoadClients;
import com.example.administrator.surprisegiftserver.model.PushEvent;
import com.example.administrator.surprisegiftserver.model.User;
import com.example.administrator.surprisegiftserver.presenter.AddEventPresenter;
import com.example.administrator.surprisegiftserver.view.AddEventView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 22/5/2017.
 */

public class AddEventActivity extends AppCompatActivity
        implements AddEventView, View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    public static int RESULT_CODE = 0;
    private EditText eventName, description, message, image, music, video, date, month, year, hour, minute;
    private RadioGroup isTimeType, isClient;
    private SwitchCompat notification;
    private Button createEvent;
    private AddEventPresenter addEventPresenter;
    private PushEvent pushEvent;
    private LinearLayout messageBlock;
    private Client mClient;
    private User user;
    private AppCompatSpinner appCompatSpinner;
    private ArrayAdapter<String> stringArrayAdapter;
    private ArrayList<Client> clients;
    private ScrollView scrollView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        initData();
        onGetClient(user);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_create_event:
                push();
                break;
        }
    }

    @Override
    public void onReload() {
    }

    @Override
    public void onClient(boolean b) {
        messageBlock.setVisibility(b == true ? View.VISIBLE : View.GONE);
        if (b == true) {
            scrollView.setSmoothScrollingEnabled(true);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        }
    }

    @Override
    public void push() {
        Event e = getEvent();
        if (e != null) {
            pushEvent = new PushEvent(this, getEvent());
            addEventPresenter.setmPushEvent(pushEvent);
            addEventPresenter.push();
        } else
            Toast.makeText(this, getString(R.string.name_is_empty), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPushSuccess(boolean b) {
        String s = "";
        if (b) {
            s = getResources().getString(R.string.add_event_success);
            RESULT_CODE = 1;
        } else
            s = getResources().getString(R.string.add_event_fail);
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onGetClient(User user) {
        LoadClients loadClients = new LoadClients(this, user);
        addEventPresenter.setLoadClients(loadClients);
        addEventPresenter.loadClient();
    }

    @Override
    public void onGetClientComplete(ArrayList<Client> clients) {
        String[] ar = new String[clients.size()];
        this.clients = clients;
        for (int i = 0; i < clients.size(); ++i) {
            ar[i] = clients.get(i).getName();
        }
        stringArrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, ar);
        appCompatSpinner.setAdapter(stringArrayAdapter);
        appCompatSpinner.setOnItemSelectedListener(this);
    }

    protected void init() {
        eventName = (EditText) findViewById(R.id.et_event_name);
        description = (EditText) findViewById(R.id.et_description);
        image = (EditText) findViewById(R.id.et_image);
        music = (EditText) findViewById(R.id.et_music);
        video = (EditText) findViewById(R.id.et_video);
        date = (EditText) findViewById(R.id.et_date);
        month = (EditText) findViewById(R.id.et_month);
        year = (EditText) findViewById(R.id.et_year);
        hour = (EditText) findViewById(R.id.et_hour);
        minute = (EditText) findViewById(R.id.et_minute);
        message = (EditText) findViewById(R.id.et_message);
        image = (EditText) findViewById(R.id.et_image);
        video = (EditText) findViewById(R.id.et_video);
        music = (EditText) findViewById(R.id.et_music);
        scrollView = (ScrollView) findViewById(R.id.sv);
        appCompatSpinner = (AppCompatSpinner) findViewById(R.id.acs_choose_client);
        isTimeType = (RadioGroup) findViewById(R.id.rg_time);
        isClient = (RadioGroup) findViewById(R.id.rg_client);
        notification = (SwitchCompat) findViewById(R.id.sc_notification);
//        chooseClient = (ImageView) findViewById(R.id.iv_choose_client);
        createEvent = (Button) findViewById(R.id.bt_create_event);
        messageBlock = (LinearLayout) findViewById(R.id.ll_message);
        isClient.setOnCheckedChangeListener(this);
        createEvent.setOnClickListener(this);
//        chooseClient.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.equals(isClient)) {
            onClient(checkedId == R.id.rb_clients ? true : false);
        }
    }

    private void initData() {
        user = (User) getIntent().getBundleExtra("data").getParcelable("user");
        mClient = new Client();
        Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        this.date.setText(String.valueOf(date));
        this.month.setText(String.valueOf(month));
        this.year.setText(String.valueOf(year));
        this.hour.setText(String.valueOf(hour));
        this.minute.setText(String.valueOf(minute));
        addEventPresenter = new AddEventPresenter(this);
    }

    protected Event getEvent() {
        Event event = null;
        String eventName;
        int date, month, year, hour, minute;
        boolean timeType, isClient;
        eventName = this.eventName.getText().toString();
        if (!eventName.isEmpty() && TextUtils.isDigitsOnly(this.date.getText()) && TextUtils.isDigitsOnly(this.month.getText())
                && TextUtils.isDigitsOnly(this.year.getText()) && TextUtils.isDigitsOnly(this.hour.getText())
                && TextUtils.isDigitsOnly(this.minute.getText())) {
            date = Integer.parseInt(this.date.getText().toString());
            month = Integer.parseInt(this.month.getText().toString());
            year = Integer.parseInt(this.year.getText().toString());
            hour = Integer.parseInt(this.hour.getText().toString());
            minute = Integer.parseInt(this.minute.getText().toString());
            event = new Event();
            event.setId(user.getId());
            event.setName(eventName);
            event.setDate(date);
            event.setMonth(month);
            event.setYear(year);
            event.setHour(hour);
            event.setMinute(minute);
            timeType = this.isTimeType.getCheckedRadioButtonId() == R.id.rb_month ? true : false;
            isClient = this.isClient.getCheckedRadioButtonId() == R.id.rb_clients ? true : false;
            event.setRepeat(timeType);
            event.setClient(isClient);
            event.setDescription(this.description.getText().toString());
            event.setNotification(this.notification.isChecked());
            if (isClient) {
                event.setMessage(this.message.getText().toString());
                event.setImage(this.image.getText().toString());
                event.setMp3(this.music.getText().toString());
                event.setVideo(this.video.getText().toString());
                event.setIdClient(this.mClient.getId());
            }
        }
        return event;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            sendData();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void sendData() {
        setResult(RESULT_CODE);
        finish();
    }

    @Override
    public void onBackPressed() {
        sendData();
        super.onBackPressed();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mClient = clients.get(position);
        Log.v("HH", mClient.getName());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
