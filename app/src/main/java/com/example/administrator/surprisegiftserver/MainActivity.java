package com.example.administrator.surprisegiftserver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.surprisegiftserver.config.Config;
import com.example.administrator.surprisegiftserver.model.EventDbManager;
import com.example.administrator.surprisegiftserver.model.User;
import com.example.administrator.surprisegiftserver.service.NotificationService;
import com.example.administrator.surprisegiftserver.view.activity.AddEventActivity;
import com.example.administrator.surprisegiftserver.view.activity.LogInActivity;
import com.example.administrator.surprisegiftserver.view.fragment.ClientManager;
import com.example.administrator.surprisegiftserver.view.fragment.Home;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private User user;
    Home home;
    ClientManager clientManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        user = (User) getIntent().getBundleExtra("data").getParcelable("user");
        home = new Home();
        Bundle b = new Bundle();
        b.putParcelable("user", user);
        home.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, home).commit();
        clientManager=new ClientManager();
        clientManager.setArguments(b);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.it_log_out:
                logout();
                break;
            case R.id.it_event:
//                Home home=new Home();
//                Bundle b=new Bundle();
//                b.putParcelable("user",user);
//                home.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl, home).commit();
                break;
            case R.id.it_client_manager:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl, clientManager).commit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", -1);
        editor.putString("userName", "");
        editor.putString("passWord", "");
        editor.putString("email", "");
        editor.putBoolean("remember", false);
        editor.commit();
        Toast.makeText(this, getResources().getString(R.string.log_out), Toast.LENGTH_SHORT).show();
        EventDbManager.getInstance(this).clear();
        Intent intent1 = new Intent("UPDATE_DB");
        sendBroadcast(intent1);
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}
