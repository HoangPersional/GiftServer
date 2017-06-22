package com.example.administrator.surprisegiftserver.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.surprisegiftserver.MainActivity;
import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.model.Circle;
import com.example.administrator.surprisegiftserver.model.User;
import com.example.administrator.surprisegiftserver.service.NotificationService;
import com.example.administrator.surprisegiftserver.support.ConnectServer;
import com.example.administrator.surprisegiftserver.support.MyMath;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Administrator on 17/5/2017.
 */

public class LogInActivity extends AppCompatActivity
        implements View.OnClickListener, ConnectServer.ConnectComplete, PopupMenu.OnMenuItemClickListener {
    public static int REQUEST_SIGN_UP = 0;
    public static int REQUEST_OK = 0;
    public static int REQUEST_FAIL = 1;
    private EditText userName, passWord;
    private Button signIn;
    private ConnectServer connectServer;
    private TextView moreOption, status;
    private ProgressBar progressBar;
    private CheckBox remember;
    private User user;
    private Gson gson;
    private boolean is_login = false;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        userName = (EditText) findViewById(R.id.et_user_name);
        passWord = (EditText) findViewById(R.id.et_pass_word);
        signIn = (Button) findViewById(R.id.bt_sign_in);
        moreOption = (TextView) findViewById(R.id.tv_more_option);
        status = (TextView) findViewById(R.id.tv_status);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        remember = (CheckBox) findViewById(R.id.cb);
        signIn.setOnClickListener(this);
        moreOption.setOnClickListener(this);
        connectServer = new ConnectServer(this, this);
        user = new User();
        gson = new Gson();
        init();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sign_in) {
            {
                handler.removeCallbacks(loginRunable);
                login();
            }
        } else if (v.getId() == R.id.tv_more_option) {
            setMenu(v);
        }
    }

    @Override
    public void response(String response) {
        progressBar.setVisibility(View.INVISIBLE);
        if (response.contains("sUserName")) {
            user = gson.fromJson(response, User.class);
            storeData();
            Intent intent = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", user);
            intent.putExtra("data", bundle);
            startActivity(intent);
            finish();
        } else if (response.equals("false")) {
            status.setText(getResources().getString(R.string.wrong_pass));

        } else if (response.equals("fail")) {
            status.setText(getResources().getString(R.string.no_user) + " " + user.getUserName());
        }
        is_login = false;
    }

    protected void setMenu(View v) {
        handler.removeCallbacks(loginRunable);
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.more_option, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_create_account:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGN_UP);
                break;
            case R.id.it_forgot_password:
                Toast.makeText(LogInActivity.this, "You got it on next version", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGN_UP && resultCode == REQUEST_OK) {
            user = (User) data.getBundleExtra("data").getParcelable("user");
            userName.setText(user.getUserName());
            passWord.setText(user.getPassWord());
            handler.postDelayed(loginRunable, 2000);
        }
    }

    public void login() {
        if (is_login == false) {
            is_login = true;
            signIn.setEnabled(false);
            status.setText("");
            user.setUserName(userName.getText().toString());
            user.setPassWord(passWord.getText().toString());
            if (!user.getUserName().isEmpty() && !user.getPassWord().isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                connectServer.setUrl(Config.LOG_IN);
                HashMap<String, String> map = new HashMap<>();
                map.put("username", user.getUserName());
                map.put("password", user.getPassWord());
                map.put("token", getToken());
                map.put("from", "server");
                connectServer.setPara(map);
                connectServer.connect();
            }
        }
    }

    private void storeData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("userName", user.getUserName());
        editor.putString("passWord", user.getPassWord());
        editor.putString("email", user.getEmail());
        editor.putBoolean("remember", remember.isChecked());
        editor.commit();

    }

    private void init() {
        handler = new Handler();
        Intent intent = getIntent();
        boolean b = intent.getBooleanExtra("MAIN", false);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
        user.setUserName(sharedPreferences.getString("userName", ""));
        user.setPassWord(sharedPreferences.getString("passWord", ""));
        remember.setChecked(sharedPreferences.getBoolean("remember", false));
        if (remember.isChecked()) {
            userName.setText(user.getUserName());
            passWord.setText(user.getPassWord());
            if (!b) {
                handler.postDelayed(loginRunable, 2000);
            } else
                login();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        startService(intent);

        Intent stopNotificaion = new Intent("STOP_NOTIFICATION");
        sendBroadcast(stopNotificaion);
    }

    public String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
        return sharedPreferences.getString("token", null);
    }

    Runnable loginRunable = new Runnable() {
        @Override
        public void run() {
            login();
        }
    };
}
