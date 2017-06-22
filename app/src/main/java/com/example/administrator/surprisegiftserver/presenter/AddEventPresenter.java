package com.example.administrator.surprisegiftserver.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.surprisegiftserver.model.Client;
import com.example.administrator.surprisegiftserver.model.Event;
import com.example.administrator.surprisegiftserver.model.EventDbManager;
import com.example.administrator.surprisegiftserver.model.LoadClients;
import com.example.administrator.surprisegiftserver.model.PushEvent;
import com.example.administrator.surprisegiftserver.support.ConnectServer;
import com.example.administrator.surprisegiftserver.support.ConvertStringToArrayList;
import com.example.administrator.surprisegiftserver.view.AddEventView;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Administrator on 23/5/2017.
 */

public class AddEventPresenter implements ConnectServer.ConnectComplete {
    private AddEventView mView;
    private PushEvent mPushEvent;
    private LoadClients loadClients;
    private ArrayList<Client> clients;
    private EventDbManager eventDbManager;
    public AddEventPresenter(){}
    public AddEventPresenter(AddEventView view) {
        mView = view;
    }

    public AddEventPresenter(AddEventView view, PushEvent pushEvent) {
        mView = view;
        mPushEvent = pushEvent;
    }

    public void push() {
        mPushEvent.setOnComplete(this);
        mPushEvent.connect();
    }

    @Override
    public void response(String response) {
//        Log.v("HH__", response);
        Gson gson = new Gson();

        if (response.contains("iIdUserName") && response.contains("sToken")) {
            {
                clients = ConvertStringToArrayList.convertStringToArr(response, Client.class);
                mView.onGetClientComplete(clients);
            }
        }
        else
        if (response.contains("iIdUserName")) {
            Event event = gson.fromJson(response, Event.class);
            eventDbManager.insertEvent(event);
            Intent intent = new Intent("UPDATE_DB");
            eventDbManager.mContext.sendBroadcast(intent);
            mView.onPushSuccess(true);
        } else if (!response.contains("null")) {
            mView.onPushSuccess(false);
        }

    }

    public PushEvent getmPushEvent() {
        return mPushEvent;
    }

    public void setmPushEvent(PushEvent mPushEvent) {
        this.mPushEvent = mPushEvent;
        eventDbManager = EventDbManager.getInstance(mPushEvent.getContext());
    }

    public LoadClients getLoadClients() {
        return loadClients;
    }

    public void setLoadClients(LoadClients loadClients) {
        this.loadClients = loadClients;
    }

    public void loadClient() {
        loadClients.setConnectComplete(this);
        loadClients.connect();
    }
}
