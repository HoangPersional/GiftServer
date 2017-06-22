package com.example.administrator.surprisegiftserver.presenter;

import android.content.Context;
import android.util.Log;

import com.example.administrator.surprisegiftserver.model.Client;
import com.example.administrator.surprisegiftserver.model.TestClient;
import com.example.administrator.surprisegiftserver.model.TestConnectClient;
import com.example.administrator.surprisegiftserver.model.TestLocation;
import com.example.administrator.surprisegiftserver.model.TestTakeImage;
import com.example.administrator.surprisegiftserver.support.ConnectServer;
import com.example.administrator.surprisegiftserver.view.ClientView;

/**
 * Created by Administrator on 20/6/2017.
 */

public class ClientDetailPresenter implements ConnectServer.ConnectComplete {
    ClientView view;
    TestClient testClient;
    Context context;
    public ClientDetailPresenter(Context context,ClientView view)
    {
        this.context=context;
        this.view=view;
    }
    public void connect()
    {
        testClient.connect();
    }

    @Override
    public void response(String response) {
        Log.v("ClientDetailPresenter",response);
    }


    public ClientView getView() {
        return view;
    }

    public void setView(ClientView view) {
        this.view = view;
    }

    public TestClient getTestClient() {
        return testClient;
    }

    public void setTestClient(TestClient testClient) {
        this.testClient = testClient;
        testClient.setConnectComplete(this);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
