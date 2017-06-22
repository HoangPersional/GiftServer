package com.example.administrator.surprisegiftserver.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.surprisegiftserver.MapsActivity;
import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.model.Client;
import com.example.administrator.surprisegiftserver.model.TestClient;
import com.example.administrator.surprisegiftserver.model.TestConnectClient;
import com.example.administrator.surprisegiftserver.model.TestLocation;
import com.example.administrator.surprisegiftserver.model.TestTakeImage;
import com.example.administrator.surprisegiftserver.presenter.ClientDetailPresenter;
import com.example.administrator.surprisegiftserver.view.ClientView;

import java.util.HashMap;

/**
 * Created by Administrator on 19/6/2017.
 */

public class ClientDetailActivity extends AppCompatActivity implements View.OnClickListener, ClientView {
    Client client;
    TextView id, name;
    Button btTestConnect, btLocation, btTakePicture;
    ClientDetailPresenter clientDetailPresenter;
    TestClient testClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_detail);
        client = getIntent().getBundleExtra("data").getParcelable("client");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = (TextView) findViewById(R.id.tv_id);
        name = (TextView) findViewById(R.id.tv_name_client);
        id.setText(getString(R.string.id) + " " + String.valueOf(client.getId()));
        name.setText(client.getName());
        Typeface typeface = Typeface.createFromAsset(getAssets(), "IndieFlower.ttf");
        name.setTypeface(typeface);
        id.setTypeface(typeface);
        btTestConnect = (Button) findViewById(R.id.bt_test_connect);
        btLocation = (Button) findViewById(R.id.bt_test_location);
        btTakePicture = (Button) findViewById(R.id.bt_test_take_image);
        btTestConnect.setOnClickListener(this);
        btLocation.setOnClickListener(this);
        btTakePicture.setOnClickListener(this);
        setTitle(client.getName());
        clientDetailPresenter = new ClientDetailPresenter(this, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_test_connect:
                onTest();
                break;
            case R.id.bt_test_location:
                onLocation();
                break;
            case R.id.bt_test_take_image:
                onTakeImage();
                break;
        }
    }

    @Override
    public void onTest() {
        testClient = new TestConnectClient(this, client.getId());
        clientDetailPresenter.setTestClient(testClient);
        clientDetailPresenter.connect();
    }

    @Override
    public void onTestComplete() {

    }

    @Override
    public void onLocation() {
        testClient = new TestLocation(this, client.getId());
        clientDetailPresenter.setTestClient(testClient);
        clientDetailPresenter.connect();

    }

    @Override
    public void onLocationComplete() {

    }

    @Override
    public void onTakeImage() {
        testClient = new TestTakeImage(this, client.getId());
        clientDetailPresenter.setTestClient(testClient);
        clientDetailPresenter.connect();
    }

    @Override
    public void onTakeImageComplete() {

    }

    BroadcastReceiver getImage =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            HashMap<String,String> hashMap= (HashMap<String, String>) intent.getSerializableExtra("data");
            Log.v("IMAGE",hashMap.toString());
            Intent i=new Intent(ClientDetailActivity.this,ImageActicity.class);
            i.putExtra("data",hashMap);
            startActivity(i);
        }
    };
    BroadcastReceiver getLocation =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            HashMap<String,String> hashMap= (HashMap<String, String>) intent.getSerializableExtra("data");
            Log.v("LOCATION",hashMap.toString());
            Intent i=new Intent(ClientDetailActivity.this,MapsActivity.class);
            Bundle bundle=new Bundle();
            bundle.putParcelable("client",client);
            i.putExtra("data",bundle);
            i.putExtra("from_client",hashMap);
            startActivity(i);
        }
    };
    BroadcastReceiver CheckClientOnline =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"Client have id is "+client.getId()+" online",Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(getImage,new IntentFilter("image"));
        registerReceiver(CheckClientOnline,new IntentFilter("online"));
        registerReceiver(getLocation,new IntentFilter("location"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(getImage);
        unregisterReceiver(CheckClientOnline);
        unregisterReceiver(getLocation);
    }
}
